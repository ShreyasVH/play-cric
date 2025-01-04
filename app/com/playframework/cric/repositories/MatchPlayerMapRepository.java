package com.playframework.cric.repositories;

import com.playframework.cric.models.MatchPlayerMap;
import com.playframework.cric.requests.players.MergeRequest;
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

    public List<MatchPlayerMap> getByMatchId(Integer matchId)
    {
        return DB.find(MatchPlayerMap.class).where().eq("matchId", matchId).findList();
    }

    public void remove(Integer matchId)
    {
        DB.deleteAll(getByMatchId(matchId));
    }

    public void merge(MergeRequest mergeRequest)
    {
        List<MatchPlayerMap> matchPlayerMaps = DB.find(MatchPlayerMap.class).where().eq("playerId", mergeRequest.getPlayerIdToMerge()).findList();
        matchPlayerMaps.forEach(matchPlayerMap -> matchPlayerMap.setPlayerId(mergeRequest.getOriginalPlayerId()));
        DB.saveAll(matchPlayerMaps);
    }
}
