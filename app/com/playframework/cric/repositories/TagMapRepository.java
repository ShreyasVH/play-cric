package com.playframework.cric.repositories;

import com.playframework.cric.models.TagMap;
import io.ebean.DB;

import java.util.List;
import java.util.stream.Collectors;

public class TagMapRepository {
    public void create(Integer entityId, List<Integer> tagIds, String tagEntityType) {
        List<TagMap> tagMaps = tagIds.stream().map(tagId -> new TagMap(null, tagEntityType, entityId, tagId)).collect(Collectors.toList());
        DB.saveAll(tagMaps);
    }

    public List<TagMap> get(Integer entityId, String tagEntityType)
    {
        return DB.find(TagMap.class).where().eq("entityId", entityId).eq("entityType", tagEntityType).findList();
    }
}