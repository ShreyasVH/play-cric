package com.playframework.cric.repositories;

import com.playframework.cric.models.BowlingFigure;
import com.playframework.cric.requests.matches.BowlingFigureRequest;
import io.ebean.DB;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BowlingFigureRepository {
    public List<BowlingFigure> add(List<BowlingFigureRequest> bowlingFigureRequests, Map<Long, Integer> matchPlayerMap)
    {
        List<BowlingFigure> bowlingFigures = bowlingFigureRequests.stream().map(bowlingFigureRequest -> new BowlingFigure(bowlingFigureRequest, matchPlayerMap)).collect(Collectors.toList());
        DB.saveAll(bowlingFigures);
        return bowlingFigures;
    }

    public Map<String, Map<String, Integer>> getBasicBowlingStats(Long playerId)
    {
        Map<String, Map<String, Integer>> statsFinal = new HashMap<>();

        String query = "SELECT COUNT(*) AS innings, SUM(balls) AS balls, SUM(maidens) AS maidens, SUM(runs) AS runs, SUM(wickets) AS wickets, gt.name AS gameType, COUNT(CASE WHEN (bf.wickets >= 5 and bf.wickets < 10) then 1 end) as fifers,  COUNT(CASE WHEN (bf.wickets = 10) then 1 end) as tenWickets FROM bowling_figures bf inner join match_player_map mpm on mpm.id = bf.match_player_id and mpm.player_id = " + playerId + " INNER JOIN matches m ON m.id = mpm.match_id INNER JOIN series s ON s.id = m.series_id and m.is_official = 1 inner join teams t on t.id = mpm.team_id inner join team_types tt on tt.id = t.type_id and tt.name = 'INTERNATIONAL' inner join game_types gt on gt.id = s.game_type_id GROUP BY gt.name";
        SqlQuery sqlQuery = DB.sqlQuery(query);
        List<SqlRow> result = sqlQuery.findList();

        for(SqlRow row: result)
        {
            String gameType = row.getString("gameType");
            Integer innings = row.getInteger("innings");
            if(innings > 0)
            {
                Map<String, Integer> stats = new HashMap<>();

                stats.put("innings", innings);
                stats.put("runs", row.getInteger("runs"));
                stats.put("balls", row.getInteger("balls"));
                stats.put("maidens", row.getInteger("maidens"));
                stats.put("wickets", row.getInteger("wickets"));
                stats.put("fifers", row.getInteger("fifers"));
                stats.put("tenWickets", row.getInteger("tenWickets"));

                statsFinal.put(gameType, stats);
            }
        }

        return statsFinal;
    }

    public List<BowlingFigure> get(List<Integer> matchPlayerIds)
    {
        return DB.find(BowlingFigure.class).where().in("matchPlayerId", matchPlayerIds).findList();
    }
}
