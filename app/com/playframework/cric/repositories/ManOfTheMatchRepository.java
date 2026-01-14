package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.ManOfTheMatch;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManOfTheMatchRepository {
    private final JPAApi jpaApi;

    @Inject
    public ManOfTheMatchRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<ManOfTheMatch> add(List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        return jpaApi.withTransaction(em -> {
            return add(em, playerIds, matchPlayerMap);
        });
    }

    public List<ManOfTheMatch> add(EntityManager em, List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        List<ManOfTheMatch> manOfTheMatchList = playerIds.stream().map(playerId -> new ManOfTheMatch(null, matchPlayerMap.get(playerId))).collect(Collectors.toList());
        manOfTheMatchList.forEach(em::persist);
        return manOfTheMatchList;
    }

    public List<ManOfTheMatch> getByMatchPlayerIds(List<Integer> matchPlayerIds)
    {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT motm FROM ManOfTheMatch motm WHERE motm.matchPlayerId IN :ids",
                    ManOfTheMatch.class
            )
            .setParameter("ids", matchPlayerIds)
            .getResultList();
        });
    }

    public void remove(List<Integer> matchPlayerIds)
    {
        jpaApi.withTransaction(em -> {
            remove(em, matchPlayerIds);
        });
    }

    public void remove(EntityManager em, List<Integer> matchPlayerIds)
    {
        em.createQuery(
            "DELETE FROM ManOfTheMatch motm WHERE motm.matchPlayerId IN :ids",
            ManOfTheMatch.class
        )
        .setParameter("ids", matchPlayerIds).executeUpdate();
    }
}
