package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.ManOfTheSeries;
import com.playframework.cric.repositories.ManOfTheSeriesRepository;
import com.playframework.cric.requests.players.MergeRequest;

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

    public void remove(Long seriesId)
    {
        manOfTheSeriesRepository.remove(seriesId);
    }

    public void merge(MergeRequest mergeRequest)
    {
        manOfTheSeriesRepository.merge(mergeRequest);
    }
}
