package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.ResultType;

import java.util.List;

public class ResultTypeRepository {
    public ResultType getById(Integer typeId) {
        return DB.find(ResultType.class).where().eq("id", typeId).findOne();
    }

    public List<ResultType> getByIds(List<Integer> typeIds) {
        return DB.find(ResultType.class).where().in("id", typeIds).findList();
    }
}