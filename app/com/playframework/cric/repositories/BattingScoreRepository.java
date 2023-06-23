package com.playframework.cric.repositories;

import com.playframework.cric.models.BattingScore;
import com.playframework.cric.requests.matches.BattingScoreRequest;
import io.ebean.DB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BattingScoreRepository {
    public List<BattingScore> add(List<BattingScoreRequest> battingScoreRequests, Map<Long, Integer> matchPlayerMaps)
    {
        List<BattingScore> battingScores = battingScoreRequests.stream().map(battingScoreRequest -> new BattingScore(battingScoreRequest, matchPlayerMaps)).collect(Collectors.toList());
        DB.saveAll(battingScores);
        return battingScores;
    }
}
