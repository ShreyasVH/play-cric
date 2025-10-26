package com.playframework.cric.repositories;

import com.playframework.cric.models.ManOfTheSeries;
import com.playframework.cric.requests.players.MergeRequest;
import io.ebean.DB;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ManOfTheSeriesRepository {
    public List<ManOfTheSeries> getBySeriesIds(List<Integer> seriesIds) {
        return DB.find(ManOfTheSeries.class).where().in("seriesId", seriesIds).findList();
    }

    public void add(Integer seriesId, List<Long> playerIds) {
        List<ManOfTheSeries> manOfTheSeriesList = playerIds.stream().map(playerId -> new ManOfTheSeries(null, seriesId, playerId)).collect(Collectors.toList());
        DB.saveAll(manOfTheSeriesList);
    }

    public void delete(Integer seriesId, List<Long> playerIds) {
        List<ManOfTheSeries> manOfTheSeriesList = DB.find(ManOfTheSeries.class).where().eq("seriesId", seriesId).in("playerId", playerIds).findList();
        DB.deleteAll(manOfTheSeriesList);
    }

    public void remove(Integer seriesId)
    {
        DB.deleteAll(getBySeriesIds(Collections.singletonList(seriesId)));
    }

    public void merge(MergeRequest mergeRequest)
    {
        List<ManOfTheSeries> manOfTheSeriesList = DB.find(ManOfTheSeries.class).where().eq("playerId", mergeRequest.getPlayerIdToMerge()).findList();
        manOfTheSeriesList.forEach(manOfTheSeries -> manOfTheSeries.setPlayerId(mergeRequest.getOriginalPlayerId()));
        DB.saveAll(manOfTheSeriesList);
    }
}
