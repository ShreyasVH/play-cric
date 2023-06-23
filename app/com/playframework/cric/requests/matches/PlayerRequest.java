package com.playframework.cric.requests.matches;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerRequest {
    private Long id;
    private Long teamId;
}
