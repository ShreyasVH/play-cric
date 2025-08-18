package com.playframework.cric.requests.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalsRequestEntry {
    private long teamId;
    private int runs;
    private int wickets;
    private int balls;
    private int innings;
}
