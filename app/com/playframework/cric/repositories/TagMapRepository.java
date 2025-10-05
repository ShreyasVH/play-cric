package com.playframework.cric.repositories;

import com.playframework.cric.models.SeriesTeamsMap;
import io.ebean.DB;

import com.playframework.cric.models.ResultType;

import java.util.List;
import java.util.stream.Collectors;

public class TagMapRepository {
    public void create(Integer entityId, List<Long> teamIds) {
        List<SeriesTeamsMap> seriesTeamsMaps = teamIds.stream().map(teamId -> new SeriesTeamsMap(null, seriesId, teamId)).collect(Collectors.toList());
        DB.saveAll(seriesTeamsMaps);
    }
}