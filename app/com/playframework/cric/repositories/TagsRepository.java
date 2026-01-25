package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.Tag;
import play.db.jpa.JPAApi;

import java.util.List;

public class TagsRepository {
    private final JPAApi jpaApi;

    @Inject
    public TagsRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<Tag> getAll(int page, int limit)
    {
        int offset = (page - 1) * limit;

        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT t FROM Tag t ORDER BY t.name ASC",
                    Tag.class
            )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        });
    }

    public long getTotalCount() {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT COUNT(t) FROM Tag t",
                    Long.class
            )
            .getSingleResult();
        });
    }

    public Tag getById(Integer id) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT t FROM Tag t WHERE t.id = :id", Tag.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
        });
    }

    public List<Tag> getByIds(List<Integer> id) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT t FROM Tag t WHERE t.id IN :ids", Tag.class)
                .setParameter("ids", id)
                .getResultList();
        });
    }
}