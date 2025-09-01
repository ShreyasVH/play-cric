package com.playframework.cric.repositories;

import com.playframework.cric.models.MatchPlayerMap;
import com.playframework.cric.models.Total;
import io.ebean.DB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TotalsRepository {
    public void add(List<Total> totals)
    {
        DB.saveAll(totals);
    }
}
