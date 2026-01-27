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

    public void create(Integer entityId, List<Integer> tagIds, String tagEntityType) {
        jpaApi.withTransaction(em -> {
            create(em, entityId, tagIds, tagEntityType);
        });
    }

    public void create(EntityManager em, Integer entityId, List<Integer> tagIds, String tagEntityType) {
        List<TagMap> tagMaps = tagIds.stream().map(tagId -> new TagMap(null, tagEntityType, entityId, tagId)).collect(Collectors.toList());
        tagMaps.forEach(em::persist);
    }

    public List<TagMap> get(Integer entityId, String tagEntityType)
    {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT tm FROM TagMap tm WHERE tm.entityId = :entityId AND tm.entityType = :entityType",
                TagMap.class
            )
            .setParameter("entityId", entityId)
            .setParameter("entityType", tagEntityType)
            .getResultList();
        });
    }

    public void remove(Integer entityId, String tagEntityType)
    {
        jpaApi.withTransaction(em -> {
            remove(em, entityId, tagEntityType);
        });
    }

    public void remove(EntityManager em, Integer entityId, String tagEntityType)
    {
        em.createQuery(
                "DELETE FROM TagMap tm WHERE tm.entityId = :entityId AND tm.entityType = :entityType"
        )
                .setParameter("entityId", entityId)
                .setParameter("entityType", tagEntityType)
                .executeUpdate();
    }
}