package com.playframework.cric.models;

import com.playframework.cric.requests.matches.BallwiseDetailRequest;
import com.playframework.cric.requests.matches.BattingScoreRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ballwise_details")
public class BallwiseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer batsmanMatchPlayerId;
    private Integer bowlerMatchPlayerId;
    private Integer innings;
    private Integer ball;
    private Integer totalRuns;
    private Integer batsmanRuns;
    private Integer bowlerRuns;
    private Integer extrasRuns;
    private String extrasType;
    private boolean dismissal;
    private long timestamp;

    public BallwiseDetail(BallwiseDetailRequest ballwiseDetailRequest, Map<Long, Integer> matchPlayerMap)
    {
        this.batsmanMatchPlayerId = matchPlayerMap.get(ballwiseDetailRequest.getBatsmanPlayerId());
        this.bowlerMatchPlayerId = matchPlayerMap.get(ballwiseDetailRequest.getBowlerPlayerId());
        this.innings = ballwiseDetailRequest.getInnings();
        this.ball = ballwiseDetailRequest.getBall();
        this.totalRuns = ballwiseDetailRequest.getTotalRuns();
        this.batsmanRuns = ballwiseDetailRequest.getBatsmanRuns();
        this.bowlerRuns = ballwiseDetailRequest.getBowlerRuns();
        this.extrasRuns = ballwiseDetailRequest.getExtrasRuns();
        this.extrasType = ballwiseDetailRequest.getExtrasType();
        this.dismissal = ballwiseDetailRequest.isDismissal();
        this.timestamp = ballwiseDetailRequest.getTimestamp();
    }
}