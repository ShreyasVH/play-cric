package com.playframework.cric.responses;

import lombok.NoArgsConstructor;
import lombok.Data;

import com.playframework.cric.models.TeamType;

@Data
@NoArgsConstructor
public class TeamTypeResponse {
    private Integer id;
    private String name;

    public TeamTypeResponse(TeamType teamType) {
        this.id = teamType.getId();
        this.name = teamType.getName();
    }
}