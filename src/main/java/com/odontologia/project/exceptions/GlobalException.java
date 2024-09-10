package com.odontologia.project.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {
  private final Logger logger = LoggerFactory.getLogger(GlobalException.class);

  @ExceptionHandler(BadRequestException.class)
  @ResponseBody
  public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
    logger.error(ex.getMessage());
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidInputException.class)
  @ResponseBody
  public ResponseEntity<String> handleInvalidInputException(InvalidInputException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseBody
  public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
    logger.error(e.getMessage());
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<String> handleGeneralException(Exception e) {
    logger.error("Error Interno del Servidor: {}", e.getMessage());
    return new ResponseEntity<>("Error Interno del Servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

