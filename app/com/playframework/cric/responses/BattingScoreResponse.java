package com.playframework.cric.responses;

import com.playframework.cric.models.BattingScore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BattingScoreResponse
{
    private Integer id;
    private PlayerMiniResponse player;
    private Integer runs;
    private Integer balls;
    private Integer fours;
    private Integer sixes;
    private DismissalModeResponse dismissalMode;
    private PlayerMiniResponse bowler;
    private List<PlayerMiniResponse> fielders;
    private Integer innings;
    private Integer number;

    public BattingScoreResponse(BattingScore battingScore, PlayerMiniResponse player, DismissalModeResponse dismissalMode, PlayerMiniResponse bowler, List<PlayerMiniResponse> fielders)
    {
        this.id = battingScore.getId();
        this.player = player;
        this.runs = battingScore.getRuns();
        this.balls = battingScore.getBalls();
        this.fours = battingScore.getFours();
        this.sixes = battingScore.getSixes();
        this.dismissalMode = dismissalMode;
        this.bowler = bowler;
        this.fielders = fielders;
        this.innings = battingScore.getInnings();
        this.number = battingScore.getNumber();
    }
}
