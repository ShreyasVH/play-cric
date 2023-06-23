package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.WinMarginType;

public class WinMarginTypeRepository {
    public WinMarginType getById(Integer typeId) {
        return DB.find(WinMarginType.class).where().eq("id", typeId).findOne();
    }
}