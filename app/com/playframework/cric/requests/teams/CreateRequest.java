package com.playframework.cric.requests.teams;

import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import com.playframework.cric.exceptions.BadRequestException;

@Data
@NoArgsConstructor
public class CreateRequest {
    private String name;
    private Long countryId;
    private Integer typeId;

    public void validate() {
        if (StringUtils.isEmpty(name)) {
            throw new BadRequestException("Invalid name");
        }
    }
}