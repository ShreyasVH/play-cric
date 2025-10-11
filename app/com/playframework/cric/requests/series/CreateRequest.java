package com.playframework.cric.requests.series;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import com.playframework.cric.exceptions.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateRequest {
    private String name;
    private Long homeCountryId;
    private Long tourId;
    private Integer typeId;
    private Integer gameTypeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;
    private List<Long> teams;
    private List<Long> manOfTheSeriesList;
    private List<Integer> tags;

    public void validate() {
        if (StringUtils.isEmpty(name)) {
            throw new BadRequestException("Invalid name");
        }

        if((null == this.teams) || (this.teams.size() < 2)) {
            throw new BadRequestException("Invalid Teams");
        }

        if(null == startTime) {
            throw new BadRequestException("Invalid start time");
        }
    }
}