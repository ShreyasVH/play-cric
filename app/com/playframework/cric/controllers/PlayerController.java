package com.playframework.cric.controllers;

import com.google.inject.Inject;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.playframework.cric.exceptions.NotFoundException;
import com.playframework.cric.models.Country;
import com.playframework.cric.models.Player;
import com.playframework.cric.requests.players.CreateRequest;
import com.playframework.cric.responses.CountryResponse;
import com.playframework.cric.responses.PaginatedResponse;
import com.playframework.cric.responses.PlayerResponse;
import com.playframework.cric.responses.Response;
import com.playframework.cric.services.CountryService;
import com.playframework.cric.services.PlayerService;
import com.playframework.cric.utils.Utils;

public class PlayerController extends Controller {
    private final PlayerService playerService;
    private final CountryService countryService;

    @Inject
    public PlayerController(PlayerService playerService, CountryService countryService) {
        this.playerService = playerService;
        this.countryService = countryService;
    }

    public Result create(Http.Request request) {
        CreateRequest createRequest = Utils.convertObject(request.body().asJson(), CreateRequest.class);

        Country country = countryService.getById(createRequest.getCountryId());
        if(null == country)
        {
            throw new NotFoundException("Country");
        }

        Player player = playerService.create(createRequest);

        return created(Json.toJson(new Response(new PlayerResponse(player, new CountryResponse(country)))));
    }

    public Result getAll(int page, int limit) {
        List<Player> players = playerService.getAll(page, limit);
        int totalCount = 0;
        if(page == 1) {
            totalCount = playerService.getTotalCount();
        }

        List<Long> countryIds = players.stream().map(Player::getCountryId).collect(Collectors.toList());

        List<Country> countries = countryService.getByIds(countryIds);
        Map<Long, Country> countryMap = countries.stream().collect(Collectors.toMap(Country::getId, country -> country));

        List<PlayerResponse> playerResponses = players.stream().map(player -> new PlayerResponse(player, new CountryResponse(countryMap.get(player.getCountryId())))).collect(Collectors.toList());
        PaginatedResponse<PlayerResponse> paginatedResponse = new PaginatedResponse<>(totalCount, playerResponses, page, limit);
        return ok(Json.toJson(new Response(paginatedResponse)));
    }
}
