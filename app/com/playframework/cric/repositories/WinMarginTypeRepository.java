package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.WinMarginType;

import java.util.List;

public class WinMarginTypeRepository {
    public WinMarginType getById(Integer typeId) {
        return DB.find(WinMarginType.class).where().eq("id", typeId).findOne();
    }

    public List<WinMarginType> getByIds(List<Integer> typeIds) {
        return DB.find(WinMarginType.class).where().in("id", typeIds).findList();
    }
}