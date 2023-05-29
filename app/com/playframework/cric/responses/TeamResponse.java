package com.playframework.cric.responses;

import lombok.NoArgsConstructor;
import lombok.Data;

import com.playframework.cric.models.Team;

@Data
@NoArgsConstructor
public class TeamResponse {
    private Long id;
    private String name;
    private CountryResponse country;
    private TeamTypeResponse teamType;

    public TeamResponse(Team team, CountryResponse country, TeamTypeResponse teamType) {
        this.id = team.getId();
        this.name = team.getName();
        this.country = country;
        this.teamType = teamType;
    }
}