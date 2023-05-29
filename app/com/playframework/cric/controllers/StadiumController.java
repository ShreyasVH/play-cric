package com.playframework.cric.controllers;

import play.mvc.Controller;
import com.google.inject.Inject;
import play.mvc.Result;
import play.libs.Json;
import play.mvc.Http;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.playframework.cric.requests.stadiums.CreateRequest;
import com.playframework.cric.responses.StadiumResponse;
import com.playframework.cric.responses.CountryResponse;
import com.playframework.cric.responses.Response;
import com.playframework.cric.responses.PaginatedResponse;
import com.playframework.cric.services.StadiumService;
import com.playframework.cric.services.CountryService;
import com.playframework.cric.utils.Utils;
import com.playframework.cric.models.Stadium;
import com.playframework.cric.models.Country;
import com.playframework.cric.exceptions.NotFoundException;

public class StadiumController extends Controller {
	private final StadiumService stadiumService;
	private final CountryService countryService;

	@Inject
	public StadiumController (StadiumService stadiumService, CountryService countryService) {
		this.stadiumService = stadiumService;
		this.countryService = countryService;
	}

	public Result create(Http.Request request) {
		CreateRequest createRequest = Utils.convertObject(request.body().asJson(), CreateRequest.class);

		Country country = countryService.getById(createRequest.getCountryId());
		if(null == country)
		{
			throw new NotFoundException("Country");
		}

		Stadium stadium = stadiumService.create(createRequest);

		return created(Json.toJson(new Response(new StadiumResponse(stadium, new CountryResponse(country)))));
	}

    public Result getAll(int page, int limit) {
        List<Stadium> stadiums = stadiumService.getAll(page, limit);
        int totalCount = 0;
        if(page == 1) {
            totalCount = stadiumService.getTotalCount();
        }
        List<Long> countryIds = stadiums.stream().map(Stadium::getCountryId).collect(Collectors.toList());
        List<Country> countries = countryService.getByIds(countryIds);
        Map<Long, Country> countryMap = countries.stream().collect(Collectors.toMap(Country::getId, country -> country));

        List<StadiumResponse> stadiumResponses = stadiums.stream().map(stadium -> new StadiumResponse(stadium, new CountryResponse(countryMap.get(stadium.getCountryId())))).collect(Collectors.toList());
        PaginatedResponse<StadiumResponse> paginatedResponse = new PaginatedResponse(totalCount, stadiumResponses, page, limit);
        return ok(Json.toJson(new Response(paginatedResponse)));
    }
}