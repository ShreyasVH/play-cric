package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.Extras;
import com.playframework.cric.repositories.ExtrasRepository;
import com.playframework.cric.requests.matches.ExtrasRequest;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ExtrasService {
    private final ExtrasRepository extrasRepository;

    @Inject
    public ExtrasService(ExtrasRepository extrasRepository)
    {
        this.extrasRepository = extrasRepository;
    }

    public List<Extras> add(Integer matchId, List<ExtrasRequest> extrasRequests)
    {
        return extrasRepository.add(matchId, extrasRequests);
    }

    public List<Extras> add(EntityManager em, Integer matchId, List<ExtrasRequest> extrasRequests)
    {
        return extrasRepository.add(em, matchId, extrasRequests);
    }

    public List<Extras> getByMatchId(Integer matchId)
    {
        return extrasRepository.getByMatchId(matchId);
    }

    public void remove(Integer matchId)
    {
        extrasRepository.remove(matchId);
    }

    public void remove(EntityManager em, Integer matchId)
    {
        extrasRepository.remove(em, matchId);
    }
}
