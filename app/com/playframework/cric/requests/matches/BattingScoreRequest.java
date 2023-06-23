package com.playframework.cric.requests.matches;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BattingScoreRequest {
    private Long playerId;
    private Integer runs;
    private Integer balls;
    private Integer fours;
    private Integer sixes;
    private Integer dismissalModeId;
    private Long bowlerId;
    private List<Long> fielderIds;
    private Integer innings;
    private Integer number;
}
