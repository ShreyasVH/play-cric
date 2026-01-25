package com.playframework.cric.repositories;

import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.playframework.cric.models.Tour;
import com.playframework.cric.requests.tours.CreateRequest;
import com.playframework.cric.utils.Utils;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

public class TourRepository {
    private final JPAApi jpaApi;

    @Inject
    public TourRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public Tour create(CreateRequest createRequest) {
        Tour tour = Utils.convertObject(createRequest, Tour.class);
        return jpaApi.withTransaction(em -> {
            em.persist(tour);
            return tour;
        });
    }

    public Tour getByNameAndStartTime(String name, LocalDateTime startTime) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT t FROM Tour t WHERE t.name = :name AND t.startTime = :startTime", Tour.class)
                .setParameter("name", name)
                .setParameter("startTime", startTime)
                .getSingleResultOrNull();
        });
    }

    public Tour getById(Long id) {
        return jpaApi.withTransaction(em -> {
            return getById(em, id);
        });
    }

    public Tour getById(EntityManager em, Long id) {
        return em.createQuery("SELECT t FROM Tour t WHERE t.id = :id", Tour.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
    }

    public List<Tour> getByIds(List<Long> ids) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT t FROM Tour t WHERE t.id IN :ids", Tour.class)
                .setParameter("ids", ids)
                .getResultList();
        });
    }

    public List<Tour> getAll(int year, int page, int limit) {
        LocalDateTime startTime = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime endTime = startTime.plusYears(1L);

        int offset = (page - 1) * limit;

        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT t FROM Tour t WHERE t.startTime >= :startTime AND t.startTime <= :endTime ORDER BY t.startTime DESC",
                    Tour.class
            )
            .setParameter("startTime", startTime)
            .setParameter("endTime", endTime)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        });
    }

    public long getTotalCountForYear(int year) {
        LocalDateTime startTime = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime endTime = startTime.plusYears(1L);
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT COUNT(t) FROM Tour t WHERE t.startTime >= :startTime AND t.startTime <= :endTime",
                Long.class
            )
            .setParameter("startTime", startTime)
            .setParameter("endTime", endTime)
            .getSingleResult();
        });
    }

    public List<Integer> getAllYears() {
        List<Integer> years = new ArrayList<>();
        String query = "SELECT DISTINCT YEAR(start_time) AS year FROM tours ORDER BY year DESC";
        jpaApi.withTransaction(em -> {
            List<Integer> rows = em.createNativeQuery(query).getResultList();

            for (int year: rows) {
                years.add(year);
            }

        });

        return years;
    }
}