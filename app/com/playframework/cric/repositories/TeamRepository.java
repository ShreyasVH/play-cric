package com.playframework.cric.repositories;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.models.Team;
import com.playframework.cric.requests.teams.CreateRequest;
import com.playframework.cric.utils.Utils;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

public class TeamRepository {
    private final JPAApi jpaApi;

    @Inject
    public TeamRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public Team create(CreateRequest createRequest) {
        Team team = Utils.convertObject(createRequest, Team.class);
        return jpaApi.withTransaction(em -> {
            em.persist(team);
            return team;
        });
    }

    public Team getByNameAndCountryIdAndTeamTypeId(String name, Long countryId, Integer teamTypeId) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery("SELECT t FROM Team t WHERE t.name = :name AND t.countryId = :countryId AND t.typeId = :typeId", Team.class)
                .setParameter("name", name)
                .setParameter("countryId", countryId)
                .setParameter("typeId", teamTypeId)
                .getSingleResultOrNull();
        });
    }

    public List<Team> getAll(int page, int limit) {
        int offset = (page - 1) * limit;

        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT t FROM Team t ORDER BY t.name ASC",
                Team.class
            )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
        });
    }

    public long getTotalCount() {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                "SELECT COUNT(t) FROM Team t",
                Long.class
            )
            .getSingleResult();
        });
    }

    public List<Team> getByIds(List<Long> ids) {
        return jpaApi.withTransaction(em -> {
            return getByIds(em, ids);
        });
    }

    public List<Team> getByIds(EntityManager em, List<Long> ids) {
        return em.createQuery("SELECT t FROM Team t WHERE t.id IN :ids", Team.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}