package com.playframework.cric.repositories;

import io.ebean.DB;
import java.util.List;

import com.playframework.cric.models.Stadium;
import com.playframework.cric.requests.stadiums.CreateRequest;
import com.playframework.cric.utils.Utils;

public class StadiumRepository {
	public Stadium create(CreateRequest createRequest) {
        Stadium stadium = Utils.convertObject(createRequest, Stadium.class);
        DB.save(stadium);
        return stadium;
    }

    public Stadium getByNameAndCountryId(String name, Long countryId) {
        return DB.find(Stadium.class).where().eq("name", name).eq("countryId", countryId).findOne();
    }

    public List<Stadium> getAll(int page, int limit) {
        return DB.find(Stadium.class)
                .orderBy("name asc")
                .setFirstRow((page - 1) * limit)
                .setMaxRows(limit)
                .findList();
    }

    public int getTotalCount() {
        return DB.find(Stadium.class).findCount();
    }

    public Stadium getById(Long id)
    {
        return DB.find(Stadium.class).where().eq("id", id).findOne();
    }
}