package com.playframework.cric.requests.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtrasRequest {
    private Integer runs;
    private Integer typeId;
    private Long battingTeamId;
    private Long bowlingTeamId;
    private Integer innings;
}
