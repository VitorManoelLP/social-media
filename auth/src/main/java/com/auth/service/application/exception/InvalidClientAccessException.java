package com.auth.service.application.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class InvalidClientAccessException extends HttpClientErrorException {
    public InvalidClientAccessException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
