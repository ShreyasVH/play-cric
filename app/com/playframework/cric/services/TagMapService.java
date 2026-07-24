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

    public void create(Integer entityId, List<Integer> tagIds) {
        tagMapRepository.create(entityId, tagIds);
    }

    public void create(EntityManager em, Integer entityId, List<Integer> tagIds) {
        tagMapRepository.create(em, entityId, tagIds);
    }

    public List<TagMap> get(Integer entityId, List<Integer> tagIds)
    {
        return tagMapRepository.get(entityId, tagIds);
    }

    public void remove(Integer entityId, List<Integer> tagIds)
    {
        tagMapRepository.remove(entityId, tagIds);
    }

    public void remove(EntityManager em, Integer entityId, List<Integer> tagIds)
    {
        tagMapRepository.remove(em, entityId, tagIds);
    }
}
