package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.FielderDismissal;
import com.playframework.cric.repositories.FielderDismissalRepository;

import java.util.List;
import java.util.Map;

public class FielderDismissalService {
    private final FielderDismissalRepository fielderDismissalRepository;

    @Inject
    public FielderDismissalService(FielderDismissalRepository fielderDismissalRepository)
    {
        this.fielderDismissalRepository = fielderDismissalRepository;
    }

    public List<FielderDismissal> add(Map<Integer, List<Long>> scorePlayerMap, Map<Long, Integer> matchPlayerMap)
    {
        return fielderDismissalRepository.add(scorePlayerMap, matchPlayerMap);
    }

    public Map<String, Map<String, Integer>> getFieldingStats(Long playerId)
    {
        return fielderDismissalRepository.getFieldingStats(playerId);
    }

    public List<FielderDismissal> get(List<Integer> matchPlayerIds)
    {
        return fielderDismissalRepository.get(matchPlayerIds);
    }

    public void remove(List<Integer> matchPlayerIds)
    {
        fielderDismissalRepository.remove(matchPlayerIds);
    }
}
