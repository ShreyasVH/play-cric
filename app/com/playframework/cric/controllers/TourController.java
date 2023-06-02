package com.playframework.cric.controllers;

import play.mvc.Controller;
import com.google.inject.Inject;
import play.mvc.Result;
import play.libs.Json;
import play.mvc.Http;

import com.playframework.cric.requests.tours.CreateRequest;
import com.playframework.cric.responses.TourResponse;
import com.playframework.cric.responses.Response;
import com.playframework.cric.services.TourService;
import com.playframework.cric.models.Tour;

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
}