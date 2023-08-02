package com.playframework.cric.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.playframework.cric.models.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MatchMiniResponse {
    private Integer id;
    private TeamResponse team1;
    private TeamResponse team2;
    private ResultTypeResponse resultType;
    private TeamResponse winner;
    private Integer winMargin;
    private WinMarginTypeResponse winMarginType;
    private StadiumResponse stadium;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    public MatchMiniResponse(Match match, TeamResponse team1, TeamResponse team2, ResultType resultType, WinMarginType winMarginType, StadiumResponse stadium)
    {
        this.id = match.getId();
        this.team1 = team1;
        this.team2 = team2;
        Map<Long, TeamResponse> teamMap = Map.of(
                team1.getId(), team1,
                team2.getId(), team2
        );
        if(null != match.getWinnerId())
        {
            this.winner = teamMap.get(match.getWinnerId());
            this.winMargin = match.getWinMargin();
            this.winMarginType = new WinMarginTypeResponse(winMarginType);
        }
        this.resultType = new ResultTypeResponse(resultType);
        this.stadium = stadium;
        this.startTime = match.getStartTime();
    }
}
