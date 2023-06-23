package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.ResultType;

public class ResultTypeRepository {
    public ResultType getById(Integer typeId) {
        return DB.find(ResultType.class).where().eq("id", typeId).findOne();
    }
}