package com.reyhan.hotel.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Map<String, Object>> handleConflict(IllegalStateException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("error", "Validation failed");
		body.put("details", ex.getBindingResult().getFieldErrors()
			.stream().map(f -> f.getField() + ": " + f.getDefaultMessage()).toArray());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
}


