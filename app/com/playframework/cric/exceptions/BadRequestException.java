package com.playframework.cric.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends MyException
{
    private Integer httpStatusCode = 400;

    public BadRequestException()
    {
        this("Bad request");
    }

    public BadRequestException(String message)
    {
        super(message);
    }
}
