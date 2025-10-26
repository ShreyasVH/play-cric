package com.playframework.cric.repositories;

import com.playframework.cric.models.Total;
import io.ebean.DB;

import java.util.List;

public class TotalsRepository {
    public void add(List<Total> totals)
    {
        DB.saveAll(totals);
    }

    public List<Total> getByMatchId(Integer matchId)
    {
        return DB.find(Total.class).where().eq("matchId", matchId).findList();
    }

    public void remove(Integer matchId)
    {
        DB.deleteAll(getByMatchId(matchId));
    }
}
