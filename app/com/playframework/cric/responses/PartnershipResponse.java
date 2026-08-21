package com.playframework.cric.responses;

import com.playframework.cric.models.Partnership;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PartnershipResponse {
    private int id;
    private int innings;
    private int wicket;
    private int runs;
    private int balls;
    private boolean ended;
    private PlayerContribution player1;
    private PlayerContribution player2;
    
    @Data
    @AllArgsConstructor
    class PlayerContribution {
        private PlayerMiniResponse player;
        private int runs;
        private int balls;
    }
    
    public PartnershipResponse(Partnership partnership, PlayerMiniResponse player1, PlayerMiniResponse player2) {
        this.id = partnership.getId();
        this.innings = partnership.getInnings();
        this.wicket = partnership.getWicket();
        this.runs = partnership.getRuns();
        this.balls = partnership.getBalls();
        this.ended = partnership.isEnded();
        this.player1 = new PlayerContribution(player1, partnership.getRuns1(), partnership.getBalls1());
        this.player2 = new PlayerContribution(player2, partnership.getRuns2(), partnership.getBalls2());
    }
}
