package com.playframework.cric.repositories;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.models.Stadium;
import com.playframework.cric.requests.stadiums.CreateRequest;
import com.playframework.cric.utils.Utils;
import jakarta.persistence.NoResultException;
import play.db.jpa.JPAApi;

public class StadiumRepository {
    private final JPAApi jpaApi;

    @Inject
    public StadiumRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

	public Stadium create(CreateRequest createRequest) {
        Stadium stadium = Utils.convertObject(createRequest, Stadium.class);
        return jpaApi.withTransaction(em -> {
            em.persist(stadium);
            return stadium;
        });
    }

    public Stadium getByNameAndCountryIdAndCity(String name, Long countryId, String city) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT s FROM Stadium s WHERE s.name = :name AND s.countryId = :countryId AND s.city = :city", Stadium.class)
                .setParameter("name", name)
                .setParameter("countryId", countryId)
                .setParameter("city", city)
                .getSingleResultOrNull();
        });
    }

    public List<Stadium> getAll(int page, int limit) {
        int offset = (page - 1) * limit;

        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT s FROM Stadium s ORDER BY s.name ASC",
                    Stadium.class
            )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        });
    }

    public long getTotalCount() {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT COUNT(s) FROM Stadium s",
                    Long.class
            )
            .getSingleResult();
        });
    }

    public Stadium getById(Long id) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT s FROM Stadium s WHERE s.id = :id", Stadium.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
        });
    }

    public List<Stadium> getByIds(List<Long> ids) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT s FROM Stadium s WHERE s.id IN :ids", Stadium.class)
                .setParameter("ids", ids)
                .getResultList();
        });
    }
}