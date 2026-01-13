package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.WicketKeeper;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WicketKeeperRepository {
    private final JPAApi jpaApi;

    @Inject
    public WicketKeeperRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<WicketKeeper> add(List<Long> playerIds, Map<Long, Integer> matchPlayerMap)
    {
        List<WicketKeeper> captainList = playerIds.stream().map(playerId -> new WicketKeeper(null, matchPlayerMap.get(playerId))).collect(Collectors.toList());
        return jpaApi.withTransaction(em -> {
            captainList.forEach(em::persist);
            return captainList;
        });
    }

    public List<WicketKeeper> getByMatchPlayerIds(List<Integer> matchPlayerIds)
    {
        return jpaApi.withTransaction(em -> {
                    return em.createQuery(
                        "SELECT wk FROM WicketKeeper wk WHERE wk.matchPlayerId IN :ids",
                        WicketKeeper.class
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
            "DELETE FROM WicketKeeper c WHERE c.matchPlayerId IN :ids",
            WicketKeeper.class
        )
        .setParameter("ids", matchPlayerIds).executeUpdate();
    }
}
