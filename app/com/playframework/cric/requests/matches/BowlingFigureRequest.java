package com.playframework.cric.requests.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BowlingFigureRequest {
    private Long playerId;
    private Integer balls;
    private Integer maidens;
    private Integer runs;
    private Integer wickets;
    private Integer innings;
}
