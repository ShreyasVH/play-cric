package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.DismissalMode;
import play.db.jpa.JPAApi;

import java.util.List;

public class DismissalModeRepository {
    private final JPAApi jpaApi;

    @Inject
    public DismissalModeRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<DismissalMode> getAll()
    {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT d FROM DismissalMode d",
                DismissalMode.class
            )
            .getResultList();
        });
    }
}
