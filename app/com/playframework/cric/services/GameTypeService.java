package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.repositories.GameTypeRepository;
import com.playframework.cric.models.GameType;
import com.playframework.cric.exceptions.ConflictException;

public class GameTypeService {
    private final GameTypeRepository gameTypeRepository;

    @Inject
    public GameTypeService(GameTypeRepository GameTypeRepository) {
        this.gameTypeRepository = GameTypeRepository;
    }

    public GameType getById(Integer id) {
        return gameTypeRepository.getById(id);
    }
}