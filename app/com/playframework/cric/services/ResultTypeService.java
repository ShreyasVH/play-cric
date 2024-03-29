package com.playframework.cric.services;

import com.google.inject.Inject;

import com.playframework.cric.repositories.ResultTypeRepository;
import com.playframework.cric.models.ResultType;

import java.util.List;

public class ResultTypeService {
    private final ResultTypeRepository resultTypeRepository;

    @Inject
    public ResultTypeService(ResultTypeRepository resultTypeRepository) {
        this.resultTypeRepository = resultTypeRepository;
    }

    public ResultType getById(Integer id) {
        return resultTypeRepository.getById(id);
    }

    public List<ResultType> getByIds(List<Integer> ids) {
        return resultTypeRepository.getByIds(ids);
    }
}