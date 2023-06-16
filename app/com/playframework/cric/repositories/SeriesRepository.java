package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.Series;
import com.playframework.cric.requests.series.CreateRequest;
import com.playframework.cric.utils.Utils;

import java.util.List;

public class SeriesRepository {
    public Series create(CreateRequest createRequest) {
        Series series = Utils.convertObject(createRequest, Series.class);
        DB.save(series);
        return series;
    }

    public Series getByNameAndTourIdAndGameTypeId(String name, Long tourId, Integer gameTypeId) {
        return DB.find(Series.class).where().eq("name", name).eq("tourId", tourId).eq("gameTypeId", gameTypeId).findOne();
    }

    public List<Series> getAll(int page, int limit) {
        return DB.find(Series.class)
                .orderBy("name asc")
                .setFirstRow((page - 1) * limit)
                .setMaxRows(limit)
                .findList();
    }

    public int getTotalCount() {
        return DB.find(Series.class).findCount();
    }
}