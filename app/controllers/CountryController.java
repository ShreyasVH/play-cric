package com.playframework.cric.controllers;

import play.mvc.Controller;
import com.google.inject.Inject;
import play.mvc.Result;
import play.libs.Json;
import play.mvc.Http;
import java.util.List;
import java.util.stream.Collectors;

import com.playframework.cric.requests.countries.CreateRequest;
import com.playframework.cric.responses.CountryResponse;
import com.playframework.cric.responses.Response;
import com.playframework.cric.responses.PaginatedResponse;
import com.playframework.cric.services.CountryService;
import com.playframework.cric.utils.Utils;
import com.playframework.cric.models.Country;

public class CountryController extends Controller {
	private final CountryService countryService;

	@Inject
	public CountryController (CountryService countryService) {
		this.countryService = countryService;
	}

	public Result create(Http.Request request) {
		CreateRequest createRequest = Utils.convertObject(request.body().asJson(), CreateRequest.class);

		Country country = countryService.create(createRequest);

		return created(Json.toJson(new Response(new CountryResponse(country))));
	}

	public Result searchByName(String name) {
		List<Country> countries = countryService.searchByName(name);
		return ok(Json.toJson(new Response(countries.stream().map(country -> new CountryResponse(country)).collect(Collectors.toList()))));
	}

	public Result getAll(int page, int limit) {
		List<Country> countries = countryService.getAll(page, limit);
		int totalCount = countryService.getTotalCount();
		List<CountryResponse> countryResponses = countries.stream().map(country -> new CountryResponse(country)).collect(Collectors.toList());
		PaginatedResponse<CountryResponse> paginatedResponse = new PaginatedResponse(totalCount, countryResponses, page, limit);
		return ok(Json.toJson(new Response(paginatedResponse)));
	}
}