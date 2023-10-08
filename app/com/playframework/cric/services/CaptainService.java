package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.Captain;
import com.playframework.cric.repositories.CaptainRepository;

import java.util.List;
import java.util.Map;

public class CaptainService {
    private final CaptainRepository captainRepository;

    @Inject
    public CaptainService(CaptainRepository captainRepository)
    {
        this.captainRepository = captainRepository;
    }

    public List<Captain> add(List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        return captainRepository.add(playerIds, matchPlayerMap);
    }

    public List<Captain> getByMatchPlayerIds(List<Integer> matchPlayerIds)
    {
        return captainRepository.getByMatchPlayerIds(matchPlayerIds);
    }
}
