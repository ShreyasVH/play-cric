package com.playframework.cric.repositories;

import com.playframework.cric.models.Series;
import com.playframework.cric.models.SeriesTeamsMap;
import com.playframework.cric.requests.series.CreateRequest;
import com.playframework.cric.utils.Utils;
import io.ebean.DB;

import java.util.List;
import java.util.stream.Collectors;

public class SeriesTeamsMapRepository {
    public void create(Long seriesId, List<Long> teamIds) {
        List<SeriesTeamsMap> seriesTeamsMaps = teamIds.stream().map(teamId -> new SeriesTeamsMap(null, seriesId, teamId)).collect(Collectors.toList());
        DB.saveAll(seriesTeamsMaps);
    }

    public List<SeriesTeamsMap> getBySeriesIds(List<Long> seriesIds) {
        return DB.find(SeriesTeamsMap.class).where().in("seriesId", seriesIds).findList();
    }

    public void delete(Long seriesId, List<Long> teamIds) {
        List<SeriesTeamsMap> seriesTeamsMaps = DB.find(SeriesTeamsMap.class).where().eq("seriesId", seriesId).in("teamId", teamIds).findList();
        DB.deleteAll(seriesTeamsMaps);
    }
}
