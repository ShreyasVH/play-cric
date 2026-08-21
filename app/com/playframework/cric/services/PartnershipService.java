package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.Partnership;
import com.playframework.cric.repositories.PartnershipRepository;
import com.playframework.cric.requests.matches.PartnershipRequest;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;

public class PartnershipService {
    private final PartnershipRepository partnershipRepository;

    @Inject
    public PartnershipService(PartnershipRepository partnershipRepository)
    {
        this.partnershipRepository = partnershipRepository;
    }

    public List<Partnership> add(List<PartnershipRequest> partnershipRequests, Map<Long, Integer> matchPlayerMaps)
    {
        return partnershipRepository.add(partnershipRequests, matchPlayerMaps);
    }

    public List<Partnership> add(EntityManager em, List<PartnershipRequest> partnershipRequests, Map<Long, Integer> matchPlayerMaps)
    {
        return partnershipRepository.add(em, partnershipRequests, matchPlayerMaps);
    }

    public List<Partnership> getPartnerships(List<Integer> matchPlayerIds)
    {
        return partnershipRepository.getPartnerships(matchPlayerIds);
    }

    public void remove(List<Integer> matchPlayerIds)
    {
        partnershipRepository.remove(matchPlayerIds);
    }

    public void remove(EntityManager em, List<Integer> matchPlayerIds)
    {
        partnershipRepository.remove(em, matchPlayerIds);
    }
}
