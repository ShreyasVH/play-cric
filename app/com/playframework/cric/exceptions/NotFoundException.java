package com.playframework.cric.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotFoundException extends MyException
{
    private Integer httpStatusCode = 404;

    public NotFoundException(String entity)
    {
        super(entity + " not found");
    }
}
