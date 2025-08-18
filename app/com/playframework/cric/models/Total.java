package com.playframework.cric.models;

import com.playframework.cric.requests.matches.TotalsRequestEntry;
import io.ebean.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "totals")
public class Total extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int matchId;
    private long teamId;
    private int runs;
    private int wickets;
    private int balls;
    private int innings;

    public Total(int matchId, TotalsRequestEntry totalsRequestEntry)
    {
        this.matchId = matchId;
        this.teamId = totalsRequestEntry.getTeamId();
        this.runs = totalsRequestEntry.getRuns();
        this.wickets = totalsRequestEntry.getWickets();
        this.balls = totalsRequestEntry.getBalls();
        this.innings = totalsRequestEntry.getInnings();
    }
}
