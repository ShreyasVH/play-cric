package com.playframework.cric.controllers;

import play.mvc.Controller;
import com.google.inject.Inject;
import play.mvc.Result;
import play.libs.Json;
import play.mvc.Http;
import java.util.List;
import java.util.stream.Collectors;

import com.playframework.cric.requests.teams.CreateRequest;
import com.playframework.cric.responses.TeamResponse;
import com.playframework.cric.responses.CountryResponse;
import com.playframework.cric.responses.TeamTypeResponse;
import com.playframework.cric.responses.Response;
import com.playframework.cric.responses.PaginatedResponse;
import com.playframework.cric.services.TeamService;
import com.playframework.cric.services.CountryService;
import com.playframework.cric.services.TeamTypeService;
import com.playframework.cric.utils.Utils;
import com.playframework.cric.models.Team;
import com.playframework.cric.models.Country;
import com.playframework.cric.models.TeamType;
import com.playframework.cric.exceptions.NotFoundException;

public class TeamController extends Controller {
    private final TeamService teamService;
    private final CountryService countryService;
    private final TeamTypeService teamTypeService;

    @Inject
    public TeamController (TeamService teamService, CountryService countryService, TeamTypeService teamTypeService) {
        this.teamService = teamService;
        this.countryService = countryService;
        this.teamTypeService = teamTypeService;
    }

    public Result create(Http.Request request) {
        CreateRequest createRequest = Utils.convertObject(request.body().asJson(), CreateRequest.class);

        Country country = countryService.getById(createRequest.getCountryId());
        if(null == country) {
            throw new NotFoundException("Country");
        }

        TeamType teamType = teamTypeService.getById(createRequest.getTypeId());
        if(null == teamType) {
            throw new NotFoundException("Team type");
        }

        Team team = teamService.create(createRequest);

        return created(Json.toJson(new Response(new TeamResponse(team, new CountryResponse(country), new TeamTypeResponse(teamType)))));
    }
}