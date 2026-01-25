package com.playframework.cric.repositories;

import com.google.inject.Inject;

import com.playframework.cric.models.GameType;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;

public class GameTypeRepository {
    private final JPAApi jpaApi;

    @Inject
    public GameTypeRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public GameType getById(Integer gameTypeId) {
        return jpaApi.withTransaction(em -> {
            return getById(em, gameTypeId);
        });
    }

    public GameType getById(EntityManager em, Integer gameTypeId) {
        return em.createQuery("SELECT gt FROM GameType gt WHERE gt.id = :id", GameType.class)
                .setParameter("id", gameTypeId)
                .getSingleResult();
    }

    public List<GameType> getByIds(List<Integer> gameTypeIds) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT gt FROM GameType gt WHERE gt.id IN :ids", GameType.class)
                .setParameter("ids", gameTypeIds)
                .getResultList();
        });
    }
}