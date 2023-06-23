package com.playframework.cric.requests.matches;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.NoArgsConstructor;
import lombok.Data;

import com.playframework.cric.exceptions.BadRequestException;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRequest {
    private Long seriesId;
    private Long team1Id;
    private Long team2Id;
    private Long tossWinnerId;
    private Long batFirstId;
    private Integer resultTypeId;
    private Long winnerId;
    private Integer winMargin;
    private Integer winMarginTypeId;
    private Long stadiumId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;
    private List<PlayerRequest> players;
    private List<PlayerRequest> bench;
    private List<ExtrasRequest> extras;
    private List<BattingScoreRequest> battingScores;
    private List<BowlingFigureRequest> bowlingFigures;
    private List<Long> manOfTheMatchList;
    private List<Long> captains;
    private List<Long> wicketKeepers;
    private Boolean isOfficial = true;

    public void validate() {
        if(null != battingScores)
        {
            for(BattingScoreRequest battingScore: battingScores)
            {
                if(null == battingScore.getPlayerId())
                {
                    throw new BadRequestException("Invalid batsman");
                }
            }
        }

        if(null != extras)
        {
            for(ExtrasRequest extra: extras)
            {
                if(null == extra.getBattingTeamId())
                {
                    throw new BadRequestException("Invalid batting team in extras");
                }

                if(null == extra.getBowlingTeamId())
                {
                    throw new BadRequestException("Invalid bowling team in extras");
                }
            }
        }

        if(null != bowlingFigures)
        {
            for(BowlingFigureRequest bowlingFigure: bowlingFigures)
            {
                if(null == bowlingFigure.getPlayerId())
                {
                    throw new BadRequestException("Invalid bowler");
                }
            }
        }

        if(null != players)
        {
            for(PlayerRequest player: players)
            {
                if(null == player.getId())
                {
                    throw new BadRequestException("Invalid player");
                }

                if(null == player.getTeamId())
                {
                    throw new BadRequestException("Invalid team for player");
                }
            }
        }

        if(null != bench)
        {
            for(PlayerRequest player: bench)
            {
                if(null == player.getId())
                {
                    throw new BadRequestException("Invalid bench player");
                }

                if(null == player.getTeamId())
                {
                    throw new BadRequestException("Invalid team for bench player");
                }
            }
        }
    }
}