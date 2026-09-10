package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.BallwiseDetail;
import com.playframework.cric.models.BattingScore;
import com.playframework.cric.repositories.BallwiseDetailsRepository;
import com.playframework.cric.repositories.BattingScoreRepository;
import com.playframework.cric.requests.matches.BallwiseDetailRequest;
import com.playframework.cric.requests.matches.BattingScoreRequest;
import com.playframework.cric.responses.BattingStats;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;

public class BallwiseDetailsService {
    private final BallwiseDetailsRepository ballwiseDetailsRepository;

    @Inject
    public BallwiseDetailsService(BallwiseDetailsRepository ballwiseDetailsRepository)
    {
        this.ballwiseDetailsRepository = ballwiseDetailsRepository;
    }

    public List<BallwiseDetail> add(List<BallwiseDetailRequest> ballwiseDetailRequests, Map<Long, Integer> matchPlayerMaps)
    {
        return ballwiseDetailsRepository.add(ballwiseDetailRequests, matchPlayerMaps);
    }

    public List<BallwiseDetail> add(EntityManager em, List<BallwiseDetailRequest> ballwiseDetailRequests, Map<Long, Integer> matchPlayerMaps)
    {
        return ballwiseDetailsRepository.add(em, ballwiseDetailRequests, matchPlayerMaps);
    }

//    public List<BattingScore> getBattingScores(List<Integer> matchPlayerIds)
//    {
//        return battingScoreRepository.getBattingScores(matchPlayerIds);
//    }
//
    public void remove(List<Integer> matchPlayerIds)
    {
        ballwiseDetailsRepository.remove(matchPlayerIds);
    }

    public void remove(EntityManager em, List<Integer> matchPlayerIds)
    {
        ballwiseDetailsRepository.remove(em, matchPlayerIds);
    }
}
