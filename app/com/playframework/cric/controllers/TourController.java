package com.playframework.cric.controllers;

import com.playframework.cric.exceptions.NotFoundException;
import com.playframework.cric.models.GameType;
import com.playframework.cric.models.Series;
import com.playframework.cric.responses.*;
import com.playframework.cric.services.GameTypeService;
import com.playframework.cric.services.SeriesService;
import play.mvc.Controller;
import com.google.inject.Inject;
import play.mvc.Result;
import play.libs.Json;
import play.mvc.Http;

import com.playframework.cric.requests.tours.CreateRequest;
import com.playframework.cric.services.TourService;
import com.playframework.cric.models.Tour;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TourController extends Controller {
    private final TourService tourService;
    private final SeriesService seriesService;
    private final GameTypeService gameTypeService;

    @Inject
    public TourController (TourService tourService, SeriesService seriesService, GameTypeService gameTypeService) {
        this.tourService = tourService;
        this.seriesService = seriesService;
        this.gameTypeService = gameTypeService;
    }

    public Result create(Http.Request request) {
        CreateRequest createRequest = Json.fromJson(request.body().asJson(), CreateRequest.class);

        Tour tour = tourService.create(createRequest);

        return created(Json.toJson(new Response(new TourMiniResponse(tour))));
    }

    public Result getAllForYear(int year, int page, int limit) {
        List<Tour> tours = tourService.getAllForYear(year, page, limit);
        int totalCount = 0;
        if(page == 1) {
            totalCount = tourService.getTotalCountForYear(year);
        }

        List<TourMiniResponse> tourResponses = tours.stream().map(TourMiniResponse::new).collect(Collectors.toList());
        PaginatedResponse<TourMiniResponse> paginatedResponse = new PaginatedResponse<>(totalCount, tourResponses, page, limit);
        return ok(Json.toJson(new Response(paginatedResponse)));
    }

    public Result getAllYears()
    {
        List<Integer> years = tourService.getAllYears();
        return ok(Json.toJson(new Response(years)));
    }

    public Result getById(long id)
    {
        Tour tour = tourService.getById(id);
        if(null == tour)
        {
            throw new NotFoundException("Tour");
        }

        TourResponse tourResponse = new TourResponse(tour);
        List<Series> seriesList = seriesService.getByTourId(id);

        List<Integer> gameTypeIds = seriesList.stream().map(Series::getGameTypeId).collect(Collectors.toList());
        List<GameType> gameTypes = gameTypeService.getByIds(gameTypeIds);
        Map<Integer, GameType> gameTypeMap = gameTypes.stream().collect(Collectors.toMap(GameType::getId, gameType -> gameType));

        List<SeriesMiniResponse> seriesMiniResponses = seriesList.stream().map(series -> new SeriesMiniResponse(series, gameTypeMap.get(series.getGameTypeId()))).collect(Collectors.toList());
        tourResponse.setSeriesList(seriesMiniResponses);

        return ok(Json.toJson(new Response(tourResponse)));
    }
}