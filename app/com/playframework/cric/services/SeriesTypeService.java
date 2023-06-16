package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.repositories.SeriesTypeRepository;
import com.playframework.cric.models.SeriesType;
import com.playframework.cric.exceptions.ConflictException;

public class SeriesTypeService {
    private final SeriesTypeRepository seriesTypeRepository;

    @Inject
    public SeriesTypeService(SeriesTypeRepository seriesTypeRepository) {
        this.seriesTypeRepository = seriesTypeRepository;
    }

    public SeriesType getById(Integer id) {
        return seriesTypeRepository.getById(id);
    }
}