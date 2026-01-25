package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.SeriesTeamsMap;
import com.playframework.cric.repositories.SeriesTeamsMapRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SeriesTeamsMapService {
    private final SeriesTeamsMapRepository seriesTeamsMapRepository;

    @Inject
    public SeriesTeamsMapService(SeriesTeamsMapRepository seriesTeamsMapRepository) {
        this.seriesTeamsMapRepository = seriesTeamsMapRepository;
    }

    public void create(Integer seriesId, List<Long> teamIds) {
        seriesTeamsMapRepository.create(seriesId, teamIds);
    }

    public void create(EntityManager em, Integer seriesId, List<Long> teamIds) {
        seriesTeamsMapRepository.create(em, seriesId, teamIds);
    }

    public List<SeriesTeamsMap> getBySeriesIds(List<Integer> seriesIds) {
        return seriesTeamsMapRepository.getBySeriesIds(seriesIds);
    }

    public void delete(Integer seriesId, List<Long> teamIds) {
        seriesTeamsMapRepository.delete(seriesId, teamIds);
    }

    public void delete(EntityManager em, Integer seriesId, List<Long> teamIds) {
        seriesTeamsMapRepository.delete(em, seriesId, teamIds);
    }

    public void remove(Integer seriesId)
    {
        seriesTeamsMapRepository.remove(seriesId);
    }

    public void remove(EntityManager em, Integer seriesId)
    {
        seriesTeamsMapRepository.remove(em, seriesId);
    }
}
