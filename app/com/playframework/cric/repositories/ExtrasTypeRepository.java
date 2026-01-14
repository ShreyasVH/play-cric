package com.playframework.cric.repositories;

import com.google.inject.Inject;

import com.playframework.cric.models.ExtrasType;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;

public class ExtrasTypeRepository {
    private final JPAApi jpaApi;

    @Inject
    public ExtrasTypeRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<ExtrasType> getAll() {
        return jpaApi.withTransaction(em -> {
            return getAll(em);

        });
    }

    public List<ExtrasType> getAll(EntityManager em) {
        return em.createQuery(
                "SELECT et FROM ExtrasType et",
                ExtrasType.class
        )
                .getResultList();
    }
}