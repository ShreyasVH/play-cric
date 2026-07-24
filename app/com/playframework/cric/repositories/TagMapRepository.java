package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.TagMap;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.stream.Collectors;

public class TagMapRepository {
    private final JPAApi jpaApi;

    @Inject
    public TagMapRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public void create(Integer entityId, List<Integer> tagIds) {
        jpaApi.withTransaction(em -> {
            create(em, entityId, tagIds);
        });
    }

    public void create(EntityManager em, Integer entityId, List<Integer> tagIds) {
        List<TagMap> tagMaps = tagIds.stream().map(tagId -> new TagMap(null, entityId, tagId)).collect(Collectors.toList());
        tagMaps.forEach(em::persist);
    }

    public List<TagMap> get(Integer entityId, List<Integer> tagIds)
    {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT tm FROM TagMap tm WHERE tm.entityId = :entityId AND tm.tagId IN :tagIds",
                TagMap.class
            )
            .setParameter("entityId", entityId)
            .setParameter("tagIds", tagIds)
            .getResultList();
        });
    }

    public void remove(Integer entityId, List<Integer> tagIds)
    {
        jpaApi.withTransaction(em -> {
            remove(em, entityId, tagIds);
        });
    }

    public void remove(EntityManager em, Integer entityId, List<Integer> tagIds)
    {
        em.createQuery(
                "DELETE FROM TagMap tm WHERE tm.entityId = :entityId AND tm.tagId IN :tagIds"
        )
                .setParameter("entityId", entityId)
                .setParameter("tagIds", tagIds)
                .executeUpdate();
    }
}