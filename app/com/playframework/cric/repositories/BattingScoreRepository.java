package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.BattingScore;
import com.playframework.cric.requests.matches.BattingScoreRequest;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BattingScoreRepository {
    private final JPAApi jpaApi;

    @Inject
    public BattingScoreRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<BattingScore> add(List<BattingScoreRequest> battingScoreRequests, Map<Long, Integer> matchPlayerMaps)
    {
        return jpaApi.withTransaction(em -> {
            return add(em, battingScoreRequests, matchPlayerMaps);
        });
    }

    public List<BattingScore> add(EntityManager em, List<BattingScoreRequest> battingScoreRequests, Map<Long, Integer> matchPlayerMaps)
    {
        List<BattingScore> battingScores = battingScoreRequests.stream().map(battingScoreRequest -> new BattingScore(battingScoreRequest, matchPlayerMaps)).collect(Collectors.toList());
        battingScores.forEach(em::persist);
        return battingScores;
    }

    public Map<String, Map<String, Integer>> getBattingStats(Long playerId)
    {
        Map<String, Map<String, Integer>> statsFinal = new HashMap<>();
        String query = "SELECT COUNT(*) AS innings, SUM(runs) AS runs, SUM(balls) AS balls, SUM(fours) AS fours, SUM(sixes) AS sixes, MAX(runs) AS highest, gt.name as gameType, count(CASE WHEN (bs.runs >= 50 and bs.runs < 100) then 1 end) as fifties, count(CASE WHEN (bs.runs >= 100 and bs.runs < 200) then 1 end) as hundreds, count(CASE WHEN (bs.runs >= 200 and bs.runs < 300) then 1 end) as twoHundreds, count(CASE WHEN (bs.runs >= 300 and bs.runs < 400) then 1 end) as threeHundreds, count(CASE WHEN (bs.runs >= 400 and bs.runs < 500) then 1 end) as fourHundreds FROM `batting_scores` bs inner join match_player_map mpm on mpm.player_id = " + playerId + " and  mpm.id = bs.match_player_id inner join matches m on m.id = mpm.match_id and m.is_official = 1 inner join series s on s.id = m.series_id inner join teams t on t.id = mpm.team_id inner join team_types tt on tt.id = t.type_id and tt.name = 'International' inner join game_types gt on gt.id = s.game_type_id group by gt.name;";
        jpaApi.withTransaction(em -> {
            List<Object[]> rows = em.createNativeQuery(query).getResultList();

            for (Object[] row: rows)
            {
                int innings = ((Long) row[0]).intValue();
                if(innings > 0)
                {
                    Map<String, Integer> stats = new HashMap<>();

                    stats.put("innings", innings);
                    stats.put("runs", ((BigDecimal) row[1]).intValue());
                    stats.put("balls", ((BigDecimal) row[2]).intValue());
                    stats.put("fours", ((BigDecimal) row[3]).intValue());
                    stats.put("sixes", ((BigDecimal) row[4]).intValue());
                    stats.put("highest", Integer.valueOf(row[5].toString()));
                    stats.put("fifties", ((Long) row[7]).intValue());
                    stats.put("hundreds", ((Long) row[8]).intValue());
                    stats.put("twoHundreds", ((Long) row[9]).intValue());
                    stats.put("threeHundreds", ((Long) row[10]).intValue());
                    stats.put("fourHundreds", ((Long) row[11]).intValue());

                    String gameType = (String) row[6];
                    statsFinal.put(gameType, stats);
                }
                String sh = "sh";
            }
        });

        return statsFinal;
    }

    public Map<String, Map<String, Integer>> getDismissalStats(Long playerId)
    {
        Map<String, Map<String, Integer>> stats = new HashMap<>();

        String query = "SELECT dm.name AS dismissalMode, COUNT(*) AS count, gt.name as gameType FROM `batting_scores` bs INNER JOIN match_player_map mpm on mpm.id = bs.match_player_id inner join dismissal_modes dm ON mpm.player_id = " + playerId + " AND bs.dismissal_mode_id IS NOT NULL and dm.id = bs.dismissal_mode_id and dm.name != 'Retired Hurt' inner join matches m on m.id = mpm.match_id and m.is_official = 1 inner join series s on s.id = m.series_id inner join teams t on t.id = mpm.team_id inner join team_types tt on tt.id = t.type_id and tt.name = 'International' inner join game_types gt on gt.id = s.game_type_id GROUP BY gt.name, bs.dismissal_mode_id;";
        jpaApi.withTransaction(em -> {
            List<Object[]> rows = em.createNativeQuery(query).getResultList();

            for (Object[] row : rows)
            {
                String gameType = (String) row[2];
                String dismissalMode = (String) row[0];
                int dismissalCount = ((Long) row[1]).intValue();
                if(stats.containsKey(gameType))
                {
                    stats.get(gameType).put(dismissalMode, dismissalCount);
                }
                else
                {
                    Map<String, Integer> partStats = new HashMap<>(){
                        {
                            put(dismissalMode, dismissalCount);
                        }
                    };
                    stats.put(gameType, partStats);
                }
                String sh = "sh";
            }
        });

        return stats;
    }

    public List<BattingScore> getBattingScores(List<Integer> matchPlayerIds)
    {
        return jpaApi.withTransaction(em -> {
                return em.createQuery(
                        "SELECT bs FROM BattingScore bs WHERE bs.matchPlayerId IN :ids ORDER BY bs.innings, bs.number",
                        BattingScore.class
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
            "DELETE FROM BattingScore bs WHERE bs.matchPlayerId IN :ids",
            BattingScore.class
        )
        .setParameter("ids", matchPlayerIds).executeUpdate();
    }
}
