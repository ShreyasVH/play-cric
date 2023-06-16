package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.SeriesType;

public class SeriesTypeRepository {
    public SeriesType getById(Integer seriesTypeId) {
        return DB.find(SeriesType.class).where().eq("id", seriesTypeId).findOne();
    }
}