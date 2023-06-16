package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.ManOfTheSeries;
import com.playframework.cric.repositories.ManOfTheSeriesRepository;

import java.util.List;

public class ManOfTheSeriesService {
    private final ManOfTheSeriesRepository manOfTheSeriesRepository;

    @Inject
    public ManOfTheSeriesService(ManOfTheSeriesRepository manOfTheSeriesRepository) {
        this.manOfTheSeriesRepository = manOfTheSeriesRepository;
    }

    public List<ManOfTheSeries> getBySeriesIds(List<Long> seriesIds) {
        return manOfTheSeriesRepository.getBySeriesIds(seriesIds);
    }

    public void add(Long seriesId, List<Long> playerIds) {
        manOfTheSeriesRepository.add(seriesId, playerIds);
    }

    public void delete(Long seriesId, List<Long> playerIds) {
        manOfTheSeriesRepository.delete(seriesId, playerIds);
    }
}
