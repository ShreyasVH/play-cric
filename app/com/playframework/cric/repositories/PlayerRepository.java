package com.playframework.cric.repositories;

import com.playframework.cric.requests.FilterRequest;
import com.playframework.cric.responses.StatsResponse;
import io.ebean.DB;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.playframework.cric.models.Player;
import com.playframework.cric.requests.players.CreateRequest;
import com.playframework.cric.utils.Utils;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;

public class PlayerRepository {
    public Player create(CreateRequest createRequest) {
        Player player = Utils.convertObject(createRequest, Player.class);
        DB.save(player);
        return player;
    }

    public Player getByNameAndCountryIdAndDateOfBirth(String name, Long countryId, LocalDate dateOfBirth) {
        return DB.find(Player.class).where().eq("name", name).eq("countryId", countryId).eq("dateOfBirth", dateOfBirth).findOne();
    }

    public List<Player> getAll(int page, int limit) {
        return DB.find(Player.class)
                .orderBy("name asc")
                .setFirstRow((page - 1) * limit)
                .setMaxRows(limit)
                .findList();
    }

    public int getTotalCount() {
        return DB.find(Player.class).findCount();
    }

    public List<Player> getByIds(List<Long> ids) {
        return DB.find(Player.class).where().in("id", ids).findList();
    }

    public Player getById(Long id)
    {
        return DB.find(Player.class).where().eq("id", id).findOne();
    }

    public void remove(Long id)
    {
        DB.delete(getById(id));
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
        return DB.find(Player.class)
                .where()
                .icontains("name", keyword)
                .orderBy("name asc")
                .setFirstRow((page - 1) * limit)
                .setMaxRows(limit)
                .findList();
    }
    public int searchCount(String keyword) {
        return DB.find(Player.class)
                .where()
                .icontains("name", keyword)
                .findCount();
    }

    public StatsResponse getBattingStats(FilterRequest filterRequest) {
        StatsResponse statsResponse = new StatsResponse();
        List<Map<String, String>> statList = new ArrayList<>();
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

        SqlQuery sqlCountQuery = DB.sqlQuery(countQuery);
        List<SqlRow> countResult = sqlCountQuery.findList();
        statsResponse.setCount(countResult.get(0).getInteger("count"));

        SqlQuery sqlQuery = DB.sqlQuery(query);
        List<SqlRow> result = sqlQuery.findList();

        for (SqlRow row : result) {
            Integer innings = row.getInteger("innings");
            if (innings > 0) {
                Map<String, String> stats = new HashMap<>();

                stats.put("id", row.getString("playerId"));
                stats.put("name", row.getString("name"));
                stats.put("innings", innings.toString());
                stats.put("runs", row.getString("runs"));
                stats.put("balls", row.getString("balls"));
                stats.put("notOuts", row.getString("notouts"));
                stats.put("fours", row.getString("fours"));
                stats.put("sixes", row.getString("sixes"));
                stats.put("highest", row.getString("highest"));
                stats.put("fifties", row.getString("fifties"));
                stats.put("hundreds", row.getString("hundreds"));
                stats.put("twoHundreds", row.getString("twoHundreds"));
                stats.put("threeHundreds", row.getString("threeHundreds"));
                stats.put("fourHundreds", row.getString("fourHundreds"));

                statList.add(stats);
            }
        }

        statsResponse.setStats(statList);

        return statsResponse;
    }

    public StatsResponse getBowlingStats(FilterRequest filterRequest) {
        StatsResponse statsResponse = new StatsResponse();
        List<Map<String, String>> statList = new ArrayList<>();

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

        SqlQuery sqlCountQuery = DB.sqlQuery(countQuery);
        List<SqlRow> countResult = sqlCountQuery.findList();
        statsResponse.setCount(countResult.get(0).getInteger("count"));

        SqlQuery sqlQuery = DB.sqlQuery(query);
        List<SqlRow> result = sqlQuery.findList();

        for(SqlRow row: result)
        {
            Integer innings = row.getInteger("innings");
            if(innings > 0)
            {
                Map<String, String> stats = new HashMap<>();

                stats.put("id", row.getString("playerId"));
                stats.put("name", row.getString("name"));
                stats.put("innings", innings.toString());
                stats.put("wickets", row.getString("wickets"));
                stats.put("runs", row.getString("runs"));
                stats.put("balls", row.getString("balls"));
                stats.put("maidens", row.getString("maidens"));
                stats.put("fifers", row.getString("fifers"));
                stats.put("tenWickets", row.getString("tenWickets"));

                statList.add(stats);
            }
        }

        statsResponse.setStats(statList);

        return statsResponse;
    }

    public StatsResponse getFieldingStats(FilterRequest filterRequest) {
        StatsResponse statsResponse = new StatsResponse();
        List<Map<String, String>> statList = new ArrayList<>();

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

        SqlQuery sqlCountQuery = DB.sqlQuery(countQuery);
        List<SqlRow> countResult = sqlCountQuery.findList();
        statsResponse.setCount(countResult.get(0).getInteger("count"));

        SqlQuery sqlQuery = DB.sqlQuery(query);
        List<SqlRow> result = sqlQuery.findList();

        for(SqlRow row: result)
        {
            Map<String, String> stats = new HashMap<>();

            stats.put("id", row.getString("playerId"));
            stats.put("name", row.getString("name"));
            stats.put("fielderCatches", row.getString("fielderCatches"));
            stats.put("keeperCatches", row.getString("keeperCatches"));
            stats.put("stumpings", row.getString("stumpings"));
            stats.put("runOuts", row.getString("runOuts"));

            statList.add(stats);
        }

        statsResponse.setStats(statList);

        return statsResponse;
    }
}
