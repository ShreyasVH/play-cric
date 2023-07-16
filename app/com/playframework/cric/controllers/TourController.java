package com.playframework.cric.controllers;

import com.playframework.cric.responses.*;
import play.mvc.Controller;
import com.google.inject.Inject;
import play.mvc.Result;
import play.libs.Json;
import play.mvc.Http;

import com.playframework.cric.requests.tours.CreateRequest;
import com.playframework.cric.services.TourService;
import com.playframework.cric.models.Tour;

import java.util.List;
import java.util.stream.Collectors;

public class TourController extends Controller {
    private final TourService tourService;

    @Inject
    public TourController (TourService tourService) {
        this.tourService = tourService;
    }

    public Result create(Http.Request request) {
        CreateRequest createRequest = Json.fromJson(request.body().asJson(), CreateRequest.class);

        Tour tour = tourService.create(createRequest);

        return created(Json.toJson(new Response(new TourResponse(tour))));
    }

    public Result getAllForYear(int year, int page, int limit) {
        List<Tour> tours = tourService.getAllForYear(year, page, limit);
        int totalCount = 0;
        if(page == 1) {
            totalCount = tourService.getTotalCountForYear(year);
        }

        List<TourResponse> tourResponses = tours.stream().map(TourResponse::new).collect(Collectors.toList());
        PaginatedResponse<TourResponse> paginatedResponse = new PaginatedResponse<>(totalCount, tourResponses, page, limit);
        return ok(Json.toJson(new Response(paginatedResponse)));
    }

    public Result getAllYears()
    {
        List<Integer> years = tourService.getAllYears();
        return ok(Json.toJson(new Response(years)));
    }
}