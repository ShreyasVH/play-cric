package com.playframework.cric.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConflictException extends MyException
{
    private Integer httpStatusCode = 409;

    public ConflictException(String entity)
    {
        super(entity + " already exists");
    }
}
