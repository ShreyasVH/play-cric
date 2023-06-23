package com.playframework.cric.repositories;

import com.playframework.cric.models.BowlingFigure;
import com.playframework.cric.requests.matches.BowlingFigureRequest;
import io.ebean.DB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BowlingFigureRepository {
    public List<BowlingFigure> add(List<BowlingFigureRequest> bowlingFigureRequests, Map<Long, Integer> matchPlayerMap)
    {
        List<BowlingFigure> bowlingFigures = bowlingFigureRequests.stream().map(bowlingFigureRequest -> new BowlingFigure(bowlingFigureRequest, matchPlayerMap)).collect(Collectors.toList());
        DB.saveAll(bowlingFigures);
        return bowlingFigures;
    }
}
