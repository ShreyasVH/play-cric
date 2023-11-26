package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.SeriesTeamsMap;
import com.playframework.cric.repositories.SeriesTeamsMapRepository;

import java.util.List;

public class SeriesTeamsMapService {
    private final SeriesTeamsMapRepository seriesTeamsMapRepository;

    @Inject
    public SeriesTeamsMapService(SeriesTeamsMapRepository seriesTeamsMapRepository) {
        this.seriesTeamsMapRepository = seriesTeamsMapRepository;
    }

    public void create(Long seriesId, List<Long> teamIds) {
        seriesTeamsMapRepository.create(seriesId, teamIds);
    }

    public List<SeriesTeamsMap> getBySeriesIds(List<Long> seriesIds) {
        return seriesTeamsMapRepository.getBySeriesIds(seriesIds);
    }

    public void delete(Long seriesId, List<Long> teamIds) {
        seriesTeamsMapRepository.delete(seriesId, teamIds);
    }

    public void remove(Long seriesId)
    {
        seriesTeamsMapRepository.remove(seriesId);
    }
}
