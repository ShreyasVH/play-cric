package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.exceptions.ConflictException;
import com.playframework.cric.models.Match;
import com.playframework.cric.repositories.MatchRepository;
import com.playframework.cric.requests.matches.CreateRequest;

import java.util.List;

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

    public List<Match> getBySeriesId(Long seriesId)
    {
        return matchRepository.getBySeriesId(seriesId);
    }

    public Match getById(Integer id)
    {
        return matchRepository.getById(id);
    }

    public void remove(Integer matchId)
    {
        matchRepository.remove(matchId);
    }
}
