package com.playframework.cric.requests.matches;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtrasRequest {
    private Integer runs;
    private Integer typeId;
    private Long battingTeamId;
    private Long bowlingTeamId;
    private Integer innings;
}
