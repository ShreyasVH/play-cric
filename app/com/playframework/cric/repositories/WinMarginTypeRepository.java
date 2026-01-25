package com.playframework.cric.repositories;

import com.google.inject.Inject;

import com.playframework.cric.models.WinMarginType;
import play.db.jpa.JPAApi;

import java.util.List;

public class WinMarginTypeRepository {
    private final JPAApi jpaApi;

    @Inject
    public WinMarginTypeRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public WinMarginType getById(Integer seriesTypeId) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT wt FROM WinMarginType wt WHERE wt.id = :id", WinMarginType.class)
                .setParameter("id", seriesTypeId)
                .getSingleResult();
        });
    }

    public List<WinMarginType> getByIds(List<Integer> seriesTypeIds) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT wt FROM WinMarginType wt WHERE wt.id IN :ids", WinMarginType.class)
                .setParameter("ids", seriesTypeIds)
                .getResultList();
        });
    }
}