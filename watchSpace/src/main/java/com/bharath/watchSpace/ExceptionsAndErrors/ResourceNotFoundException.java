package com.bharath.watchSpace.ExceptionsAndErrors;

public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
