package com.playframework.cric.repositories;

import com.playframework.cric.models.ManOfTheMatch;
import io.ebean.DB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManOfTheMatchRepository {
    public List<ManOfTheMatch> add(List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        List<ManOfTheMatch> manOfTheMatchList = playerIds.stream().map(playerId -> new ManOfTheMatch(null, matchPlayerMap.get(playerId))).collect(Collectors.toList());
        DB.saveAll(manOfTheMatchList);
        return manOfTheMatchList;
    }

    public List<ManOfTheMatch> getByMatchPlayerIds(List<Integer> matchPlayerIds)
    {
        return DB.find(ManOfTheMatch.class).where().in("matchPlayerId", matchPlayerIds).findList();
    }

    public void remove(List<Integer> matchPlayerIds)
    {
        DB.deleteAll(getByMatchPlayerIds(matchPlayerIds));
    }
}
