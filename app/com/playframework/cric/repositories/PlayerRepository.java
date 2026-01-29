package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.requests.FilterRequest;
import com.playframework.cric.responses.StatsResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.playframework.cric.models.Player;
import com.playframework.cric.requests.players.CreateRequest;
import com.playframework.cric.utils.Utils;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

public class PlayerRepository {
    private final JPAApi jpaApi;

    @Inject
    public PlayerRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public Player create(CreateRequest createRequest) {
        Player player = Utils.convertObject(createRequest, Player.class);
        return jpaApi.withTransaction(em -> {
            em.persist(player);
            return player;
        });
    }

    public Player getByNameAndCountryIdAndDateOfBirth(String name, Long countryId, LocalDate dateOfBirth) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT p FROM Player p WHERE p.name = :name AND p.countryId = :countryId AND p.dateOfBirth = :dateOfBirth", Player.class)
                .setParameter("name", name)
                .setParameter("countryId", countryId)
                .setParameter("dateOfBirth", dateOfBirth)
                .getSingleResultOrNull();
        });
    }

    public List<Player> getAll(int page, int limit) {
        int offset = (page - 1) * limit;

        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT p FROM Player p ORDER BY p.name ASC",
                Player.class
            )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        });
    }

    public long getTotalCount() {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT COUNT(p) FROM Player p",
                Long.class
            )
            .getSingleResult();
        });
    }

    public List<Player> getByIds(List<Long> ids) {
        return jpaApi.withTransaction(em -> {
            return getByIds(em, ids);
        });
    }

    public List<Player> getByIds(EntityManager em, List<Long> ids) {
        return em.createQuery("SELECT p FROM Player p WHERE p.id IN :ids", Player.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public Player getById(Long id)
    {
        return jpaApi.withTransaction(em -> {
            return getById(em, id);
        });
    }

    public Player getById(EntityManager em, Long id)
    {
        return em.createQuery("SELECT p FROM Player p WHERE p.id = :id", Player.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
    }

    public void remove(Long id)
    {
        jpaApi.withTransaction(em -> {
            remove(em, id);
        });
    }

    public void remove(EntityManager em, Long id)
    {
        em.createQuery(
                "DELETE FROM Player p WHERE p.id = :id"
        )
        .setParameter("id", id)
        .executeUpdate();
    }

    public String getFieldNameWithTablePrefix(String field)
    {
        String fieldName = "";

        switch(field)
        {
            case "gameType":
                fieldName = "s.game_type_id";
                break;
            case "stadium":
                fieldName = "m.stadium_id";
                break;
            case "team":
                fieldName = "t.id";
                break;
            case "opposingTeam":
                fieldName = "IF(t.id = m.team_1_id, m.team_2_id, m.team_1_id)";
                break;
            case "teamType":
                fieldName = "t.type_id";
                break;
            case "country":
                fieldName = "p.country_id";
                break;
            case "series":
                fieldName = "s.id";
                break;
            case "year":
                fieldName = "YEAR(m.start_time)";
                break;
            case "playerName":
                fieldName = "p.name";
                break;
        }

        return fieldName;
    }

    public String getFieldNameForDisplay(String field)
    {
        String fieldName = "";

        switch(field)
        {
            case "runs":
                fieldName = "runs";
                break;
            case "balls":
                fieldName = "balls";
                break;
            case "innings":
                fieldName = "innings";
                break;
            case "notOuts":
                fieldName = "notouts";
                break;
            case "fifties":
                fieldName = "fifties";
                break;
            case "hundreds":
                fieldName = "hundreds";
                break;
            case "highest":
                fieldName = "highest";
                break;
            case "fours":
                fieldName = "fours";
                break;
            case "sixes":
                fieldName = "sixes";
                break;
            case "wickets":
                fieldName = "wickets";
                break;
            case "maidens":
                fieldName = "maidens";
                break;
            case "fifers":
                fieldName = "fifers";
                break;
            case "tenWickets":
                fieldName = "tenWickets";
                break;
            case "fielderCatches":
                fieldName = "fielderCatches";
                break;
            case "keeperCatches":
                fieldName = "keeperCatches";
                break;
            case "stumpings":
                fieldName = "stumpings";
                break;
            case "runOuts":
                fieldName = "runOuts";
                break;
        }

        return fieldName;
    }

    public List<Player> search(String keyword, int page, int limit) {
        int offset = (page - 1) * limit;

        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT p FROM Player p WHERE LOWER(p.name) like LOWER(:name) ORDER BY p.name ASC",
                Player.class
            )
            .setParameter("name", "%" + keyword + "%")
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        });
    }
    public long searchCount(String keyword) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT COUNT(p) FROM Player p WHERE LOWER(p.name) like LOWER(:name)",
                    Long.class
            )
            .setParameter("name", "%" + keyword + "%")
            .getSingleResult();
        });
    }

    public StatsResponse getBattingStats(FilterRequest filterRequest) {
        StatsResponse statsResponse = new StatsResponse();
        List<Map<String, Object>> statList = new ArrayList<>();
        String query = "select p.id as playerId, p.name AS name, sum(bs.runs) AS `runs`, count(0) AS `innings`, sum(`bs`.`balls`) AS `balls`, sum(`bs`.`fours`) AS `fours`, sum(`bs`.`sixes`) AS `sixes`, max(`bs`.`runs`) AS `highest`, count((case when (`bs`.`dismissal_mode_id` is null) then 1 end)) AS `notouts`, count((case when ((`bs`.`runs` >= 50) and (`bs`.`runs` < 100)) then 1 end)) AS `fifties`, count((case when ((`bs`.`runs` >= 100)) then 1 end)) AS `hundreds` from batting_scores bs " +
                "inner join match_player_map mpm on mpm.id = bs.match_player_id " +
                "inner join players p on p.id = mpm.player_id " +
                "inner join matches m on m.id = mpm.match_id " +
                "inner join series s on s.id = m.series_id " +
                "inner join stadiums st on st.id = m.stadium_id " +
                "inner join teams t on t.id = mpm.team_id";

        String countQuery = "select count(distinct p.id) as count from batting_scores bs " +
                "inner join match_player_map mpm on mpm.id = bs.match_player_id " +
                "inner join players p on p.id = mpm.player_id " +
                "inner join matches m on m.id = mpm.match_id " +
                "inner join series s on s.id = m.series_id " +
                "inner join stadiums st on st.id = m.stadium_id " +
                "inner join teams t on t.id = mpm.team_id";

        //where
        List<String> whereQueryParts = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : filterRequest.getFilters().entrySet()) {
            String field = entry.getKey();
            List<String> valueList = entry.getValue();

            String fieldNameWithTablePrefix = getFieldNameWithTablePrefix(field);
            if (!fieldNameWithTablePrefix.isEmpty() && !valueList.isEmpty()) {
                whereQueryParts.add(fieldNameWithTablePrefix + " in (" + String.join(", ", valueList) + ")");
            }
        }

        for (Map.Entry<String, Map<String, String>> entry : filterRequest.getRangeFilters().entrySet()) {
            String field = entry.getKey();
            Map<String, String> rangeValues = entry.getValue();

            String fieldNameWithTablePrefix = getFieldNameWithTablePrefix(field);
            if (!fieldNameWithTablePrefix.isEmpty() && !rangeValues.isEmpty()) {
                if (rangeValues.containsKey("from")) {
                    whereQueryParts.add(fieldNameWithTablePrefix + " >= " + rangeValues.get("from"));
                }
                if (rangeValues.containsKey("to")) {
                    whereQueryParts.add(fieldNameWithTablePrefix + " <= " + rangeValues.get("to"));
                }

            }
        }

        if (!whereQueryParts.isEmpty()) {
            query += " where " + String.join(" and ", whereQueryParts);
            countQuery += " where " + String.join(" and ", whereQueryParts);
        }

        query += " group by playerId";

        //sort
        List<String> sortList = new ArrayList<>();
        for (Map.Entry<String, String> entry : filterRequest.getSortMap().entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();

            String sortFieldName = getFieldNameForDisplay(field);
            if (!sortFieldName.isEmpty()) {
                sortList.add(sortFieldName + " " + value);
            }
        }
        if (sortList.isEmpty()) {
            sortList.add(getFieldNameForDisplay("runs") + " desc");
        }
        query += " order by " + String.join(", ", sortList);

        //offset limit
        query += " limit " + Integer.min(30, filterRequest.getCount()) + " offset " + filterRequest.getOffset();

        String finalCountQuery = countQuery;
        jpaApi.withTransaction(em -> {
            List<Long> countResult = em.createNativeQuery(finalCountQuery).getResultList();
            statsResponse.setCount(countResult.get(0));
        });

        String finalQuery = query;
        jpaApi.withTransaction(em -> {
            List<Object[]> result = em.createNativeQuery(finalQuery).getResultList();
            for(Object[] row: result)
            {
                long innings = (Long) row[3];
                if (innings > 0) {
                    Map<String, Object> stats = new HashMap<>();

                    stats.put("id", row[0]);
                    stats.put("name", row[1]);
                    stats.put("innings", innings);
                    stats.put("runs", row[2]);
                    stats.put("balls", row[4]);
                    stats.put("notOuts", row[8]);
                    stats.put("fours", row[5]);
                    stats.put("sixes", row[6]);
                    stats.put("highest", row[7]);
                    stats.put("fifties", row[9]);
                    stats.put("hundreds", row[10]);
//                    stats.put("twoHundreds", row.getString("twoHundreds"));
//                    stats.put("threeHundreds", row.getString("threeHundreds"));
//                    stats.put("fourHundreds", row.getString("fourHundreds"));

                    statList.add(stats);
                }
            }
        });

        statsResponse.setStats(statList);

        return statsResponse;
    }

    public StatsResponse getBowlingStats(FilterRequest filterRequest) {
        StatsResponse statsResponse = new StatsResponse();
        List<Map<String, Object>> statList = new ArrayList<>();

        String query = "select p.id as playerId, p.name AS name, sum(bf.wickets) AS wickets, sum(bf.runs) as runs, count(0) AS `innings`, sum(`bf`.`balls`) AS `balls`, sum(`bf`.`maidens`) AS `maidens`, count((case when ((`bf`.`wickets` >= 5) and (`bf`.`wickets` < 10)) then 1 end)) AS `fifers`, count((case when (`bf`.`wickets` = 10) then 1 end)) AS `tenWickets` from bowling_figures bf " +
                "inner join match_player_map mpm on mpm.id = bf.match_player_id " +
                "inner join players p on p.id = mpm.player_id " +
                "inner join matches m on m.id = mpm.match_id " +
                "inner join series s on s.id = m.series_id " +
                "inner join stadiums st on st.id = m.stadium_id " +
                "inner join teams t on t.id = mpm.team_id";

        String countQuery = "select count(distinct p.id) as count from bowling_figures bf " +
                "inner join match_player_map mpm on mpm.id = bf.match_player_id " +
                "inner join players p on p.id = mpm.player_id " +
                "inner join matches m on m.id = mpm.match_id " +
                "inner join series s on s.id = m.series_id " +
                "inner join stadiums st on st.id = m.stadium_id " +
                "inner join teams t on t.id = mpm.team_id";

        //where
        List<String> whereQueryParts = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry: filterRequest.getFilters().entrySet())
        {
            String field = entry.getKey();
            List<String> valueList = entry.getValue();

            String fieldNameWithTablePrefix = getFieldNameWithTablePrefix(field);
            if(!fieldNameWithTablePrefix.isEmpty() && !valueList.isEmpty())
            {
                whereQueryParts.add(fieldNameWithTablePrefix + " in (" + String.join(", ", valueList) + ")");
            }
        }

        for(Map.Entry<String, Map<String, String>> entry: filterRequest.getRangeFilters().entrySet())
        {
            String field = entry.getKey();
            Map<String, String> rangeValues = entry.getValue();

            String fieldNameWithTablePrefix = getFieldNameWithTablePrefix(field);
            if(!fieldNameWithTablePrefix.isEmpty() && !rangeValues.isEmpty())
            {
                if(rangeValues.containsKey("from"))
                {
                    whereQueryParts.add(fieldNameWithTablePrefix + " >= " +  rangeValues.get("from"));
                }
                if(rangeValues.containsKey("to"))
                {
                    whereQueryParts.add(fieldNameWithTablePrefix + " <= " +  rangeValues.get("to"));
                }

            }
        }

        if(!whereQueryParts.isEmpty())
        {
            query += " where " + String.join(" and ", whereQueryParts);
            countQuery += " where " + String.join(" and ", whereQueryParts);
        }

        query += " group by playerId";

        //sort
        List<String> sortList = new ArrayList<>();
        for(Map.Entry<String, String> entry: filterRequest.getSortMap().entrySet())
        {
            String field = entry.getKey();
            String value = entry.getValue();

            String sortFieldName = getFieldNameForDisplay(field);
            if(!sortFieldName.isEmpty())
            {
                sortList.add(sortFieldName + " " + value);
            }
        }
        if(sortList.isEmpty())
        {
            sortList.add(getFieldNameForDisplay("wickets") + " desc");
        }
        query += " order by " + String.join(", ", sortList);

        //offset limit
        query += " limit " + Integer.min(30, filterRequest.getCount()) + " offset " + filterRequest.getOffset();

        String finalCountQuery = countQuery;
        jpaApi.withTransaction(em -> {
            List<Long> countResult = em.createNativeQuery(finalCountQuery).getResultList();
            statsResponse.setCount(countResult.get(0));
        });

        String finalQuery = query;
        jpaApi.withTransaction(em -> {
            List<Object[]> result = em.createNativeQuery(finalQuery).getResultList();
            for (Object[] row : result) {
                Long innings = (Long) row[4];
                if(innings > 0)
                {
                    Map<String, Object> stats = new HashMap<>();

                    stats.put("id", row[0]);
                    stats.put("name", row[1]);
                    stats.put("innings", innings);
                    stats.put("wickets", row[2]);
                    stats.put("runs", row[3]);
                    stats.put("balls", row[5]);
                    stats.put("maidens", row[6]);
                    stats.put("fifers", row[7]);
                    stats.put("tenWickets", row[8]);

                    statList.add(stats);
                }
            }
        });

        statsResponse.setStats(statList);

        return statsResponse;
    }

    public StatsResponse getFieldingStats(FilterRequest filterRequest) {
        StatsResponse statsResponse = new StatsResponse();
        List<Map<String, Object>> statList = new ArrayList<>();

        String query = "select p.id as playerId, p.name AS name, SUM(case when dm.name = 'Caught' and wk.id is null then 1 else 0 end) as `fielderCatches`, SUM(case when dm.name = 'Caught' and wk.id is not null then 1 else 0 end) as `keeperCatches`, SUM(case when dm.name = 'Stumped' then 1 else 0 end) as `stumpings`, SUM(case when dm.name = 'Run Out' then 1 else 0 end) as `runOuts` from fielder_dismissals fd " +
                "inner join match_player_map mpm on mpm.id = fd.match_player_id " +
                "inner join players p on p.id = mpm.player_id " +
                "inner join matches m on m.id = mpm.match_id " +
                "inner join series s on s.id = m.series_id " +
                "inner join stadiums st on st.id = m.stadium_id " +
                "inner join batting_scores bs on bs.id = fd.score_id " +
                "inner join dismissal_modes dm on dm.id = bs.dismissal_mode_id " +
                "inner join teams t on t.id = mpm.team_id " +
                "left join wicket_keepers wk on wk.match_player_id = fd.match_player_id";

        String countQuery = "select count(distinct p.id) as count from fielder_dismissals fd " +
                "inner join match_player_map mpm on mpm.id = fd.match_player_id " +
                "inner join players p on p.id = mpm.player_id " +
                "inner join matches m on m.id = mpm.match_id " +
                "inner join series s on s.id = m.series_id " +
                "inner join stadiums st on st.id = m.stadium_id " +
                "inner join batting_scores bs on bs.id = fd.score_id " +
                "inner join dismissal_modes dm on dm.id = bs.dismissal_mode_id " +
                "inner join teams t on t.id = mpm.team_id " +
                "left join wicket_keepers wk on wk.match_player_id = fd.match_player_id";

        //where
        List<String> whereQueryParts = new ArrayList<>(){
            {
                add(getFieldNameWithTablePrefix("playerName") + " != 'sub'");
            }
        };
        for(Map.Entry<String, List<String>> entry: filterRequest.getFilters().entrySet())
        {
            String field = entry.getKey();
            List<String> valueList = entry.getValue();

            String fieldNameWithTablePrefix = getFieldNameWithTablePrefix(field);
            if(!fieldNameWithTablePrefix.isEmpty() && !valueList.isEmpty())
            {
                whereQueryParts.add(fieldNameWithTablePrefix + " in (" + String.join(", ", valueList) + ")");
            }
        }

        for(Map.Entry<String, Map<String, String>> entry: filterRequest.getRangeFilters().entrySet())
        {
            String field = entry.getKey();
            Map<String, String> rangeValues = entry.getValue();

            String fieldNameWithTablePrefix = getFieldNameWithTablePrefix(field);
            if(!fieldNameWithTablePrefix.isEmpty() && !rangeValues.isEmpty())
            {
                if(rangeValues.containsKey("from"))
                {
                    whereQueryParts.add(fieldNameWithTablePrefix + " >= " +  rangeValues.get("from"));
                }
                if(rangeValues.containsKey("to"))
                {
                    whereQueryParts.add(fieldNameWithTablePrefix + " <= " +  rangeValues.get("to"));
                }

            }
        }

        if(!whereQueryParts.isEmpty())
        {
            query += " where " + String.join(" and ", whereQueryParts);
            countQuery += " where " + String.join(" and ", whereQueryParts);
        }

        query += " group by playerId";

        //sort
        List<String> sortList = new ArrayList<>();
        for(Map.Entry<String, String> entry: filterRequest.getSortMap().entrySet())
        {
            String field = entry.getKey();
            String value = entry.getValue();

            String sortFieldName = getFieldNameForDisplay(field);
            if(!sortFieldName.isEmpty())
            {
                sortList.add(sortFieldName + " " + value);
            }
        }
        if(sortList.isEmpty())
        {
            sortList.add(getFieldNameForDisplay("fielderCatches") + " desc");
        }
        query += " order by " + String.join(", ", sortList);

        //offset limit
        query += " limit " + Integer.min(30, filterRequest.getCount()) + " offset " + filterRequest.getOffset();

        String finalCountQuery = countQuery;
        jpaApi.withTransaction(em -> {
            List<Long> countResult = em.createNativeQuery(finalCountQuery).getResultList();
            statsResponse.setCount(countResult.get(0));
        });

        String finalQuery = query;
        jpaApi.withTransaction(em -> {
            List<Object[]> result = em.createNativeQuery(finalQuery).getResultList();
            for (Object[] row : result) {
                Map<String, Object> stats = new HashMap<>();

                stats.put("id", row[0]);
                stats.put("name", row[1]);
                stats.put("fielderCatches", row[2]);
                stats.put("keeperCatches", row[3]);
                stats.put("stumpings", row[4]);
                stats.put("runOuts", row[5]);

                statList.add(stats);
            }
        });

        statsResponse.setStats(statList);

        return statsResponse;
    }
}
