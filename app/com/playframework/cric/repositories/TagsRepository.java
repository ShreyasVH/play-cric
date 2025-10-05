package com.playframework.cric.repositories;

import com.playframework.cric.models.Tag;
import io.ebean.DB;

import java.util.List;

public class TagsRepository {
    public List<Tag> getAll()
    {
        return DB.find(Tag.class).findList();
    }

    public Tag getById(Integer typeId) {
        return DB.find(Tag.class).where().eq("id", typeId).findOne();
    }

    public List<Tag> getByIds(List<Integer> typeIds) {
        return DB.find(Tag.class).where().in("id", typeIds).findList();
    }
}