package com.playframework.cric.services;

import com.google.inject.Inject;

import com.playframework.cric.repositories.TourRepository;
import com.playframework.cric.requests.tours.CreateRequest;
import com.playframework.cric.models.Tour;
import com.playframework.cric.exceptions.ConflictException;

public class TourService {
    private final TourRepository tourRepository;

    @Inject
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public Tour create(CreateRequest createRequest) {
        createRequest.validate();

        Tour existingTour = tourRepository.getByNameAndStartTime(createRequest.getName(), createRequest.getStartTime());

        if(null != existingTour) {
            throw new ConflictException("Tour");
        }

        return tourRepository.create(createRequest);
    }
}