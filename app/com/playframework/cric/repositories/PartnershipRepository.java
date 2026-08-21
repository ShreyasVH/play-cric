package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.Partnership;
import com.playframework.cric.requests.matches.PartnershipRequest;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartnershipRepository {
    private final JPAApi jpaApi;

    @Inject
    public PartnershipRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<Partnership> add(List<PartnershipRequest> battingScoreRequests, Map<Long, Integer> matchPlayerMaps) {
        return jpaApi.withTransaction(em -> {
            return add(em, battingScoreRequests, matchPlayerMaps);
        });
    }

    public List<Partnership> add(EntityManager em, List<PartnershipRequest> partnershipRequests, Map<Long, Integer> matchPlayerMaps) {
        List<Partnership> partnerships = partnershipRequests.stream().flatMap(partnershipRequest -> Stream.of(
                new Partnership(partnershipRequest, matchPlayerMaps, true),
                new Partnership(partnershipRequest, matchPlayerMaps, false)
        )).collect(Collectors.toList());
        partnerships.forEach(em::persist);
        return partnerships;
    }
}