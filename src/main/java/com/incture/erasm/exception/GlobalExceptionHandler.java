package com.incture.erasm.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<Object> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        logger.error("User not found: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "User Not Found",
                ex.getMessage());
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<Object> handleProjectNotFound(ProjectNotFoundException ex) {
        logger.error("Project not found: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Project Not Found",
                ex.getMessage());
    }

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<Object> handleSkillNotFound(SkillNotFoundException ex) {
        logger.error("Skill not found: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Skill Not Found",
                ex.getMessage());
    }

    @ExceptionHandler(AllocationException.class)
    public ResponseEntity<Object> handleAllocationException(AllocationException ex) {
        logger.error("Allocation error: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Allocation Error",
                ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDatabaseException(DataAccessException ex) {
        logger.error("Database failure occurred", ex);
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Database Error",
                "Database operation failed");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        logger.error("Runtime exception occurred", ex);
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        logger.error("Unexpected system exception", ex);
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                ex.getMessage());
    }
}