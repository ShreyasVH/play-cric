package com.playframework.cric.responses;

import com.playframework.cric.models.Extras;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtrasResponse {
    private Integer id;
    private Integer runs;
    private ExtrasTypeResponse type;
    private TeamResponse battingTeam;
    private TeamResponse bowlingTeam;
    private Integer innings;

    public ExtrasResponse(Extras extras, ExtrasTypeResponse extrasTypeResponse, TeamResponse battingTeam, TeamResponse bowlingTeam)
    {
        this.id = extras.getId();
        this.runs = extras.getRuns();
        this.type = extrasTypeResponse;
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.innings = extras.getInnings();
    }
}
