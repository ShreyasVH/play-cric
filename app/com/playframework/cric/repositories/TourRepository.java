package com.playframework.cric.repositories;

import io.ebean.DB;

import java.time.LocalDateTime;

import com.playframework.cric.models.Tour;
import com.playframework.cric.requests.tours.CreateRequest;
import com.playframework.cric.utils.Utils;

public class TourRepository {
    public Tour create(CreateRequest createRequest) {
        Tour tour = Utils.convertObject(createRequest, Tour.class);
        DB.save(tour);
        return tour;
    }

    public Tour getByNameAndStartTime(String name, LocalDateTime startTime) {
        return DB.find(Tour.class).where().eq("name", name).eq("startTime", startTime).findOne();
    }

    public Tour getById(Long id) {
        return DB.find(Tour.class).where().eq("id", id).findOne();
    }
}