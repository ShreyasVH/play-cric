package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.Partnership;
import com.playframework.cric.repositories.PartnershipRepository;
import com.playframework.cric.requests.matches.PartnershipRequest;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;

public class PartnershipService {
    private final PartnershipRepository partnershipRepository;

    @Inject
    public PartnershipService(PartnershipRepository partnershipRepository)
    {
        this.partnershipRepository = partnershipRepository;
    }

    public List<Partnership> add(List<PartnershipRequest> partnershipRequests, Map<Long, Integer> matchPlayerMaps)
    {
        return partnershipRepository.add(partnershipRequests, matchPlayerMaps);
    }

    public List<Partnership> add(EntityManager em, List<PartnershipRequest> partnershipRequests, Map<Long, Integer> matchPlayerMaps)
    {
        return partnershipRepository.add(em, partnershipRequests, matchPlayerMaps);
    }

//    public Map<String, Map<String, Integer>> getBattingStats(Long playerId)
//    {
//        return battingScoreRepository.getBattingStats(playerId);
//    }
//
//    public Map<String, Map<String, Integer>> getDismissalStats(Long playerId)
//    {
//        return battingScoreRepository.getDismissalStats(playerId);
//    }
//
//    public List<BattingScore> getBattingScores(List<Integer> matchPlayerIds)
//    {
//        return battingScoreRepository.getBattingScores(matchPlayerIds);
//    }
//
//    public void remove(List<Integer> matchPlayerIds)
//    {
//        battingScoreRepository.remove(matchPlayerIds);
//    }
//
//    public void remove(EntityManager em, List<Integer> matchPlayerIds)
//    {
//        battingScoreRepository.remove(em, matchPlayerIds);
//    }
}
