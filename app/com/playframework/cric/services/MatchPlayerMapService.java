package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.MatchPlayerMap;
import com.playframework.cric.repositories.MatchPlayerMapRepository;

import java.util.List;
import java.util.Map;

public class MatchPlayerMapService {
    private final MatchPlayerMapRepository matchPlayerMapRepository;

    @Inject
    public MatchPlayerMapService(MatchPlayerMapRepository matchPlayerMapRepository)
    {
        this.matchPlayerMapRepository = matchPlayerMapRepository;
    }

    public List<MatchPlayerMap> add(Integer matchId, List<Long> playerIds, Map<Long, Long> playerTeamMap)
    {
        return matchPlayerMapRepository.add(matchId, playerIds, playerTeamMap);
    }
}
