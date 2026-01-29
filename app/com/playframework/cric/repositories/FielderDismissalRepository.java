package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.FielderDismissal;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FielderDismissalRepository {
    private final JPAApi jpaApi;

    @Inject
    public FielderDismissalRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<FielderDismissal> add(Map<Integer, List<Long>> scorePlayerMap, Map<Long, Integer> matchPlayerMap)
    {
        return jpaApi.withTransaction(em -> {
            return add(em, scorePlayerMap, matchPlayerMap);
        });
    }

    public List<FielderDismissal> add(EntityManager em, Map<Integer, List<Long>> scorePlayerMap, Map<Long, Integer> matchPlayerMap)
    {
        List<FielderDismissal> fielderDismissals = new ArrayList<>();
        for(Map.Entry<Integer, List<Long>> scorePlayerEntry: scorePlayerMap.entrySet())
        {
            fielderDismissals.addAll(scorePlayerEntry.getValue().stream().map(playerId -> new FielderDismissal(null, scorePlayerEntry.getKey(), matchPlayerMap.get(playerId))).collect(Collectors.toList()));
        }
        fielderDismissals.forEach(em::persist);
        return fielderDismissals;
    }

    public Map<String, Map<String, Integer>> getFieldingStats(Long playerId)
    {
        Map<String, Map<String, Integer>> statsFinal = new HashMap<>();

        String query = "select dm.name as dismissalMode, count(*) as count, gt.name as gameType from fielder_dismissals fd inner join match_player_map mpm on mpm.id = fd.match_player_id inner join batting_scores bs on bs.id = fd.score_id and mpm.player_id = " + playerId + " inner join dismissal_modes dm on dm.id = bs.dismissal_mode_id inner join matches m on m.id = mpm.match_id and m.is_official = 1 inner join series s on s.id = m.series_id inner join teams t on t.id = mpm.team_id inner join team_types tt on tt.id = t.type_id and tt.name = 'International' inner join game_types gt on gt.id = s.game_type_id group by gt.name, dm.name";
        jpaApi.withTransaction(em -> {
            List<Object[]> rows = em.createNativeQuery(query).getResultList();

            for (Object[] row : rows) {
                String gameType = (String) row[2];
                String dismissalMode = (String) row[0];
                int dismissalCount = ((Long) row[1]).intValue();
                if(statsFinal.containsKey(gameType))
                {
                    statsFinal.get(gameType).put(dismissalMode, dismissalCount);
                }
                else
                {
                    statsFinal.put(gameType, new HashMap<>(){
                        {
                            put(dismissalMode, dismissalCount);
                        }
                    });
                }
                String sh = "sh";
            }
        });

        return statsFinal;
    }

    public List<FielderDismissal> get(List<Integer> matchPlayerIds)
    {
        return jpaApi.withTransaction(em -> {
                return em.createQuery(
                        "SELECT fd FROM FielderDismissal fd WHERE fd.matchPlayerId IN :ids",
                        FielderDismissal.class
                )
                .setParameter("ids", matchPlayerIds)
                .getResultList();
            }
        );
    }

    public void remove(List<Integer> matchPlayerIds)
    {
        jpaApi.withTransaction(em -> {
            remove(em, matchPlayerIds);
        });
    }

    public void remove(EntityManager em, List<Integer> matchPlayerIds)
    {
        em.createQuery(
            "DELETE FROM FielderDismissal fd WHERE fd.matchPlayerId IN :ids"
        )
        .setParameter("ids", matchPlayerIds).executeUpdate();
    }
}
