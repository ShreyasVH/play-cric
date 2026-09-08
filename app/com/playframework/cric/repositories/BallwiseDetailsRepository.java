package com.playframework.cric.repositories;

import com.google.inject.Inject;
import com.playframework.cric.models.BallwiseDetail;
import com.playframework.cric.models.BattingScore;
import com.playframework.cric.requests.matches.BallwiseDetailRequest;
import com.playframework.cric.requests.matches.BattingScoreRequest;
import jakarta.persistence.EntityManager;
import play.db.jpa.JPAApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BallwiseDetailsRepository {
    private final JPAApi jpaApi;

    @Inject
    public BallwiseDetailsRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public List<BallwiseDetail> add(List<BallwiseDetailRequest> ballwiseDetailRequests, Map<Long, Integer> matchPlayerMaps)
    {
        return jpaApi.withTransaction(em -> {
            return add(em, ballwiseDetailRequests, matchPlayerMaps);
        });
    }

    public List<BallwiseDetail> add(EntityManager em, List<BallwiseDetailRequest> ballwiseDetailRequests, Map<Long, Integer> matchPlayerMaps)
    {
        List<BallwiseDetail> ballwiseDetails = ballwiseDetailRequests.stream().map(ballwiseDetailRequest -> new BallwiseDetail(ballwiseDetailRequest, matchPlayerMaps)).toList();
        ballwiseDetails.forEach(em::persist);
        return ballwiseDetails;
    }

//    public List<BattingScore> getBattingScores(List<Integer> matchPlayerIds)
//    {
//        return jpaApi.withTransaction(em -> {
//                    return em.createQuery(
//                                    "SELECT bs FROM BattingScore bs WHERE bs.matchPlayerId IN :ids ORDER BY bs.innings, bs.number",
//                                    BattingScore.class
//                            )
//                            .setParameter("ids", matchPlayerIds)
//                            .getResultList();
//                }
//        );
//    }
//
//    public void remove(List<Integer> matchPlayerIds)
//    {
//        jpaApi.withTransaction(em -> {
//            remove(em, matchPlayerIds);
//        });
//    }
//
//    public void remove(EntityManager em, List<Integer> matchPlayerIds)
//    {
//        em.createQuery(
//                        "DELETE FROM BattingScore bs WHERE bs.matchPlayerId IN :ids"
//                )
//                .setParameter("ids", matchPlayerIds).executeUpdate();
//    }
}
