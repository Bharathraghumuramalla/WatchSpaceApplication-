package com.bharath.watchSpace.ExceptionsAndErrors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }
    @ExceptionHandler(ActiveWatchSpaceException.class)
    public ResponseEntity<Map<String, String>>
    handleActiveWatchSpaceException(
            ActiveWatchSpaceException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "CONFLICT",
                        "message", ex.getMessage()
                ));
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleIBadRequestException(BadRequestException e, HttpServletRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e,HttpServletRequest request)
//    {
//        ErrorResponse errorResponse = new ErrorResponse();
//
//        errorResponse.setMessage("Authentication failed");
//        errorResponse.setTimestamp(LocalDateTime.now());
//        errorResponse.setPath(request.getRequestURI());
//        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
//    }
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<?> handleAccessDeniedException(
//            AccessDeniedException e,
//            HttpServletRequest request) {
//
//        ErrorResponse errorResponse = new ErrorResponse();
//
//        errorResponse.setMessage("Access denied");
//        errorResponse.setTimestamp(LocalDateTime.now());
//        errorResponse.setPath(request.getRequestURI());
//        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
//
//        return ResponseEntity
//                .status(HttpStatus.FORBIDDEN)
//                .body(errorResponse);
//    }
}
