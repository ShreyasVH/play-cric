package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.BattingScore;
import com.playframework.cric.repositories.BattingScoreRepository;
import com.playframework.cric.requests.matches.BattingScoreRequest;

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
}
