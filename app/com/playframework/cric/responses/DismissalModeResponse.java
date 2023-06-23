package com.playframework.cric.responses;

import com.playframework.cric.models.DismissalMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DismissalModeResponse
{
    private Integer id;
    private String name;

    public DismissalModeResponse(DismissalMode dismissalMode)
    {
        this.id = dismissalMode.getId();
        this.name = dismissalMode.getName();
    }
}
