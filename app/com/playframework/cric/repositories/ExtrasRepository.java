package com.playframework.cric.repositories;

import com.playframework.cric.models.Extras;
import com.playframework.cric.requests.matches.ExtrasRequest;
import io.ebean.DB;

import java.util.List;
import java.util.stream.Collectors;

public class ExtrasRepository {
    public List<Extras> add(Integer matchId, List<ExtrasRequest> extrasRequests)
    {
        List<Extras> extrasList = extrasRequests.stream().map(extrasRequest -> new Extras(matchId, extrasRequest)).collect(Collectors.toList());
        DB.saveAll(extrasList);
        return extrasList;
    }

    public List<Extras> getByMatchId(Integer matchId)
    {
        return DB.find(Extras.class).where().eq("matchId", matchId).orderBy("innings ASC, id ASC").findList();
    }

    public void remove(Integer matchId)
    {
        DB.deleteAll(getByMatchId(matchId));
    }
}
