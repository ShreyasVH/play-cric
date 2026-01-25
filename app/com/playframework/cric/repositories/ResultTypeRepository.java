package com.playframework.cric.repositories;

import com.google.inject.Inject;

import com.playframework.cric.models.ResultType;
import play.db.jpa.JPAApi;

import java.util.List;

public class ResultTypeRepository {
    private final JPAApi jpaApi;

    @Inject
    public ResultTypeRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public ResultType getById(Integer typeId) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT rt FROM ResultType rt WHERE rt.id = :id", ResultType.class)
                    .setParameter("id", typeId)
                    .getSingleResult();
        });
    }

    public List<ResultType> getByIds(List<Integer> typeIds) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT gt FROM ResultType gt WHERE gt.id IN :ids", ResultType.class)
                .setParameter("ids", typeIds)
                .getResultList();
        });
    }
}