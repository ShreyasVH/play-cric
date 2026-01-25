package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.Match;
import com.playframework.cric.requests.matches.CreateRequest;
import com.playframework.cric.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import play.db.jpa.JPAApi;

import java.time.LocalDateTime;
import java.util.List;

public class MatchRepository {
    private final JPAApi jpaApi;

    @Inject
    public MatchRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public Match create(CreateRequest createRequest)
    {
        return jpaApi.withTransaction(em -> {
            return create(em, createRequest);
        });
    }

    public Match create(EntityManager em, CreateRequest createRequest)
    {
        Match match = Utils.convertObject(createRequest, Match.class);
        em.persist(match);
        return match;
    }

    public Match getByStadiumAndStartTime(Long stadiumId, LocalDateTime startTime)
    {
        return jpaApi.withTransaction(em -> {
            try {
                return em.createQuery("SELECT m FROM Match m WHERE m.stadiumId = :stadiumId AND m.startTime = :startTime", Match.class)
                        .setParameter("stadiumId", stadiumId)
                        .setParameter("startTime", startTime)
                        .getSingleResult();
            } catch (NoResultException ex) {
                return null;
            }
        });
    }

    public List<Match> getBySeriesId(Integer seriesId)
    {
        return jpaApi.withTransaction(em -> {
            return getBySeriesId(em, seriesId);
        });
    }

    public List<Match> getBySeriesId(EntityManager em, Integer seriesId)
    {
        return em.createQuery("SELECT m FROM Match m WHERE m.seriesId = :seriesId ORDER BY m.startTime ASC", Match.class)
                .setParameter("seriesId", seriesId)
                .getResultList();
    }

    public Match getById(Integer id)
    {
        return jpaApi.withTransaction(em -> {
            return getById(em, id);
        });
    }

    public Match getById(EntityManager em, Integer id)
    {
        return em.createQuery("SELECT m FROM Match m WHERE m.id = :id", Match.class)
            .setParameter("id", id)
            .getSingleResultOrNull();
    }

    public void remove(Integer matchId)
    {
        jpaApi.withTransaction(em -> {
            remove(em, matchId);
        });
    }

    public void remove(EntityManager em, Integer matchId)
    {
        em.createQuery(
            "DELETE FROM Match m WHERE m.id = :id",
            Match.class
        )
        .setParameter("id", matchId).executeUpdate();
    }
}
