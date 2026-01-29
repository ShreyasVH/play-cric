package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.BattingScore;
import com.playframework.cric.models.MatchPlayerMap;
import com.playframework.cric.requests.players.MergeRequest;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchPlayerMapRepository {
    private final JPAApi jpaApi;

    @Inject
    public MatchPlayerMapRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<MatchPlayerMap> add(Integer matchId, List<Long> playerIds, Map<Long, Long> playerTeamMap)
    {
        return jpaApi.withTransaction(em -> {
            return add(em, matchId, playerIds, playerTeamMap);
        });
    }

    public List<MatchPlayerMap> add(EntityManager em, Integer matchId, List<Long> playerIds, Map<Long, Long> playerTeamMap)
    {
        List<MatchPlayerMap> matchPlayerMaps = playerIds.stream().map(playerId -> new MatchPlayerMap(null, matchId, playerId, playerTeamMap.get(playerId))).collect(Collectors.toList());
        matchPlayerMaps.forEach(em::persist);
        return matchPlayerMaps;
    }

    public List<MatchPlayerMap> getByMatchId(Integer matchId)
    {
        return jpaApi.withTransaction(em -> {
            return getByMatchId(em, matchId);
        });
    }

    public List<MatchPlayerMap> getByMatchId(EntityManager em, Integer matchId)
    {
        return em.createQuery(
            "SELECT mpm FROM MatchPlayerMap mpm WHERE mpm.matchId = :matchId",
            MatchPlayerMap.class
        )
        .setParameter("matchId", matchId)
        .getResultList();
    }

    public void remove(Integer matchId)
    {
        jpaApi.withTransaction(em -> {
            remove(em, matchId);
        });
    }

    public void remove(EntityManager em, Integer matchId)
    {
        em.createQuery(
            "DELETE FROM MatchPlayerMap mpm WHERE mpm.matchId = :matchId"
        )
        .setParameter("matchId", matchId).executeUpdate();
    }

    public void merge(MergeRequest mergeRequest)
    {
        jpaApi.withTransaction(em -> {
            merge(em, mergeRequest);
        });

    }

    public void merge(EntityManager em, MergeRequest mergeRequest)
    {
        List<MatchPlayerMap> matchPlayerMaps = em.createQuery(
                "SELECT mpm FROM MatchPlayerMap mpm WHERE mpm.playerId = :playerId",
                MatchPlayerMap.class
        )
                .setParameter("playerId", mergeRequest.getPlayerIdToMerge())
                .getResultList();
        matchPlayerMaps.forEach(matchPlayerMap -> matchPlayerMap.setPlayerId(mergeRequest.getOriginalPlayerId()));
        matchPlayerMaps.forEach(em::merge);
    }
}
