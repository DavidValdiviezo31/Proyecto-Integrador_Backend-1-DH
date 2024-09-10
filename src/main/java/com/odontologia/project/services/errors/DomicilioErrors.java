package com.odontologia.project.services.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DomicilioErrors {
  private static final Map<DomicilioErrorTypes, String> ErrorMessages = new HashMap<>();

  private static final Logger logger = LoggerFactory.getLogger(DomicilioErrors.class);

  static {
    ErrorMessages.put(DomicilioErrorTypes.ID_NULL, "La ID del Domicilio no puede ser nula.");
    ErrorMessages.put(DomicilioErrorTypes.CALLE_NULL, "La CALLE del Domicilio no puede ser nula.");
    ErrorMessages.put(DomicilioErrorTypes.NUMERO_NULL, "El NÚMERO del Domicilio no puede ser nulo.");
    ErrorMessages.put(DomicilioErrorTypes.LOCALIDAD_NULL, "La LOCALIDAD del Domicilio no puede ser nula.");
    ErrorMessages.put(DomicilioErrorTypes.PROVINCIA_NULL, "La PROVINCIA del Domicilio no puede ser nula.");
    ErrorMessages.put(DomicilioErrorTypes.NOT_FOUND, "El Domicilio buscado no existe: ");
  }

  public static String getErrorMessage(DomicilioErrorTypes type) {
    String errorMessage = ErrorMessages.get(type);
    logger.error(errorMessage);
    return errorMessage;
  }
}
