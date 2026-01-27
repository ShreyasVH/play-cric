package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.SeriesTeamsMap;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.stream.Collectors;

public class SeriesTeamsMapRepository {
    private final JPAApi jpaApi;

    @Inject
    public SeriesTeamsMapRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public void create(Integer seriesId, List<Long> teamIds) {
        jpaApi.withTransaction(em -> {
            create(em, seriesId, teamIds);
        });
    }

    public void create(EntityManager em, Integer seriesId, List<Long> teamIds) {
        List<SeriesTeamsMap> seriesTeamsMaps = teamIds.stream().map(teamId -> new SeriesTeamsMap(null, seriesId, teamId)).collect(Collectors.toList());
        seriesTeamsMaps.forEach(em::persist);
    }

    public List<SeriesTeamsMap> getBySeriesIds(List<Integer> seriesIds) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT stm FROM SeriesTeamsMap stm WHERE stm.seriesId IN :seriesIds",
                    SeriesTeamsMap.class
            )
            .setParameter("seriesIds", seriesIds)
            .getResultList();
        });
    }

    public void delete(Integer seriesId, List<Long> teamIds) {
        jpaApi.withTransaction(em -> {
            delete(em, seriesId, teamIds);
        });
    }

    public void delete(EntityManager em, Integer seriesId, List<Long> teamIds) {
        em.createQuery(
                "DELETE FROM SeriesTeamsMap stm WHERE stm.seriesId = :seriesId AND stm.teamId IN :teamIds"
        )
        .setParameter("seriesId", seriesId)
        .setParameter("teamIds", teamIds)
        .executeUpdate();
    }

    public void remove(Integer seriesId)
    {
        jpaApi.withTransaction(em -> {
            remove(em, seriesId);
        });
    }

    public void remove(EntityManager em, Integer seriesId)
    {
        em.createQuery(
            "DELETE FROM SeriesTeamsMap stm WHERE stm.seriesId = :seriesId"
        )
        .setParameter("seriesId", seriesId)
        .executeUpdate();
    }
}
