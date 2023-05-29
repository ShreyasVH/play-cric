package com.playframework.cric.controllers;

import com.playframework.cric.models.Stadium;
import com.playframework.cric.responses.*;
import play.mvc.Controller;
import com.google.inject.Inject;
import play.mvc.Result;
import play.libs.Json;
import play.mvc.Http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.playframework.cric.requests.teams.CreateRequest;
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

    public Result getAll(int page, int limit) {
        List<Team> teams = teamService.getAll(page, limit);
        int totalCount = 0;
        if(page == 1) {
            totalCount = teamService.getTotalCount();
        }

        List<Long> countryIds = new ArrayList<>();
        List<Integer> teamTypeIds = new ArrayList<>();

        for (Team team: teams) {
            countryIds.add(team.getCountryId());
            teamTypeIds.add(team.getTypeId());
        }

        List<Country> countries = countryService.getByIds(countryIds);
        Map<Long, Country> countryMap = countries.stream().collect(Collectors.toMap(Country::getId, country -> country));

        List<TeamType> teamTypes = teamTypeService.getByIds(teamTypeIds);
        Map<Integer, TeamType> teamTypeMap = teamTypes.stream().collect(Collectors.toMap(TeamType::getId, teamType -> teamType));

        List<TeamResponse> teamResponses = teams.stream().map(team -> new TeamResponse(team, new CountryResponse(countryMap.get(team.getCountryId())), new TeamTypeResponse(teamTypeMap.get(team.getTypeId())))).collect(Collectors.toList());
        PaginatedResponse<TeamResponse> paginatedResponse = new PaginatedResponse<>(totalCount, teamResponses, page, limit);
        return ok(Json.toJson(new Response(paginatedResponse)));
    }
}