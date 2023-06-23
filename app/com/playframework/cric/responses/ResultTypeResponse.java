package com.playframework.cric.responses;

import com.playframework.cric.models.ResultType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultTypeResponse {
    private Integer id;
    private String name;

    public ResultTypeResponse(ResultType resultType)
    {
        this.id = resultType.getId();
        this.name = resultType.getName();
    }
}
