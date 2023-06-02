package com.playframework.cric.requests.tours;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.playframework.cric.exceptions.BadRequestException;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateRequest {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    public void validate() {
        if (StringUtils.isEmpty(name)) {
            throw new BadRequestException("Invalid name");
        }

        if (null == startTime) {
            throw new BadRequestException("Invalid startTime");
        }
    }
}