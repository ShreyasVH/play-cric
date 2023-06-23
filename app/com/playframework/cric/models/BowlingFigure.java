package com.playframework.cric.models;

import com.playframework.cric.requests.matches.BowlingFigureRequest;
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
@Table(name = "bowling_figures")
public class BowlingFigure extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer matchPlayerId;
    private Integer balls;
    private Integer maidens;
    private Integer runs;
    private Integer wickets;
    private Integer innings;

    public BowlingFigure(BowlingFigureRequest bowlingFigureRequest, Map<Long, Integer> matchPlayerMap)
    {
        this.balls = bowlingFigureRequest.getBalls();
        this.maidens = bowlingFigureRequest.getMaidens();
        this.runs = bowlingFigureRequest.getRuns();
        this.wickets = bowlingFigureRequest.getWickets();
        this.innings = bowlingFigureRequest.getInnings();
        this.matchPlayerId = matchPlayerMap.get(bowlingFigureRequest.getPlayerId());
    }
}