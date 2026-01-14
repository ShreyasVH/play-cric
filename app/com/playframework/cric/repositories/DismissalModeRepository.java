package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.DismissalMode;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.function.Function;

public class DismissalModeRepository {
    private final JPAApi jpaApi;

    @Inject
    public DismissalModeRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<DismissalMode> getAll()
    {
        return jpaApi.withTransaction((Function<EntityManager, List<DismissalMode>>) this::getAll);
    }

    public List<DismissalMode> getAll(EntityManager em)
    {
        return em.createQuery(
                "SELECT d FROM DismissalMode d",
                DismissalMode.class
        )
                .getResultList();
    }
}
