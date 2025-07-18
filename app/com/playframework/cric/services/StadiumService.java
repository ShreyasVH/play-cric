package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.repositories.StadiumRepository;
import com.playframework.cric.requests.stadiums.CreateRequest;
import com.playframework.cric.models.Stadium;
import com.playframework.cric.exceptions.ConflictException;

public class StadiumService {
	private final StadiumRepository stadiumRepository;

	@Inject
	public StadiumService(StadiumRepository stadiumRepository) {
		this.stadiumRepository = stadiumRepository;
	}

	public Stadium create(CreateRequest createRequest) {
		createRequest.validate();

		Stadium existingStadium = stadiumRepository.getByNameAndCountryIdAndCity(createRequest.getName(), createRequest.getCountryId(), createRequest.getCity());

		if(null != existingStadium) {
			throw new ConflictException("Stadium");
		}

		return stadiumRepository.create(createRequest);
	}

	public List<Stadium> getAll(int page, int limit) {
		return stadiumRepository.getAll(page, limit);
	}

	public int getTotalCount() {
		return stadiumRepository.getTotalCount();
	}

	public Stadium getById(Long id)
	{
		return stadiumRepository.getById(id);
	}

	public List<Stadium> getByIds(List<Long> ids)
	{
		return stadiumRepository.getByIds(ids);
	}
}