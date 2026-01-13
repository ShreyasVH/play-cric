package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.TagMap;
import com.playframework.cric.repositories.TagMapRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TagMapService {
    private final TagMapRepository tagMapRepository;

    @Inject
    public TagMapService(TagMapRepository tagMapRepository) {
        this.tagMapRepository = tagMapRepository;
    }

    public void create(Integer entityId, List<Integer> tagIds, String tagEntityType) {
        tagMapRepository.create(entityId, tagIds, tagEntityType);
    }

    public void create(EntityManager em, Integer entityId, List<Integer> tagIds, String tagEntityType) {
        tagMapRepository.create(em, entityId, tagIds, tagEntityType);
    }

    public List<TagMap> get(Integer entityId, String tagEntityType)
    {
        return tagMapRepository.get(entityId, tagEntityType);
    }

    public void remove(Integer entityId, String tagEntityType)
    {
        tagMapRepository.remove(entityId, tagEntityType);
    }

    public void remove(EntityManager em, Integer entityId, String tagEntityType)
    {
        tagMapRepository.remove(em, entityId, tagEntityType);
    }
}
