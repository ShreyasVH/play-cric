package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.models.Series;
import com.playframework.cric.repositories.SeriesRepository;
import com.playframework.cric.requests.series.CreateRequest;
import com.playframework.cric.exceptions.ConflictException;

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
}