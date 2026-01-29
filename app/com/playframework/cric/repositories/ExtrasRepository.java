package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.Extras;
import com.playframework.cric.requests.matches.ExtrasRequest;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.stream.Collectors;

public class ExtrasRepository {
    private final JPAApi jpaApi;

    @Inject
    public ExtrasRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<Extras> add(Integer matchId, List<ExtrasRequest> extrasRequests)
    {
        return jpaApi.withTransaction(em -> {
            return add(em, matchId, extrasRequests);
        });
    }

    public List<Extras> add(EntityManager em, Integer matchId, List<ExtrasRequest> extrasRequests)
    {
        List<Extras> extrasList = extrasRequests.stream().map(extrasRequest -> new Extras(matchId, extrasRequest)).collect(Collectors.toList());
        extrasList.forEach(em::persist);
        return extrasList;
    }

    public List<Extras> getByMatchId(Integer matchId)
    {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT e FROM Extras e WHERE e.matchId = :id ORDER BY e.innings, e.id",
                    Extras.class
            )
            .setParameter("id", matchId)
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
            "DELETE FROM Extras e WHERE e.matchId = :id"
        )
        .setParameter("id", matchId).executeUpdate();
    }
}
