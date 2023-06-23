package com.playframework.cric.responses;

import com.playframework.cric.models.BowlingFigure;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BowlingFigureResponse
{
    private Integer id;
    private PlayerResponse player;
    private Integer balls;
    private Integer maidens;
    private Integer runs;
    private Integer wickets;
    private Integer innings;

    public BowlingFigureResponse(BowlingFigure bowlingFigure, PlayerResponse player)
    {
        this.id = bowlingFigure.getId();
        this.player = player;
        this.balls = bowlingFigure.getBalls();
        this.maidens = bowlingFigure.getMaidens();
        this.runs = bowlingFigure.getRuns();
        this.wickets = bowlingFigure.getWickets();
        this.innings = bowlingFigure.getInnings();
    }
}
