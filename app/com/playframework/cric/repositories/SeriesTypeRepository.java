package com.playframework.cric.repositories;

import com.google.inject.Inject;

import com.playframework.cric.models.SeriesType;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;

public class SeriesTypeRepository {
    private final JPAApi jpaApi;

    @Inject
    public SeriesTypeRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public SeriesType getById(Integer seriesTypeId) {
        return jpaApi.withTransaction(em -> {
            return getById(em, seriesTypeId);
        });
    }

    public SeriesType getById(EntityManager em, Integer seriesTypeId) {
        return em.createQuery("SELECT st FROM SeriesType st WHERE st.id = :id", SeriesType.class)
                .setParameter("id", seriesTypeId)
                .getSingleResult();
    }

    public List<SeriesType> getByIds(List<Integer> seriesTypeIds) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT st FROM SeriesType st WHERE st.id IN :ids", SeriesType.class)
                    .setParameter("ids", seriesTypeIds)
                    .getResultList();
        });
    }
}