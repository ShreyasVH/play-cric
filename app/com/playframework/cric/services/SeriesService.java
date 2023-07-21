package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.models.Series;
import com.playframework.cric.repositories.SeriesRepository;
import com.playframework.cric.requests.series.CreateRequest;
import com.playframework.cric.exceptions.ConflictException;
import com.playframework.cric.requests.series.UpdateRequest;

public class SeriesService {
    private final SeriesRepository seriesRepository;

    @Inject
    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public Series create(CreateRequest createRequest) {
        createRequest.validate();

        Series existingSeries = seriesRepository.getByNameAndTourIdAndGameTypeId(createRequest.getName(), createRequest.getTourId(), createRequest.getGameTypeId());
        if(null != existingSeries) {
            throw new ConflictException("Series");
        }

        return seriesRepository.create(createRequest);
    }

    public List<Series> getAll(int page, int limit) {
        return seriesRepository.getAll(page, limit);
    }

    public int getTotalCount() {
        return seriesRepository.getTotalCount();
    }

    public Series getById(Long id) {
        return seriesRepository.getById(id);
    }

    public Series update(Series existingSeries, UpdateRequest updateRequest) {
        boolean isUpdateRequired = false;
        if (null != updateRequest.getName() && !updateRequest.getName().equals(existingSeries.getName())) {
            isUpdateRequired = true;
            existingSeries.setName(updateRequest.getName());
        }

        if (null != updateRequest.getHomeCountryId() && !updateRequest.getHomeCountryId().equals(existingSeries.getHomeCountryId())) {
            isUpdateRequired = true;
            existingSeries.setHomeCountryId(updateRequest.getHomeCountryId());
        }

        if (null != updateRequest.getTourId() && !updateRequest.getTourId().equals(existingSeries.getTourId())) {
            isUpdateRequired = true;
            existingSeries.setTourId(updateRequest.getTourId());
        }

        if (null != updateRequest.getTypeId() && !updateRequest.getTypeId().equals(existingSeries.getTypeId())) {
            isUpdateRequired = true;
            existingSeries.setTypeId(updateRequest.getTypeId());
        }

        if (null != updateRequest.getGameTypeId() && !updateRequest.getGameTypeId().equals(existingSeries.getGameTypeId())) {
            isUpdateRequired = true;
            existingSeries.setGameTypeId(updateRequest.getGameTypeId());
        }

        if (null != updateRequest.getStartTime() && !updateRequest.getStartTime().equals(existingSeries.getStartTime())) {
            isUpdateRequired = true;
            existingSeries.setStartTime(updateRequest.getStartTime());
        }

        if (isUpdateRequired) {
            seriesRepository.update(existingSeries);
        }

        return existingSeries;
    }

    public List<Series> getByTourId(long tourId)
    {
        return seriesRepository.getByTourId(tourId);
    }
}