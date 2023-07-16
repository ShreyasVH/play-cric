package com.playframework.cric.repositories;

import io.ebean.DB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.playframework.cric.models.Tour;
import com.playframework.cric.requests.tours.CreateRequest;
import com.playframework.cric.utils.Utils;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;

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

    public List<Tour> getByIds(List<Long> ids) {
        return DB.find(Tour.class).where().in("id", ids).findList();
    }

    public List<Tour> getAll(int year, int page, int limit) {
        LocalDateTime startTime = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime endTime = startTime.plusYears(1L);

        return DB.find(Tour.class)
                .where()
                .ge("startTime", startTime)
                .le("startTime", endTime)
                .orderBy("startTime asc")
                .setFirstRow((page - 1) * limit)
                .setMaxRows(limit)
                .findList();
    }

    public int getTotalCountForYear(int year) {
        LocalDateTime startTime = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime endTime = startTime.plusYears(1L);
        return DB.find(Tour.class)
                .where()
                .ge("startTime", startTime)
                .le("startTime", endTime)
                .findCount();
    }

    public List<Integer> getAllYears() {
        String query = "SELECT DISTINCT YEAR(start_time) AS year FROM tours ORDER BY year DESC";
        SqlQuery sqlQuery = DB.sqlQuery(query);
        List<SqlRow> result = sqlQuery.findList();

        List<Integer> years = new ArrayList<>();

        for (SqlRow row: result) {
            years.add(row.getInteger("year"));
        }

        return years;
    }
}