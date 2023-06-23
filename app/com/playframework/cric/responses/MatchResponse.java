package com.playframework.cric.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.playframework.cric.models.Match;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MatchResponse {
    private Integer id;
    private SeriesResponse series;
    private TeamResponse team1;
    private TeamResponse team2;
    private TeamResponse tossWinner;
    private TeamResponse batFirst;
    private ResultTypeResponse resultType;
    private TeamResponse winner;
    private Integer winMargin;
    private WinMarginTypeResponse winMarginType;
    private StadiumResponse stadium;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;
    private List<BattingScoreResponse> battingScores;
    private List<BowlingFigureResponse> bowlingFigures;
    private List<ExtrasResponse> extras;
    private List<PlayerResponse> players;
    private List<PlayerResponse> manOfTheMatchList;
    private List<PlayerResponse> captains;
    private List<PlayerResponse> wicketKeepers;

    public MatchResponse(Match match, SeriesResponse seriesResponse, TeamResponse team1, TeamResponse team2, ResultTypeResponse resultType, WinMarginTypeResponse winMarginType, StadiumResponse stadium, List<PlayerResponse> players, List<BattingScoreResponse> battingScores, List<BowlingFigureResponse> bowlingFigures, List<ExtrasResponse> extras, List<Long> manOfTheMatchPlayerIds, List<Long> captainIds, List<Long> wicketKeeperIds)
    {
        this.id = match.getId();
        this.series = seriesResponse;
        this.team1 = team1;
        this.team2 = team2;
        Map<Long, TeamResponse> teamMap = Map.of(
            team1.getId(), team1,
            team2.getId(), team2
        );
        if(null != match.getTossWinnerId())
        {
            this.tossWinner = teamMap.get(match.getTossWinnerId());
            this.batFirst = teamMap.get(match.getBatFirstId());
        }
        if(null != match.getWinnerId())
        {
            this.winner = teamMap.get(match.getWinnerId());
            this.winMargin = match.getWinMargin();
            this.winMarginType = winMarginType;
        }
        this.resultType = resultType;
        this.stadium = stadium;
        this.startTime = match.getStartTime();
        this.battingScores = battingScores;
        this.bowlingFigures = bowlingFigures;
        this.extras = extras;
        this.players = players;
        Map<Long, PlayerResponse> playerMap = players.stream().collect(Collectors.toMap(PlayerResponse::getId, player -> player));
        this.manOfTheMatchList = manOfTheMatchPlayerIds.stream().map(playerMap::get).collect(Collectors.toList());
        this.captains = captainIds.stream().map(playerMap::get).collect(Collectors.toList());
        this.wicketKeepers = wicketKeeperIds.stream().map(playerMap::get).collect(Collectors.toList());
    }
}
