package com.playframework.cric.repositories;

import io.ebean.DB;
import java.time.LocalDate;
import java.util.List;

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

    public List<Player> getAll(int page, int limit) {
        return DB.find(Player.class)
                .orderBy("name asc")
                .setFirstRow((page - 1) * limit)
                .setMaxRows(limit)
                .findList();
    }

    public int getTotalCount() {
        return DB.find(Player.class).findCount();
    }

    public List<Player> getByIds(List<Long> ids) {
        return DB.find(Player.class).where().in("id", ids).findList();
    }

    public Player getById(Long id)
    {
        return DB.find(Player.class).where().eq("id", id).findOne();
    }
}
