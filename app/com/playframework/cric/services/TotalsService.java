package com.playframework.cric.services;

import com.playframework.cric.models.Total;
import com.playframework.cric.repositories.TotalsRepository;

import javax.inject.Inject;
import java.util.List;

public class TotalsService {
    private final TotalsRepository totalsRepository;

    @Inject
    public TotalsService(TotalsRepository totalsRepository)
    {
        this.totalsRepository = totalsRepository;
    }

    public void add(List<Total> totals)
    {
        this.totalsRepository.add(totals);
    }
}
