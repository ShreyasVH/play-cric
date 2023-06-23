package com.playframework.cric.repositories;

import com.playframework.cric.models.DismissalMode;
import io.ebean.DB;

import java.util.List;

public class DismissalModeRepository {
    public List<DismissalMode> getAll()
    {
        return DB.find(DismissalMode.class).findList();
    }
}
