package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.repositories.PlayerRepository;
import com.playframework.cric.requests.FilterRequest;
import com.playframework.cric.responses.StatsResponse;

public class StatsService {
    private final PlayerRepository playerRepository;

    @Inject
    public StatsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public StatsResponse getStats(FilterRequest filterRequest)
    {
        StatsResponse statsResponse = new StatsResponse();
        if("batting".equals(filterRequest.getType()))
        {
            statsResponse = this.playerRepository.getBattingStats(filterRequest);
        }
        else if("bowling".equals(filterRequest.getType()))
        {
            statsResponse = this.playerRepository.getBowlingStats(filterRequest);
        }
        else if("fielding".equals(filterRequest.getType()))
        {
            statsResponse = this.playerRepository.getFieldingStats(filterRequest);
        }
        return statsResponse;
    }
}
