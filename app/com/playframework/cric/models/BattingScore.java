package com.playframework.cric.models;

import com.playframework.cric.requests.matches.BattingScoreRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import io.ebean.Model;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batting_scores")
public class BattingScore extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer matchPlayerId;
    private Integer runs;
    private Integer balls;
    private Integer fours;
    private Integer sixes;
    private Integer dismissalModeId;
    private Integer bowlerId;
    private Integer innings;
    private Integer number;

    public BattingScore(BattingScoreRequest battingScoreRequest, Map<Long, Integer> matchPlayerMap)
    {
        this.runs = battingScoreRequest.getRuns();
        this.balls = battingScoreRequest.getBalls();
        this.fours = battingScoreRequest.getFours();
        this.sixes = battingScoreRequest.getSixes();
        this.dismissalModeId = battingScoreRequest.getDismissalModeId();
        this.innings = battingScoreRequest.getInnings();
        this.number = battingScoreRequest.getNumber();
        this.matchPlayerId = matchPlayerMap.get(battingScoreRequest.getPlayerId());
        if(null != battingScoreRequest.getBowlerId())
        {
            this.bowlerId = matchPlayerMap.get(battingScoreRequest.getBowlerId());
        }
    }
}