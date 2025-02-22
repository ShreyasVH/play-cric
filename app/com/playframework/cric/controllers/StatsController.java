package com.playframework.cric.controllers;

import com.playframework.cric.models.Country;
import com.playframework.cric.requests.FilterRequest;
import com.playframework.cric.requests.countries.CreateRequest;
import com.playframework.cric.responses.CountryResponse;
import com.playframework.cric.responses.Response;
import com.playframework.cric.responses.StatsResponse;
import com.playframework.cric.services.StatsService;
import com.playframework.cric.utils.Utils;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

public class StatsController extends Controller {
    private final StatsService statsService;

    @Inject
    public StatsController(StatsService statsService)
    {
        this.statsService = statsService;
    }

    public Result getStats(Http.Request request) {
        FilterRequest filterRequest = Utils.convertObject(request.body().asJson(), FilterRequest.class);

        StatsResponse statsResponse = statsService.getStats(filterRequest);

        return ok(Json.toJson(new Response(statsResponse)));
    }
}
