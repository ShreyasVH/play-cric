package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.Total;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;

public class TotalsRepository {
    private final JPAApi jpaApi;

    @Inject
    public TotalsRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public void add(List<Total> totals)
    {
        jpaApi.withTransaction(em -> {
            totals.forEach(em::persist);
        });
    }

    public List<Total> getByMatchId(Integer matchId)
    {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT t FROM Total t WHERE t.matchId = :matchId",
                Total.class
            )
                .setParameter("matchId", matchId)
                .getResultList();
        });
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
            "DELETE FROM Total t WHERE t.matchId = :matchId",
            Total.class
        )
        .setParameter("matchId", matchId)
        .executeUpdate();
    }
}
