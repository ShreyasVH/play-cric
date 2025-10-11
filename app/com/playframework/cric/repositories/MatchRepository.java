package com.playframework.cric.repositories;

import com.playframework.cric.models.Match;
import com.playframework.cric.requests.matches.CreateRequest;
import com.playframework.cric.utils.Utils;
import io.ebean.DB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MatchRepository {
    public Match create(CreateRequest createRequest)
    {
        Match match = Utils.convertObject(createRequest, Match.class);
        DB.save(match);
        return match;
    }

    public Match getByStadiumAndStartTime(Long stadiumId, LocalDateTime startTime)
    {
        return DB.find(Match.class).where().eq("stadiumId", stadiumId).eq("startTime", startTime).findOne();
    }

    public List<Match> getBySeriesId(Integer seriesId)
    {
        return DB.find(Match.class).where().eq("seriesId", seriesId).orderBy("startTime ASC").findList();
    }

    public Match getById(Integer id)
    {
        return DB.find(Match.class).where().eq("id", id).findOne();
    }

    public void remove(Integer matchId)
    {
        DB.delete(getById(matchId));
    }
}
