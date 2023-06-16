package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.models.Stadium;
import com.playframework.cric.repositories.TeamRepository;
import com.playframework.cric.repositories.TeamTypeRepository;
import com.playframework.cric.requests.teams.CreateRequest;
import com.playframework.cric.models.Team;
import com.playframework.cric.exceptions.ConflictException;

public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamTypeRepository teamTypeRepository;

    @Inject
    public TeamService(TeamRepository teamRepository, TeamTypeRepository teamTypeRepository) {
        this.teamRepository = teamRepository;
        this.teamTypeRepository = teamTypeRepository;
    }

    public Team create(CreateRequest createRequest) {
        createRequest.validate();

        Team existingTeam = teamRepository.getByNameAndCountryIdAndTeamTypeId(createRequest.getName(), createRequest.getCountryId(), createRequest.getTypeId());
        if(null != existingTeam) {
            throw new ConflictException("Team");
        }

        return teamRepository.create(createRequest);
    }

    public List<Team> getAll(int page, int limit) {
        return teamRepository.getAll(page, limit);
    }

    public int getTotalCount() {
        return teamRepository.getTotalCount();
    }

    public List<Team> getByIds(List<Long> teamIds) {
        return teamRepository.getByIds(teamIds);
    }
}