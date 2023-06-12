package com.playframework.cric.repositories;

import io.ebean.DB;
import java.time.LocalDate;

import com.playframework.cric.models.Player;
import com.playframework.cric.requests.players.CreateRequest;
import com.playframework.cric.utils.Utils;

public class PlayerRepository {
    public Player create(CreateRequest createRequest) {
        Player player = Utils.convertObject(createRequest, Player.class);
        DB.save(player);
        return player;
    }

    public Player getByNameAndCountryIdAndDateOfBirth(String name, Long countryId, LocalDate dateOfBirth) {
        return DB.find(Player.class).where().eq("name", name).eq("countryId", countryId).eq("dateOfBirth", dateOfBirth).findOne();
    }
}
