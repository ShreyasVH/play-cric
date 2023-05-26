package com.playframework.cric.repositories;

import io.ebean.DB;
import java.util.List;

import com.playframework.cric.models.Country;
import com.playframework.cric.requests.countries.CreateRequest;
import com.playframework.cric.exceptions.BadRequestException;
import com.playframework.cric.utils.Utils;

public class CountryRepository {
	public Country create(CreateRequest createRequest) {
        Country country = Utils.convertObject(createRequest, Country.class);
        DB.save(country);
        return country;
    }

    public Country getById(Long id) {
        return DB.find(Country.class).where().eq("id", id).findOne();
    }

    public Country getByName(String name) {
        return DB.find(Country.class).where().eq("name", name).findOne();
    }

    public List<Country> getByNamePattern(String name) {
        return DB.find(Country.class).where().ilike("name", "%" + name + "%").findList();
    }

    public List<Country> getAll(int page, int limit) {
        return DB.find(Country.class)
            .orderBy("name asc")
            .setFirstRow((page - 1) * limit)
            .setMaxRows(limit)
            .findList();
    }

    public int getTotalCount() {
        return DB.find(Country.class).findCount();
    }
}