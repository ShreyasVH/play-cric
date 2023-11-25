package com.playframework.cric.repositories;

import com.playframework.cric.models.Captain;
import io.ebean.DB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CaptainRepository {
    public List<Captain> add(List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        List<Captain> captainList = playerIds.stream().map(playerId -> new Captain(null, matchPlayerMap.get(playerId))).collect(Collectors.toList());
        DB.saveAll(captainList);
        return captainList;
    }

    public List<Captain> getByMatchPlayerIds(List<Integer> matchPlayerIds)
    {
        return DB.find(Captain.class).where().in("matchPlayerId", matchPlayerIds).findList();
    }

    public void remove(List<Integer> matchPlayerIds)
    {
        DB.deleteAll(getByMatchPlayerIds(matchPlayerIds));
    }
}
