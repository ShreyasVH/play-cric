package com.playframework.cric.repositories;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.models.Country;
import com.playframework.cric.requests.countries.CreateRequest;
import com.playframework.cric.utils.Utils;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

public class CountryRepository {
    private final JPAApi jpaApi;

    @Inject
    public CountryRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

	public Country create(CreateRequest createRequest) {
        Country country = Utils.convertObject(createRequest, Country.class);
        return jpaApi.withTransaction(em -> {
            em.persist(country);
            return country;
        });
    }

    public Country getById(Long id) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT c FROM Country c WHERE c.id = :id", Country.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
        });
    }

    public List<Country> getByIds(List<Long> ids) {
        return jpaApi.withTransaction(em -> {
            return getByIds(em, ids);
        });
    }

    public List<Country> getByIds(EntityManager em, List<Long> ids) {
        return em.createQuery("SELECT c FROM Country c WHERE c.id IN :ids", Country.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public Country getByName(String name) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class)
                    .setParameter("name", name)
                    .getSingleResultOrNull();
        });
    }

    public List<Country> getByNamePattern(String name) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT c FROM Country c WHERE LOWER(c.name) like LOWER(:name)", Country.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
        });
    }

    public List<Country> getAll(int page, int limit) {
        int offset = (page - 1) * limit;

        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                    "SELECT c FROM Country c ORDER BY c.name ASC",
                    Country.class
            )
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        });
    }

    public long getTotalCount() {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT COUNT(c) FROM Country c",
                Long.class
            )
            .getSingleResult();
        });
    }
}