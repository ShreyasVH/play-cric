package com.playframework.cric.services;

import com.google.inject.Inject;
import com.playframework.cric.models.DismissalMode;
import com.playframework.cric.repositories.DismissalModeRepository;

import java.util.List;

public class DismissalModeService {
    private final DismissalModeRepository dismissalModeRepository;

    @Inject
    public DismissalModeService(DismissalModeRepository dismissalModeRepository)
    {
        this.dismissalModeRepository = dismissalModeRepository;
    }

    public List<DismissalMode> getAll()
    {
        return dismissalModeRepository.getAll();
    }
}
