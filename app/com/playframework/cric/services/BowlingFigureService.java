package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.BowlingFigure;
import com.playframework.cric.repositories.BowlingFigureRepository;
import com.playframework.cric.requests.matches.BowlingFigureRequest;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;

public class BowlingFigureService {
    private final BowlingFigureRepository bowlingFigureRepository;

    @Inject
    public BowlingFigureService(BowlingFigureRepository bowlingFigureRepository)
    {
        this.bowlingFigureRepository = bowlingFigureRepository;
    }

    public List<BowlingFigure> add(List<BowlingFigureRequest> bowlingFigureRequests, Map<Long, Integer> matchPlayerMap)
    {
        return bowlingFigureRepository.add(bowlingFigureRequests, matchPlayerMap);
    }

    public List<BowlingFigure> add(EntityManager em, List<BowlingFigureRequest> bowlingFigureRequests, Map<Long, Integer> matchPlayerMap)
    {
        return bowlingFigureRepository.add(em, bowlingFigureRequests, matchPlayerMap);
    }

    public Map<String, Map<String, Integer>> getBasicBowlingStats(Long playerId)
    {
        return bowlingFigureRepository.getBasicBowlingStats(playerId);
    }

    public List<BowlingFigure> get(List<Integer> matchPlayerIds)
    {
        return bowlingFigureRepository.get(matchPlayerIds);
    }

    public void remove(List<Integer> matchPlayerIds)
    {
        bowlingFigureRepository.remove(matchPlayerIds);
    }

    public void remove(EntityManager em, List<Integer> matchPlayerIds)
    {
        bowlingFigureRepository.remove(em, matchPlayerIds);
    }
}
