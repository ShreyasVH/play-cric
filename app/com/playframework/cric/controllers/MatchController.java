package com.playframework.cric.controllers;

import com.google.inject.Inject;
import com.playframework.cric.exceptions.NotFoundException;
import com.playframework.cric.models.*;
import com.playframework.cric.requests.matches.CreateRequest;
import com.playframework.cric.requests.matches.PlayerRequest;
import com.playframework.cric.responses.*;
import com.playframework.cric.services.*;
import com.playframework.cric.utils.Utils;
import io.ebean.DB;
import io.ebean.Transaction;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.*;
import java.util.stream.Collectors;

public class MatchController extends Controller {
    private final MatchService matchService;
    private final SeriesService seriesService;
    private final CountryService countryService;
    private final TeamService teamService;
    private final TeamTypeService teamTypeService;
    private final ResultTypeService resultTypeService;
    private final WinMarginTypeService winMarginTypeService;
    private final StadiumService stadiumService;
    private final PlayerService playerService;
    private final MatchPlayerMapService matchPlayerMapService;
    private final BattingScoreService battingScoreService;
    private final DismissalModeService dismissalModeService;
    private final FielderDismissalService fielderDismissalService;
    private final BowlingFigureService bowlingFigureService;
    private final ExtrasService extrasService;
    private final ExtrasTypeService extrasTypeService;
    private final ManOfTheMatchService manOfTheMatchService;
    private final CaptainService captainService;
    private final WicketKeeperService wicketKeeperService;
    private final GameTypeService gameTypeService;

    @Inject
    public MatchController(MatchService matchService, SeriesService seriesService, CountryService countryService, TeamService teamService, TeamTypeService teamTypeService, ResultTypeService resultTypeService, WinMarginTypeService winMarginTypeService, StadiumService stadiumService, PlayerService playerService, MatchPlayerMapService matchPlayerMapService, BattingScoreService battingScoreService, DismissalModeService dismissalModeService, FielderDismissalService fielderDismissalService, BowlingFigureService bowlingFigureService, ExtrasService extrasService, ExtrasTypeService extrasTypeService, ManOfTheMatchService manOfTheMatchService, CaptainService captainService, WicketKeeperService wicketKeeperService, GameTypeService gameTypeService)
    {
        this.matchService = matchService;
        this.seriesService = seriesService;
        this.countryService = countryService;
        this.teamService = teamService;
        this.teamTypeService = teamTypeService;
        this.resultTypeService = resultTypeService;
        this.winMarginTypeService = winMarginTypeService;
        this.stadiumService = stadiumService;
        this.playerService = playerService;
        this.matchPlayerMapService = matchPlayerMapService;
        this.battingScoreService = battingScoreService;
        this.dismissalModeService = dismissalModeService;
        this.fielderDismissalService = fielderDismissalService;
        this.bowlingFigureService = bowlingFigureService;
        this.extrasService = extrasService;
        this.extrasTypeService = extrasTypeService;
        this.manOfTheMatchService = manOfTheMatchService;
        this.captainService = captainService;
        this.wicketKeeperService = wicketKeeperService;
        this.gameTypeService = gameTypeService;
    }

