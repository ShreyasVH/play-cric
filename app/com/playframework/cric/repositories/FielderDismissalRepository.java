package com.playframework.cric.repositories;

import com.playframework.cric.models.FielderDismissal;
import io.ebean.DB;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FielderDismissalRepository {
    public List<FielderDismissal> add(Map<Integer, List<Long>> scorePlayerMap, Map<Long, Integer> matchPlayerMap)
    {
        List<FielderDismissal> fielderDismissals = new ArrayList<>();
        for(Map.Entry<Integer, List<Long>> scorePlayerEntry: scorePlayerMap.entrySet())
        {
            fielderDismissals.addAll(scorePlayerEntry.getValue().stream().map(playerId -> new FielderDismissal(null, scorePlayerEntry.getKey(), matchPlayerMap.get(playerId))).collect(Collectors.toList()));
        }
        DB.saveAll(fielderDismissals);
        return fielderDismissals;
    }

    public Map<String, Map<String, Integer>> getFieldingStats(Long playerId)
    {
        Map<String, Map<String, Integer>> statsFinal = new HashMap<>();

        String query = "select dm.name as dismissalMode, count(*) as count, gt.name as gameType from fielder_dismissals fd inner join match_player_map mpm on mpm.id = fd.match_player_id inner join batting_scores bs on bs.id = fd.score_id and mpm.player_id = " + playerId + " inner join dismissal_modes dm on dm.id = bs.dismissal_mode_id inner join matches m on m.id = mpm.match_id and m.is_official = 1 inner join series s on s.id = m.series_id inner join teams t on t.id = mpm.team_id inner join team_types tt on tt.id = t.type_id and tt.name = 'International' inner join game_types gt on gt.id = s.game_type_id group by gt.name, dm.name";
        SqlQuery sqlQuery = DB.sqlQuery(query);
        List<SqlRow> result = sqlQuery.findList();

        for(SqlRow row: result)
        {
            String gameType = row.getString("gameType");
            if(statsFinal.containsKey(gameType))
            {
                statsFinal.get(gameType).put(row.getString("dismissalMode"), row.getInteger("count"));
            }
            else
            {
                statsFinal.put(gameType, new HashMap<>(){
                    {
                        put(row.getString("dismissalMode"), row.getInteger("count"));
                    }
                });
            }
        }

        return statsFinal;
    }

    public List<FielderDismissal> get(List<Integer> matchPlayerIds)
    {
        return DB.find(FielderDismissal.class).where().in("matchPlayerId", matchPlayerIds).findList();
    }
}
