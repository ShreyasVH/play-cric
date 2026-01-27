package com.playframework.cric.repositories;

import com.google.inject.Inject;

import com.playframework.cric.models.Series;
import com.playframework.cric.requests.series.CreateRequest;
import com.playframework.cric.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import play.db.jpa.JPAApi;

import java.util.List;

public class SeriesRepository {
    private final JPAApi jpaApi;

    @Inject
    public SeriesRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public Series create(CreateRequest createRequest) {
        return jpaApi.withTransaction(em -> {
            return create(em, createRequest);
        });
    }

    public Series create(EntityManager em, CreateRequest createRequest) {
        Series series = Utils.convertObject(createRequest, Series.class);
        em.persist(series);
        return series;
    }

    public Series getByNameAndTourIdAndGameTypeId(String name, Long tourId, Integer gameTypeId) {
        return jpaApi.withTransaction(em -> {
            return getByNameAndTourIdAndGameTypeId(em, name, tourId, gameTypeId);
        });
    }

    public Series getByNameAndTourIdAndGameTypeId(EntityManager em, String name, Long tourId, Integer gameTypeId) {
        return em.createQuery("SELECT s FROM Series s WHERE s.name = :name AND s.tourId = :tourId AND s.gameTypeId = :gameTypeId", Series.class)
                .setParameter("name", name)
                .setParameter("tourId", tourId)
                .setParameter("gameTypeId", gameTypeId)
                .getSingleResultOrNull();
    }

    public List<Series> getAll(int page, int limit) {
        int offset = (page - 1) * limit;

        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT s FROM Series s ORDER BY s.name ASC",
                    Series.class
            )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        });
    }

    public long getTotalCount() {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT COUNT(s) FROM Series s",
                Long.class
            )
            .getSingleResult();
        });
    }

    public Series getById(Integer id) {
        return jpaApi.withTransaction(em -> {
            return getById(em, id);
        });
    }

    public Series getById(EntityManager em, Integer id) {
        return em.createQuery("SELECT s FROM Series s WHERE s.id = :id", Series.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
    }

    public void update(Series series) {
        jpaApi.withTransaction(em -> {
            update(em, series);
        });
    }

    public void update(EntityManager em, Series series) {
        em.merge(series);
    }

    public List<Series> getByTourId(Long tourId)
    {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT s FROM Series s WHERE s.tourId = :tourId ORDER BY s.startTime DESC", Series.class)
                .setParameter("tourId", tourId)
                .getResultList();
        });
    }

    public void remove(Integer id)
    {
        jpaApi.withTransaction(em -> {
            remove(em, id);
        });
    }

    public void remove(EntityManager em, Integer id)
    {
        em.createQuery(
                "DELETE FROM Series s WHERE s.id = :id"
        )
        .setParameter("id", id).executeUpdate();
    }
}