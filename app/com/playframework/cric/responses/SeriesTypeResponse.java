package com.playframework.cric.responses;

import lombok.NoArgsConstructor;
import lombok.Data;

import com.playframework.cric.models.SeriesType;

@Data
@NoArgsConstructor
public class SeriesTypeResponse {
    private Integer id;
    private String name;

    public SeriesTypeResponse(SeriesType seriesType) {
        this.id = seriesType.getId();
        this.name = seriesType.getName();
    }
}