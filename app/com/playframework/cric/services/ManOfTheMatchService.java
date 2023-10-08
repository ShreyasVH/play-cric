package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.ManOfTheMatch;
import com.playframework.cric.repositories.ManOfTheMatchRepository;

import java.util.List;
import java.util.Map;

public class ManOfTheMatchService {
    private final ManOfTheMatchRepository manOfTheMatchRepository;

    @Inject
    public ManOfTheMatchService(ManOfTheMatchRepository manOfTheMatchRepository)
    {
        this.manOfTheMatchRepository = manOfTheMatchRepository;
    }

    public List<ManOfTheMatch> add(List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        return manOfTheMatchRepository.add(playerIds, matchPlayerMap);
    }

    public List<ManOfTheMatch> getByMatchPlayerIds(List<Integer> matchPlayerIds)
    {
        return manOfTheMatchRepository.getByMatchPlayerIds(matchPlayerIds);
    }
}
