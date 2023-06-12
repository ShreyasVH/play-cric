package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.exceptions.ConflictException;
import com.playframework.cric.models.Player;
import com.playframework.cric.repositories.PlayerRepository;
import com.playframework.cric.requests.players.CreateRequest;

public class PlayerService {
    private final PlayerRepository playerRepository;

    @Inject
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player create(CreateRequest createRequest) {
        createRequest.validate();

        Player existingPlayer = playerRepository.getByNameAndCountryIdAndDateOfBirth(createRequest.getName(), createRequest.getCountryId(), createRequest.getDateOfBirth());

        if(null != existingPlayer) {
            throw new ConflictException("Player");
        }

        return playerRepository.create(createRequest);
    }

    public List<Player> getAll(int page, int limit) {
        return playerRepository.getAll(page, limit);
    }

    public int getTotalCount() {
        return playerRepository.getTotalCount();
    }
}