    public Result create(Http.Request request)
    {
        CreateRequest createRequest = Utils.convertObject(request.body().asJson(), CreateRequest.class);

        Series series = seriesService.getById(createRequest.getSeriesId());
        if(null == series)
        {
            throw new NotFoundException("Series");
        }

        GameType gameType = gameTypeService.getById(series.getTypeId());

        List<Long> countryIds = new ArrayList<>();

        List<Long> teamIds = new ArrayList<>();
        teamIds.add(createRequest.getTeam1Id());
        teamIds.add(createRequest.getTeam2Id());
        List<Team> teams = teamService.getByIds(teamIds);
        Map<Long, Team> teamMap = new HashMap<>();
        for(Team team: teams)
        {
            teamMap.put(team.getId(), team);
            countryIds.add(team.getCountryId());
        }

        Team team1 = teamMap.get(createRequest.getTeam1Id());
        if(null == team1)
        {
            throw new NotFoundException("Team 1");
        }

        Team team2 = teamMap.get(createRequest.getTeam2Id());
        if(null == team2)
        {
            throw new NotFoundException("Team 2");
        }

        ResultType resultType = resultTypeService.getById(createRequest.getResultTypeId());
        if(null == resultType)
        {
            throw new NotFoundException("Result type");
        }

        WinMarginTypeResponse winMarginTypeResponse = null;
        if(null != createRequest.getWinMarginTypeId())
        {
            WinMarginType winMarginType = winMarginTypeService.getById(createRequest.getWinMarginTypeId());
            if(null == winMarginType)
            {
                throw new NotFoundException("Win margin type");
            }
            winMarginTypeResponse = new WinMarginTypeResponse(winMarginType);
        }

        Stadium stadium = stadiumService.getById(createRequest.getStadiumId());
        if(null == stadium)
        {
            throw new NotFoundException("Stadium");
        }

        Map<Long, Long> playerTeamMap = new HashMap<>();
        List<Long> allPlayerIds = new ArrayList<>();
        for(PlayerRequest playerRequest: createRequest.getPlayers())
        {
            playerTeamMap.put(playerRequest.getId(), playerRequest.getTeamId());
            allPlayerIds.add(playerRequest.getId());
        }
        for(PlayerRequest playerRequest: createRequest.getBench())
        {
            playerTeamMap.put(playerRequest.getId(), playerRequest.getTeamId());
            allPlayerIds.add(playerRequest.getId());
        }

        List<Player> allPlayers = playerService.getByIds(allPlayerIds);
        List<Long> playerCountryIds = allPlayers.stream().map(Player::getCountryId).collect(Collectors.toList());
        Map<Long, Player> playerMap = allPlayers.stream().collect(Collectors.toMap(Player::getId, player -> player));

        countryIds.add(team1.getCountryId());
        countryIds.add(team2.getCountryId());
        countryIds.add(stadium.getCountryId());
        countryIds.addAll(playerCountryIds);
        List<Integer> teamTypeIds = Arrays.asList(team1.getTypeId(), team2.getTypeId());
        List<TeamType> teamTypes = teamTypeService.getByIds(teamTypeIds);
        Map<Integer, TeamType> teamTypeMap = teamTypes.stream().collect(Collectors.toMap(TeamType::getId, teamType -> teamType));

        List<Country> countries = countryService.getByIds(countryIds);
        Map<Long, Country> countryMap = countries.stream().collect(Collectors.toMap(Country::getId, country -> country));

        Match match;
        Transaction transaction = DB.beginTransaction();
        List<BattingScoreResponse> battingScoreResponses = new ArrayList<>();
        List<BowlingFigureResponse> bowlingFigureResponses = new ArrayList<>();
        List<ExtrasResponse> extrasResponses = new ArrayList<>();
        try
        {
            match = matchService.create(createRequest);
            List<MatchPlayerMap> matchPlayerMapList = matchPlayerMapService.add(match.getId(), allPlayerIds, playerTeamMap);
            Map<Long, Integer> playerToMatchPlayerMap = matchPlayerMapList.stream().collect(Collectors.toMap(MatchPlayerMap::getPlayerId, MatchPlayerMap::getId));
            List<BattingScore> battingScores = battingScoreService.add(createRequest.getBattingScores(), playerToMatchPlayerMap);
            List<DismissalMode> dismissalModes = dismissalModeService.getAll();
            Map<Integer, DismissalMode> dismissalModeMap = dismissalModes.stream().collect(Collectors.toMap(DismissalMode::getId, dismissalMode -> dismissalMode));
            Map<String, BattingScore> battingScoreMap = battingScores.stream().collect(Collectors.toMap(battingScore -> battingScore.getMatchPlayerId() + "_" + battingScore.getInnings(), battingScore -> battingScore));
            Map<Integer, List<Long>> scoreFielderMap = new HashMap<>();
            battingScoreResponses = createRequest.getBattingScores().stream().map(battingScore -> {
                String key = playerToMatchPlayerMap.get(battingScore.getPlayerId()) + "_" + battingScore.getInnings();
                BattingScore battingScoreFromDb = battingScoreMap.get(key);

                DismissalModeResponse dismissalModeResponse = null;
                List<PlayerMiniResponse> fielders = new ArrayList<>();
                PlayerMiniResponse bowler = null;

                if(null != battingScore.getDismissalModeId())
                {
                    dismissalModeResponse = new DismissalModeResponse(dismissalModeMap.get(battingScore.getDismissalModeId()));
                    if(null != battingScore.getBowlerId())
                    {
                        Player bowlerPlayer = playerMap.get(battingScore.getBowlerId());
                        bowler = new PlayerMiniResponse(bowlerPlayer, new CountryResponse(countryMap.get(bowlerPlayer.getCountryId())));
                    }

                    if(null != battingScore.getFielderIds())
                    {
                        fielders = battingScore.getFielderIds().stream().map(playerId -> {
                            Player fielderPlayer = playerMap.get(playerId);
                            return new PlayerMiniResponse(fielderPlayer, new CountryResponse(countryMap.get(fielderPlayer.getCountryId())));
                        }).collect(Collectors.toList());
                        scoreFielderMap.put(battingScoreFromDb.getId(), battingScore.getFielderIds());
                    }
                }
                Player batsmanPlayer = playerMap.get(battingScore.getPlayerId());

                return new BattingScoreResponse(
                    battingScoreFromDb,
                    new PlayerMiniResponse(batsmanPlayer, new CountryResponse(countryMap.get(batsmanPlayer.getCountryId()))),
                    dismissalModeResponse,
                    bowler,
                    fielders
                );
            }).collect(Collectors.toList());

            fielderDismissalService.add(scoreFielderMap, playerToMatchPlayerMap);
            List<BowlingFigure> bowlingFigures = bowlingFigureService.add(createRequest.getBowlingFigures(), playerToMatchPlayerMap);
            Map<String, BowlingFigure> bowlingFigureMap = bowlingFigures.stream().collect(Collectors.toMap(bowlingFigure -> bowlingFigure.getMatchPlayerId() + "_" + bowlingFigure.getInnings(), bowlingFigure -> bowlingFigure));
            bowlingFigureResponses = createRequest.getBowlingFigures().stream().map(bowlingFigureRequest -> {
                String key = playerToMatchPlayerMap.get(bowlingFigureRequest.getPlayerId()) + "_" + bowlingFigureRequest.getInnings();
                BowlingFigure bowlingFigure = bowlingFigureMap.get(key);

                Player bowlerPlayer = playerMap.get(bowlingFigureRequest.getPlayerId());

                return new BowlingFigureResponse(bowlingFigure, new PlayerMiniResponse(bowlerPlayer, new CountryResponse(countryMap.get(bowlerPlayer.getCountryId()))));
            }).collect(Collectors.toList());

            List<ExtrasType> extrasTypes = extrasTypeService.getAll();
            Map<Integer, ExtrasType> extrasTypeMap = extrasTypes.stream().collect(Collectors.toMap(ExtrasType::getId, extrasType -> extrasType));
            List<Extras> extrasList = extrasService.add(match.getId(), createRequest.getExtras());
            extrasResponses = extrasList.stream()
                .map(extras -> {
                    Team battingTeam = teamMap.get(extras.getBattingTeamId());
                    Team bowlingTeam = teamMap.get(extras.getBowlingTeamId());
                    return new ExtrasResponse(
                        extras,
                        new ExtrasTypeResponse(extrasTypeMap.get(extras.getTypeId())),
                        new TeamResponse(
                            battingTeam,
                            new CountryResponse(countryMap.get(battingTeam.getCountryId())),
                            new TeamTypeResponse(teamTypeMap.get(battingTeam.getTypeId()))
                        ),
                        new TeamResponse(
                            bowlingTeam,
                            new CountryResponse(countryMap.get(bowlingTeam.getCountryId())),
                            new TeamTypeResponse(teamTypeMap.get(bowlingTeam.getTypeId()))
                        )
                    );
                })
                .collect(Collectors.toList());

            manOfTheMatchService.add(createRequest.getManOfTheMatchList(), playerToMatchPlayerMap);
            captainService.add(createRequest.getCaptains(), playerToMatchPlayerMap);
            wicketKeeperService.add(createRequest.getWicketKeepers(), playerToMatchPlayerMap);

            transaction.commit();
            transaction.end();
        }
        catch(Exception ex)
        {
            transaction.end();
            throw ex;
        }

        List<PlayerMiniResponse> playerResponses = allPlayers.stream().map(player -> new PlayerMiniResponse(player, new CountryResponse(countryMap.get(player.getCountryId())))).collect(Collectors.toList());

        MatchResponse matchResponse = new MatchResponse(
            match,
            series,
            gameType,
            new TeamResponse(team1, new CountryResponse(countryMap.get(team1.getCountryId())), new TeamTypeResponse(teamTypeMap.get(team1.getTypeId()))),
            new TeamResponse(team2, new CountryResponse(countryMap.get(team2.getCountryId())), new TeamTypeResponse(teamTypeMap.get(team2.getTypeId()))),
            new ResultTypeResponse(resultType),
            winMarginTypeResponse,
            new StadiumResponse(stadium, new CountryResponse(countryMap.get(stadium.getCountryId()))),
            playerResponses,
            battingScoreResponses,
            bowlingFigureResponses,
            extrasResponses,
            createRequest.getManOfTheMatchList(),
            createRequest.getCaptains(),
            createRequest.getWicketKeepers()
        );

        return created(Json.toJson(new Response(matchResponse)));
    }
}
