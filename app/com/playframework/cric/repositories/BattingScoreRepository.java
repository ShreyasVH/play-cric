package com.playframework.cric.repositories;

import com.playframework.cric.models.BattingScore;
import com.playframework.cric.requests.matches.BattingScoreRequest;
import io.ebean.DB;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BattingScoreRepository {
    public List<BattingScore> add(List<BattingScoreRequest> battingScoreRequests, Map<Long, Integer> matchPlayerMaps)
    {
        List<BattingScore> battingScores = battingScoreRequests.stream().map(battingScoreRequest -> new BattingScore(battingScoreRequest, matchPlayerMaps)).collect(Collectors.toList());
        DB.saveAll(battingScores);
        return battingScores;
    }

    public Map<String, Map<String, Integer>> getBattingStats(Long playerId)
    {
        Map<String, Map<String, Integer>> statsFinal = new HashMap<>();
        String query = "SELECT COUNT(*) AS innings, SUM(runs) AS runs, SUM(balls) AS balls, SUM(fours) AS fours, SUM(sixes) AS sixes, MAX(runs) AS highest, gt.name as gameType, count(CASE WHEN (bs.runs >= 50 and bs.runs < 100) then 1 end) as fifties, count(CASE WHEN (bs.runs >= 100 and bs.runs < 200) then 1 end) as hundreds, count(CASE WHEN (bs.runs >= 200 and bs.runs < 300) then 1 end) as twoHundreds, count(CASE WHEN (bs.runs >= 300 and bs.runs < 400) then 1 end) as threeHundreds, count(CASE WHEN (bs.runs >= 400 and bs.runs < 500) then 1 end) as fourHundreds FROM `batting_scores` bs inner join match_player_map mpm on mpm.player_id = " + playerId + " and  mpm.id = bs.match_player_id inner join matches m on m.id = mpm.match_id and m.is_official = 1 inner join series s on s.id = m.series_id inner join teams t on t.id = mpm.team_id inner join team_types tt on tt.id = t.type_id and tt.name = 'International' inner join game_types gt on gt.id = s.game_type_id group by gt.name;";
        SqlQuery sqlQuery = DB.sqlQuery(query);
        List<SqlRow> rows = sqlQuery.findList();

        for(SqlRow row: rows)
        {
            Integer innings = row.getInteger("innings");
            if(innings > 0)
            {
                Map<String, Integer> stats = new HashMap<>();

                stats.put("innings", innings);
                stats.put("runs", row.getInteger("runs"));
                stats.put("balls", row.getInteger("balls"));
                stats.put("fours", row.getInteger("fours"));
                stats.put("sixes", row.getInteger("sixes"));
                stats.put("highest", row.getInteger("highest"));
                stats.put("fifties", row.getInteger("fifties"));
                stats.put("hundreds", row.getInteger("hundreds"));
                stats.put("twoHundreds", row.getInteger("twoHundreds"));
                stats.put("threeHundreds", row.getInteger("threeHundreds"));
                stats.put("fourHundreds", row.getInteger("fourHundreds"));

                String gameType = row.getString("gameType");
                statsFinal.put(gameType, stats);
            }
        }

        return statsFinal;
    }

    public Map<String, Map<String, Integer>> getDismissalStats(Long playerId)
    {
        Map<String, Map<String, Integer>> stats = new HashMap<>();

        String query = "SELECT dm.name AS dismissalMode, COUNT(*) AS count, gt.name as gameType FROM `batting_scores` bs INNER JOIN match_player_map mpm on mpm.id = bs.match_player_id inner join dismissal_modes dm ON mpm.player_id = " + playerId + " AND bs.dismissal_mode_id IS NOT NULL and dm.id = bs.dismissal_mode_id and dm.name != 'Retired Hurt' inner join matches m on m.id = mpm.match_id and m.is_official = 1 inner join series s on s.id = m.series_id inner join teams t on t.id = mpm.team_id inner join team_types tt on tt.id = t.type_id and tt.name = 'International' inner join game_types gt on gt.id = s.game_type_id GROUP BY gt.name, bs.dismissal_mode_id;";
        SqlQuery sqlQuery = DB.sqlQuery(query);
        List<SqlRow> result = sqlQuery.findList();

        for(SqlRow row: result)
        {
            String gameType = row.getString("gameType");
            if(stats.containsKey(gameType))
            {
                stats.get(gameType).put(row.getString("dismissalMode"), row.getInteger("count"));
            }
            else
            {
                Map<String, Integer> partStats = new HashMap<>(){
                    {
                        put(row.getString("dismissalMode"), row.getInteger("count"));
                    }
                };
                stats.put(gameType, partStats);
            }
        }

        return stats;
    }

    public List<BattingScore> getBattingScores(List<Integer> matchPlayerIds)
    {
        return DB.find(BattingScore.class).where().in("matchPlayerId", matchPlayerIds).findList();
    }
}
