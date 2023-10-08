package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.WicketKeeper;
import com.playframework.cric.repositories.WicketKeeperRepository;

import java.util.List;
import java.util.Map;

public class WicketKeeperService {
    private final WicketKeeperRepository wicketKeeperRepository;

    @Inject
    public WicketKeeperService(WicketKeeperRepository wicketKeeperRepository)
    {
        this.wicketKeeperRepository = wicketKeeperRepository;
    }

    public List<WicketKeeper> add(List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        return wicketKeeperRepository.add(playerIds, matchPlayerMap);
    }

    public List<WicketKeeper> getByMatchPlayerIds(List<Integer> matchPlayerIds)
    {
        return wicketKeeperRepository.getByMatchPlayerIds(matchPlayerIds);
    }
}
