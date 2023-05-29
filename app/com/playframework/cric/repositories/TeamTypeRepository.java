package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.TeamType;

public class TeamTypeRepository {
    public TeamType getById(Integer teamTypeId) {
        return DB.find(TeamType.class).where().eq("id", teamTypeId).findOne();
    }
}