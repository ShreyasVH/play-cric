package com.playframework.cric.requests.stadiums;

import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import com.playframework.cric.exceptions.BadRequestException;

@Data
@NoArgsConstructor
public class CreateRequest {
	private String name;
	private String city;
	private String state;
	private Long countryId;

	public void validate() {
		if (StringUtils.isEmpty(name)) {
			throw new BadRequestException("Invalid name");
		}

		if (StringUtils.isEmpty(city)) {
			throw new BadRequestException("Invalid city");
		}
	}
}