package com.playframework.cric.repositories;

import com.playframework.cric.models.MatchPlayerMap;
import io.ebean.DB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchPlayerMapRepository {
    public List<MatchPlayerMap> add(Integer matchId, List<Long> playerIds, Map<Long, Long> playerTeamMap)
    {
        List<MatchPlayerMap> matchPlayerMaps = playerIds.stream().map(playerId -> new MatchPlayerMap(null, matchId, playerId, playerTeamMap.get(playerId))).collect(Collectors.toList());
        DB.saveAll(matchPlayerMaps);
        return matchPlayerMaps;
    }
}
