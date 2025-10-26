package com.playframework.cric.repositories;

import com.playframework.cric.models.Tag;
import io.ebean.DB;

import java.util.List;

public class TagsRepository {
    public List<Tag> getAll(int page, int limit)
    {
        return DB.find(Tag.class)
            .orderBy("name asc")
            .setFirstRow((page - 1) * limit)
            .setMaxRows(limit)
            .findList();
    }

    public int getTotalCount() {
        return DB.find(Tag.class).findCount();
    }

    public Tag getById(Integer typeId) {
        return DB.find(Tag.class).where().eq("id", typeId).findOne();
    }

    public List<Tag> getByIds(List<Integer> typeIds) {
        return DB.find(Tag.class).where().in("id", typeIds).findList();
    }
}