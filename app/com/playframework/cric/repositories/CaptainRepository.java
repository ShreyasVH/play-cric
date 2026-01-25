package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.Captain;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CaptainRepository {
    private final JPAApi jpaApi;

    @Inject
    public CaptainRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<Captain> add(List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        return jpaApi.withTransaction(em -> {
            return add(em, playerIds, matchPlayerMap);
        });
    }

    public List<Captain> add(EntityManager em, List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        List<Captain> captainList = playerIds.stream().map(playerId -> new Captain(null, matchPlayerMap.get(playerId))).collect(Collectors.toList());
        captainList.forEach(em::persist);
        return captainList;
    }

    public List<Captain> getByMatchPlayerIds(List<Integer> matchPlayerIds)
    {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT c FROM Captain c WHERE c.matchPlayerId IN :ids",
                Captain.class
            )
                .setParameter("ids", matchPlayerIds)
                .getResultList();
            }
        );
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
                "DELETE FROM Captain c WHERE c.matchPlayerId IN :ids",
                Captain.class
        )
        .setParameter("ids", matchPlayerIds).executeUpdate();
    }
}
