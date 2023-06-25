package com.playframework.cric.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldingStats {
    private Integer catches = 0;
    private Integer runOuts = 0;
    private Integer stumpings = 0;

    public FieldingStats(Map<String, Integer> fieldingStats)
    {
        this.catches = fieldingStats.getOrDefault("Caught", 0);
        this.runOuts = fieldingStats.getOrDefault("Run Out", 0);
        this.stumpings = fieldingStats.getOrDefault("Stumped", 0);
    }
}
