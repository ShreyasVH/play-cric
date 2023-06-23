package com.playframework.cric.repositories;

import com.playframework.cric.models.WicketKeeper;
import io.ebean.DB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WicketKeeperRepository {
    public List<WicketKeeper> add(List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        List<WicketKeeper> wicketKeeperList = playerIds.stream().map(playerId -> new WicketKeeper(null, matchPlayerMap.get(playerId))).collect(Collectors.toList());
        DB.saveAll(wicketKeeperList);
        return wicketKeeperList;
    }
}
