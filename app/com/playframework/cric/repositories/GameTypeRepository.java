package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.GameType;

import java.util.List;

public class GameTypeRepository {
    public GameType getById(Integer gameTypeId) {
        return DB.find(GameType.class).where().eq("id", gameTypeId).findOne();
    }

    public List<GameType> getByIds(List<Integer> gameTypeIds) {
        return DB.find(GameType.class).where().in("id", gameTypeIds).findList();
    }
}