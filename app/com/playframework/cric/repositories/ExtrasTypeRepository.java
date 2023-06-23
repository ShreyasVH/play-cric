package com.playframework.cric.repositories;

import io.ebean.DB;

import com.playframework.cric.models.ExtrasType;

import java.util.List;

public class ExtrasTypeRepository {
    public List<ExtrasType> getAll() {
        return DB.find(ExtrasType.class).findList();
    }
}