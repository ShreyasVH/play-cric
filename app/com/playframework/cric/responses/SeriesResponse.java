package com.playframework.cric.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.playframework.cric.models.*;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SeriesResponse {
    private Long id;
    private String name;
    private CountryResponse homeCountry;
    private TourResponse tour;
    private SeriesTypeResponse type;
    private GameTypeResponse gameType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;
    private List<TeamResponse> teams;
    private List<PlayerResponse> manOfTheSeriesList;

    public SeriesResponse(Series series, CountryResponse country, TourResponse tour, SeriesTypeResponse seriesType, GameTypeResponse gameType, List<TeamResponse> teams, List<PlayerResponse> manOfTheSeriesList) {
        this.id = series.getId();
        this.name = series.getName();
        this.homeCountry = country;
        this.tour = tour;
        this.type = seriesType;
        this.gameType = gameType;
        this.startTime = series.getStartTime();
        this.teams = teams;
        this.manOfTheSeriesList = manOfTheSeriesList;
    }
}