package com.playframework.cric.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BowlingStats {
    private Integer innings = 0;
    private Integer balls = 0;
    private Integer maidens = 0;
    private Integer runs = 0;
    private Integer wickets = 0;
    private Double economy;
    private Double average;
    private Double strikeRate;
    private Integer fifers = 0;
    private Integer tenWickets = 0;

    public BowlingStats(Map<String, Integer> basicStats)
    {
        this.innings = basicStats.getOrDefault("innings", 0);
        this.balls = basicStats.getOrDefault("balls", 0);
        this.maidens = basicStats.getOrDefault("maidens", 0);
        this.runs = basicStats.getOrDefault("runs", 0);
        this.wickets = basicStats.getOrDefault("wickets", 0);
        this.fifers = basicStats.get("fifers");
        this.tenWickets = basicStats.get("tenWickets");
    }
}
