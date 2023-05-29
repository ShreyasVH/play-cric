package com.playframework.cric.responses;

import lombok.NoArgsConstructor;
import lombok.Data;

import com.playframework.cric.models.Country;

@Data
@NoArgsConstructor
public class CountryResponse {
	private Long id;
	private String name;

	public CountryResponse(Country country) {
		this.id = country.getId();
		this.name = country.getName();
	}
}