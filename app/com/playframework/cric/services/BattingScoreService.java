package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.BattingScore;
import com.playframework.cric.repositories.BattingScoreRepository;
import com.playframework.cric.requests.matches.BattingScoreRequest;
import com.playframework.cric.responses.BattingStats;

import java.util.List;
import java.util.Map;

public class BattingScoreService {
    private final BattingScoreRepository battingScoreRepository;

    @Inject
    public BattingScoreService(BattingScoreRepository battingScoreRepository)
    {
        this.battingScoreRepository = battingScoreRepository;
    }

    public List<BattingScore> add(List<BattingScoreRequest> battingScoreRequests, Map<Long, Integer> matchPlayerMaps)
    {
        return battingScoreRepository.add(battingScoreRequests, matchPlayerMaps);
    }

    public Map<String, Map<String, Integer>> getBattingStats(Long playerId)
    {
        return battingScoreRepository.getBattingStats(playerId);
    }

    public Map<String, Map<String, Integer>> getDismissalStats(Long playerId)
    {
        return battingScoreRepository.getDismissalStats(playerId);
    }

    public List<BattingScore> getBattingScores(List<Integer> matchPlayerIds)
    {
        return battingScoreRepository.getBattingScores(matchPlayerIds);
    }
}
