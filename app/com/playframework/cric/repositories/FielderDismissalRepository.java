package com.playframework.cric.repositories;

import com.playframework.cric.models.FielderDismissal;
import io.ebean.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FielderDismissalRepository {
    public List<FielderDismissal> add(Map<Integer, List<Long>> scorePlayerMap, Map<Long, Integer> matchPlayerMap)
    {
        List<FielderDismissal> fielderDismissals = new ArrayList<>();
        for(Map.Entry<Integer, List<Long>> scorePlayerEntry: scorePlayerMap.entrySet())
        {
            fielderDismissals.addAll(scorePlayerEntry.getValue().stream().map(playerId -> new FielderDismissal(null, scorePlayerEntry.getKey(), matchPlayerMap.get(playerId))).collect(Collectors.toList()));
        }
        DB.saveAll(fielderDismissals);
        return fielderDismissals;
    }
}
