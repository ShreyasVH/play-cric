package com.playframework.cric.requests.players;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MergeRequest {
    private long playerIdToMerge;
    private long originalPlayerId;
}
