package com.playframework.cric.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.NoArgsConstructor;
import lombok.Data;

import com.playframework.cric.models.Tour;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TourResponse {
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    public TourResponse(Tour tour) {
        this.id = tour.getId();
        this.name = tour.getName();
        this.startTime = tour.getStartTime();
    }
}