package com.playframework.cric.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.playframework.cric.models.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SeriesDetailedResponse
{
    private Long id;
    private String name;
    private SeriesTypeResponse type;
    private GameTypeResponse gameType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;
    private List<TeamResponse> teams;
    private List<MatchMiniResponse> matches;

    public SeriesDetailedResponse(Series series, SeriesType seriesType, GameType gameType, List<TeamResponse> teams, List<MatchMiniResponse> matches)
    {
        this.id = series.getId();
        this.name = series.getName();
        this.type = new SeriesTypeResponse(seriesType);
        this.gameType = new GameTypeResponse(gameType);
        this.startTime = series.getStartTime();
        this.teams = teams;
        this.matches = matches;
    }
}
