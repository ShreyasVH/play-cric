package com.playframework.cric.responses;

import lombok.NoArgsConstructor;
import lombok.Data;

import com.playframework.cric.models.GameType;

@Data
@NoArgsConstructor
public class GameTypeResponse {
    private Integer id;
    private String name;

    public GameTypeResponse(GameType gameType) {
        this.id = gameType.getId();
        this.name = gameType.getName();
    }
}