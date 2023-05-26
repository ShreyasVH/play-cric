package com.playframework.cric.responses;

import lombok.NoArgsConstructor;
import lombok.Data;

import com.playframework.cric.models.Stadium;
import com.playframework.cric.models.Country;

@Data
@NoArgsConstructor
public class StadiumResponse {
	private Long id;
	private String name;
	private String city;
    private String state;
    private CountryResponse country;

	public StadiumResponse(Stadium stadium, CountryResponse country) {
		this.id = stadium.getId();
		this.name = stadium.getName();
		this.city = stadium.getCity();
		this.state = stadium.getState();
		this.country = country;
	}
}