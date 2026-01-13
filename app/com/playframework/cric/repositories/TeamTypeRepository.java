package com.playframework.cric.repositories;

import com.google.inject.Inject;

import com.playframework.cric.models.TeamType;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;

public class TeamTypeRepository {
    private final JPAApi jpaApi;

    @Inject
    public TeamTypeRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public TeamType getById(Integer seriesTypeId) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT tt FROM TeamType tt WHERE tt.id = :id", TeamType.class)
                .setParameter("id", seriesTypeId)
                .getSingleResult();
        });
    }

    public List<TeamType> getByIds(List<Integer> seriesTypeIds) {
        return jpaApi.withTransaction(em -> {
            return getByIds(em, seriesTypeIds);
        });
    }

    public List<TeamType> getByIds(EntityManager em, List<Integer> seriesTypeIds) {
        return em.createQuery("SELECT tt FROM TeamType tt WHERE tt.id IN :ids", TeamType.class)
                .setParameter("ids", seriesTypeIds)
                .getResultList();
    }
}