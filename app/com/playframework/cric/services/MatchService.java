package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.exceptions.ConflictException;
import com.playframework.cric.models.Match;
import com.playframework.cric.repositories.MatchRepository;
import com.playframework.cric.requests.matches.CreateRequest;

public class MatchService {
    private final MatchRepository matchRepository;

    @Inject
    public MatchService(MatchRepository matchRepository)
    {
        this.matchRepository = matchRepository;
    }

    public Match create(CreateRequest createRequest)
    {
        createRequest.validate();

        Match existingMatch = matchRepository.getByStadiumAndStartTime(createRequest.getStadiumId(), createRequest.getStartTime());
        if(null != existingMatch)
        {
            throw new ConflictException("Match");
        }

        return matchRepository.create(createRequest);
    }
}
