package com.playframework.cric.responses;

import com.playframework.cric.models.ExtrasType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtrasTypeResponse {
    private Integer id;
    private String name;

    public ExtrasTypeResponse(ExtrasType extrasType)
    {
        this.id = extrasType.getId();
        this.name = extrasType.getName();
    }
}
