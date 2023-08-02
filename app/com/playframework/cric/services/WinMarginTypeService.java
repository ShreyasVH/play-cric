package com.playframework.cric.services;

import com.google.inject.Inject;

import com.playframework.cric.repositories.WinMarginTypeRepository;
import com.playframework.cric.models.WinMarginType;

import java.util.List;

public class WinMarginTypeService {
    private final WinMarginTypeRepository winMarginTypeRepository;

    @Inject
    public WinMarginTypeService(WinMarginTypeRepository winMarginTypeRepository) {
        this.winMarginTypeRepository = winMarginTypeRepository;
    }

    public WinMarginType getById(Integer id) {
        return winMarginTypeRepository.getById(id);
    }

    public List<WinMarginType> getByIds(List<Integer> ids) {
        return winMarginTypeRepository.getByIds(ids);
    }
}