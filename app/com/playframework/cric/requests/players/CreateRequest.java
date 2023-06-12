package com.playframework.cric.requests.players;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import com.playframework.cric.exceptions.BadRequestException;


@Data
@NoArgsConstructor
public class CreateRequest {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;
    private Long countryId;
    private String image;

    public void validate() {
        if (StringUtils.isEmpty(name)) {
            throw new BadRequestException("Invalid name");
        }

        if (null == dateOfBirth) {
            throw new BadRequestException("Invalid date of birth");
        }
    }
}