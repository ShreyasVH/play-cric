package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.repositories.TeamTypeRepository;
import com.playframework.cric.models.TeamType;
import com.playframework.cric.exceptions.ConflictException;

public class TeamTypeService {
    private final TeamTypeRepository teamTypeRepository;

    @Inject
    public TeamTypeService(TeamTypeRepository teamTypeRepository) {
        this.teamTypeRepository = teamTypeRepository;
    }

    public TeamType getById(Integer id) {
        return teamTypeRepository.getById(id);
    }
}