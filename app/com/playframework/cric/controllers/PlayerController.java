package com.playframework.cric.controllers;

import com.google.inject.Inject;
import com.playframework.cric.responses.*;
import com.playframework.cric.services.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.playframework.cric.exceptions.NotFoundException;
import com.playframework.cric.models.Country;
import com.playframework.cric.models.Player;
import com.playframework.cric.requests.players.CreateRequest;
import com.playframework.cric.utils.Utils;

public class PlayerController extends Controller {
    private final PlayerService playerService;
    private final CountryService countryService;
    private final BattingScoreService battingScoreService;
    private final BowlingFigureService bowlingFigureService;
    private final FielderDismissalService fielderDismissalService;

    @Inject
    public PlayerController(PlayerService playerService, CountryService countryService, BattingScoreService battingScoreService, BowlingFigureService bowlingFigureService, FielderDismissalService fielderDismissalService) {
        this.playerService = playerService;
        this.countryService = countryService;
        this.battingScoreService = battingScoreService;
        this.bowlingFigureService = bowlingFigureService;
        this.fielderDismissalService = fielderDismissalService;
    }

    public Result create(Http.Request request) {
        CreateRequest createRequest = Utils.convertObject(request.body().asJson(), CreateRequest.class);

        Country country = countryService.getById(createRequest.getCountryId());
        if(null == country)
        {
            throw new NotFoundException("Country");
        }

        Player player = playerService.create(createRequest);

        return created(Json.toJson(new Response(new PlayerMiniResponse(player, new CountryResponse(country)))));
    }

    public Result getAll(int page, int limit) {
        List<Player> players = playerService.getAll(page, limit);
        int totalCount = 0;
        if(page == 1) {
            totalCount = playerService.getTotalCount();
        }

        List<Long> countryIds = players.stream().map(Player::getCountryId).collect(Collectors.toList());

        List<Country> countries = countryService.getByIds(countryIds);
        Map<Long, Country> countryMap = countries.stream().collect(Collectors.toMap(Country::getId, country -> country));

        List<PlayerMiniResponse> playerResponses = players.stream().map(player -> new PlayerMiniResponse(player, new CountryResponse(countryMap.get(player.getCountryId())))).collect(Collectors.toList());
        PaginatedResponse<PlayerMiniResponse> paginatedResponse = new PaginatedResponse<>(totalCount, playerResponses, page, limit);
        return ok(Json.toJson(new Response(paginatedResponse)));
    }

    public Result get(Long id)
    {
        Player player = playerService.getById(id);
        if(null == player)
        {
            throw new NotFoundException("Player");
        }

        PlayerResponse playerResponse = new PlayerResponse(player);
        Country country = countryService.getById(player.getCountryId());
        playerResponse.setCountry(new CountryResponse(country));

        Map<String, Map<String, Integer>> dismissalStats = battingScoreService.getDismissalStats(id);
        playerResponse.setDismissalStats(dismissalStats);

        Map<String, Integer> dismissalCountMap = new HashMap<>();
        for(String gameType: dismissalStats.keySet())
        {
            Integer dismissalCount = 0;
            for(String key: dismissalStats.get(gameType).keySet())
            {
                dismissalCount += dismissalStats.get(gameType).get(key);
            }
            dismissalCountMap.put(gameType, dismissalCount);
        }

        Map<String, Map<String, Integer>> basicBattingStats = battingScoreService.getBattingStats(id);
        if(!basicBattingStats.keySet().isEmpty())
        {
            Map<String, BattingStats> battingStatsMap = new HashMap<>();

            for(String gameType: basicBattingStats.keySet())
            {
                BattingStats battingStats = new BattingStats(basicBattingStats.get(gameType));
                battingStats.setNotOuts(battingStats.getInnings() - dismissalCountMap.getOrDefault(gameType, 0));

                if(dismissalCountMap.getOrDefault(gameType, 0) > 0)
                {
                    battingStats.setAverage(battingStats.getRuns() * 1.0 / dismissalCountMap.get(gameType));
                }

                if(battingStats.getBalls() > 0)
                {
                    battingStats.setStrikeRate(battingStats.getRuns() * 100.0 / battingStats.getBalls());
                }

                battingStatsMap.put(gameType, battingStats);
            }

            playerResponse.setBattingStats(battingStatsMap);
        }

        Map<String, Map<String, Integer>> basicBowlingStatsMap = bowlingFigureService.getBasicBowlingStats(id);
        if(!basicBowlingStatsMap.keySet().isEmpty())
        {
            Map<String, BowlingStats> bowlingStatsFinal = new HashMap<>();

            for(String gameType: basicBowlingStatsMap.keySet())
            {
                BowlingStats bowlingStats = new BowlingStats(basicBowlingStatsMap.get(gameType));

                if(bowlingStats.getBalls() > 0)
                {
                    bowlingStats.setEconomy(bowlingStats.getRuns() * 6.0 / bowlingStats.getBalls());

                    if(bowlingStats.getWickets() > 0)
                    {
                        bowlingStats.setAverage(bowlingStats.getRuns() * 1.0 / bowlingStats.getWickets());

                        bowlingStats.setStrikeRate(bowlingStats.getBalls() * 1.0 / bowlingStats.getWickets());
                    }
                }

                bowlingStatsFinal.put(gameType, bowlingStats);
            }

            playerResponse.setBowlingStats(bowlingStatsFinal);
        }

        Map<String, Map<String, Integer>> fieldingStatsMap = fielderDismissalService.getFieldingStats(id);
        if(!fieldingStatsMap.keySet().isEmpty())
        {
            Map<String, FieldingStats> fieldingStatsMapFinal = new HashMap<>();
            for(String gameType: fieldingStatsMap.keySet())
            {
                FieldingStats fieldingStats = new FieldingStats(fieldingStatsMap.get(gameType));
                fieldingStatsMapFinal.put(gameType, fieldingStats);
            }

            playerResponse.setFieldingStats(fieldingStatsMapFinal);
        }

        return ok(Json.toJson(new Response(playerResponse)));
    }
}
