package com.playframework.cric.models;

import com.playframework.cric.requests.matches.PartnershipRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partnerships")
public class Partnership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int innings;
    private int wicket;
    private int runs;
    private int balls;
    private boolean ended;
    @Column(name = "match_player_id_1")
    private int matchPlayerId1;
    @Column(name = "runs_1")
    private int runs1;
    @Column(name = "balls_1")
    private int balls1;
    @Column(name = "match_player_id_2")
    private int matchPlayerId2;
    @Column(name = "runs_2")
    private int runs2;
    @Column(name = "balls_2")
    private int balls2;
    private boolean primaryEntry;
    
    public Partnership(PartnershipRequest partnershipRequest, Map<Long, Integer> matchPlayerMap, boolean primary) {
        this.innings = partnershipRequest.getInnings();
        this.wicket = partnershipRequest.getWicket();
        this.runs = partnershipRequest.getRuns();
        this.balls = partnershipRequest.getBalls();
        this.ended = partnershipRequest.isEnded();
        if (primary) {
            this.matchPlayerId1 = matchPlayerMap.get(partnershipRequest.getPlayerId1());
            this.runs1 = partnershipRequest.getRuns1();
            this.balls1 = partnershipRequest.getBalls1();
            this.matchPlayerId2 = matchPlayerMap.get(partnershipRequest.getPlayerId2());
            this.runs2 = partnershipRequest.getRuns2();
            this.balls2 = partnershipRequest.getBalls2();
        } else {
            this.matchPlayerId1 = matchPlayerMap.get(partnershipRequest.getPlayerId2());
            this.runs1 = partnershipRequest.getRuns2();
            this.balls1 = partnershipRequest.getBalls2();
            this.matchPlayerId2 = matchPlayerMap.get(partnershipRequest.getPlayerId1());
            this.runs2 = partnershipRequest.getRuns1();
            this.balls2 = partnershipRequest.getBalls1();
        }
        
        this.primaryEntry = primary;
    }
}