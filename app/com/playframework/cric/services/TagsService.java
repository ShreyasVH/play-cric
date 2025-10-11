package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.models.Tag;
import com.playframework.cric.repositories.TagsRepository;

public class TagsService {
    private final TagsRepository tagsRepository;

    @Inject
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<Tag> getAll(int page, int limit) {
        return tagsRepository.getAll(page, limit);
    }

    public int getTotalCount() {
        return tagsRepository.getTotalCount();
    }

    public List<Tag> get(List<Integer> ids)
    {
         return tagsRepository.getByIds(ids);
    }
}