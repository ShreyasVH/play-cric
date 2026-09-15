package com.playframework.cric.requests.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BallwiseDetailRequest {
    private Long batsmanPlayerId;
    private Long bowlerPlayerId;
    private Integer innings;
    private Integer ball;
    private Integer totalRuns;
    private Integer batsmanRuns;
    private Integer bowlerRuns;
    private Integer extrasRuns;
    private String extrasType;
    private boolean dismissal;
    private long timestamp;
}
