package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.ManOfTheSeries;
import com.playframework.cric.requests.players.MergeRequest;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.stream.Collectors;

public class ManOfTheSeriesRepository {
    private final JPAApi jpaApi;

    @Inject
    public ManOfTheSeriesRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<ManOfTheSeries> getBySeriesIds(List<Integer> seriesIds) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT mots FROM ManOfTheSeries mots WHERE mots.seriesId IN :ids",
                    ManOfTheSeries.class
            )
            .setParameter("ids", seriesIds)
            .getResultList();
        });
    }

    public void add(Integer seriesId, List<Long> playerIds) {
        jpaApi.withTransaction(em -> {
            add(em, seriesId, playerIds);
        });
    }

    public void add(EntityManager em, Integer seriesId, List<Long> playerIds) {
        List<ManOfTheSeries> manOfTheSeriesList = playerIds.stream().map(playerId -> new ManOfTheSeries(null, seriesId, playerId)).collect(Collectors.toList());
        manOfTheSeriesList.forEach(em::persist);
    }

    public void delete(Integer seriesId, List<Long> playerIds) {
        jpaApi.withTransaction(em -> {
            delete(em, seriesId, playerIds);
        });
    }

    public void delete(EntityManager em, Integer seriesId, List<Long> playerIds) {
        em.createQuery(
                "DELETE FROM ManOfTheSeries mots WHERE mots.seriesId = :seriesId AND mots.playerId IN :playerIds",
                ManOfTheSeries.class
        )
                .setParameter("seriesId", seriesId).setParameter("playerIds", playerIds).executeUpdate();
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
            "DELETE FROM ManOfTheSeries mots WHERE mots.seriesId = :seriesId",
            ManOfTheSeries.class
        )
        .setParameter("seriesId", seriesId).executeUpdate();
    }

    public void merge(MergeRequest mergeRequest)
    {
        jpaApi.withTransaction(em -> {
            merge(em, mergeRequest);
        });
    }

    public void merge(EntityManager em, MergeRequest mergeRequest)
    {
        List<ManOfTheSeries> manOfTheSeriesList = em.createQuery(
                "SELECT mots FROM ManOfTheSeries mots WHERE mots.playerId = :playerId",
                ManOfTheSeries.class
        )
                .setParameter("playerId", mergeRequest.getPlayerIdToMerge())
                .getResultList();
        manOfTheSeriesList.forEach(manOfTheSeries -> manOfTheSeries.setPlayerId(mergeRequest.getOriginalPlayerId()));

        manOfTheSeriesList.forEach(em::merge);
    }
}
