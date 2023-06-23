package com.playframework.cric.models;

import com.playframework.cric.requests.matches.ExtrasRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import io.ebean.Model;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "extras")
public class Extras extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer matchId;
    private Integer typeId;
    private Integer runs;
    private Long battingTeamId;
    private Long bowlingTeamId;
    private Integer innings;

    public Extras(Integer matchId, ExtrasRequest extrasRequest)
    {
        this.matchId = matchId;
        this.typeId = extrasRequest.getTypeId();
        this.runs = extrasRequest.getRuns();
        this.battingTeamId = extrasRequest.getBattingTeamId();
        this.bowlingTeamId = extrasRequest.getBowlingTeamId();
        this.innings = extrasRequest.getInnings();
    }
}