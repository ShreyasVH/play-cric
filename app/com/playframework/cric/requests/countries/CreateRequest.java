package com.playframework.cric.requests.countries;

import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import com.playframework.cric.exceptions.BadRequestException;

@Data
@NoArgsConstructor
public class CreateRequest {
	private String name;

	public void validate() {
		if (StringUtils.isEmpty(name)) {
			throw new BadRequestException("Invalid name");
		}
	}
}