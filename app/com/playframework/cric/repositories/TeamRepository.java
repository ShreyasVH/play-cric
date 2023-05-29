package com.playframework.cric.repositories;

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
}