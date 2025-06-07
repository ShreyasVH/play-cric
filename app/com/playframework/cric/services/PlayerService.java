package com.playframework.cric.services;

import com.google.inject.Inject;
import java.util.List;

import com.playframework.cric.exceptions.ConflictException;
import com.playframework.cric.models.Player;
import com.playframework.cric.repositories.PlayerRepository;
import com.playframework.cric.requests.players.CreateRequest;
import com.playframework.cric.requests.players.MergeRequest;

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

    public List<Player> getByIds(List<Long> ids) {
        return playerRepository.getByIds(ids);
    }

    public Player getById(Long id)
    {
        return playerRepository.getById(id);
    }

    public void remove(Long id)
    {
        playerRepository.remove(id);
    }

    public List<Player> search(String keyword, int page, int limit)
    {
        return playerRepository.search(keyword, page, limit);
    }

    public int searchCount(String keyword)
    {
        return playerRepository.searchCount(keyword);
    }
}
