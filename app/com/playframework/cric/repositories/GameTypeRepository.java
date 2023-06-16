package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.GameType;

public class GameTypeRepository {
    public GameType getById(Integer gameTypeId) {
        return DB.find(GameType.class).where().eq("id", gameTypeId).findOne();
    }
}