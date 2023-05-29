package com.playframework.cric.repositories;

import com.playframework.cric.models.Stadium;
import io.ebean.DB;
import java.util.List;

import com.playframework.cric.models.Team;
import com.playframework.cric.requests.teams.CreateRequest;
import com.playframework.cric.utils.Utils;

public class TeamRepository {
    public Team create(CreateRequest createRequest) {
        Team team = Utils.convertObject(createRequest, Team.class);
        DB.save(team);
        return team;
    }

    public Team getByNameAndCountryIdAndTeamTypeId(String name, Long countryId, Integer teamTypeId) {
        return DB.find(Team.class).where().eq("name", name).eq("countryId", countryId).eq("typeId", teamTypeId).findOne();
    }

    public List<Team> getAll(int page, int limit) {
        return DB.find(Team.class)
                .orderBy("name asc")
                .setFirstRow((page - 1) * limit)
                .setMaxRows(limit)
                .findList();
    }

    public int getTotalCount() {
        return DB.find(Team.class).findCount();
    }
}