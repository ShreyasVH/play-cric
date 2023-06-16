package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.SeriesType;

import java.util.List;

public class SeriesTypeRepository {
    public SeriesType getById(Integer seriesTypeId) {
        return DB.find(SeriesType.class).where().eq("id", seriesTypeId).findOne();
    }

    public List<SeriesType> getByIds(List<Integer> seriesTypeIds) {
        return DB.find(SeriesType.class).where().in("id", seriesTypeIds).findList();
    }
}