package com.playframework.cric.repositories;

import io.ebean.DB;
import java.util.List;

import com.playframework.cric.models.TeamType;

public class TeamTypeRepository {
    public TeamType getById(Integer teamTypeId) {
        return DB.find(TeamType.class).where().eq("id", teamTypeId).findOne();
    }

    public List<TeamType> getByIds(List<Integer> teamTypeIds) {
        return DB.find(TeamType.class).where().in("id", teamTypeIds).findList();
    }
}