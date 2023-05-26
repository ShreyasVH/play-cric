package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.repositories.CountryRepository;
import com.playframework.cric.requests.countries.CreateRequest;
import com.playframework.cric.models.Country;
import com.playframework.cric.exceptions.ConflictException;

public class CountryService {
	private final CountryRepository countryRepository;

	@Inject
	public CountryService(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public Country create(CreateRequest createRequest) {
		createRequest.validate();

		Country existingCountry = countryRepository.getByName(createRequest.getName());

		if(null != existingCountry) {
			throw new ConflictException("Country");
		}

		return countryRepository.create(createRequest);
	}

	public Country getById(Long id) {
		return countryRepository.getById(id);
	}

	public List<Country> searchByName(String name) {
		return countryRepository.getByNamePattern(name);
	}

	public List<Country> getAll(int page, int limit) {
		return countryRepository.getAll(page, limit);
	}

	public int getTotalCount() {
		return countryRepository.getTotalCount();
	}
}