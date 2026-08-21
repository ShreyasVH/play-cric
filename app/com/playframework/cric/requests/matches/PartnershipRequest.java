package com.playframework.cric.requests.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnershipRequest {
    private int innings;
    private int wicket;
    private int runs;
    private int balls;
    private boolean ended;
    private long playerId1;
    private int runs1;
    private int balls1;
    private long playerId2;
    private int runs2;
    private int balls2;
}
