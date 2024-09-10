package com.odontologia.project.services.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TurnoErrors {
  private static final Map<TurnoErrorTypes, String> ErrorMessages = new HashMap<>();

  private static final Logger logger = LoggerFactory.getLogger(TurnoErrors.class);

  static {
    ErrorMessages.put(TurnoErrorTypes.ID_NULL, "La ID del Turno no puede ser null.");
    ErrorMessages.put(TurnoErrorTypes.FECHA_NULL, "La FECHA del Turno no puede ser null.");
    ErrorMessages.put(TurnoErrorTypes.HORA_NULL, "La HORA del Turno no puede ser null.");
    ErrorMessages.put(TurnoErrorTypes.ODONTOLOGO_NULL, "El ODONTÓLOGO del Turno no puede ser null.");
    ErrorMessages.put(TurnoErrorTypes.PACIENTE_NULL, "El PACIENTE del Turno no puede ser null.");
    ErrorMessages.put(TurnoErrorTypes.NOT_FOUND, "El Turno buscado no existe. ");
  }

  public static String getErrorMessage(TurnoErrorTypes type) {
    String errorMessage = ErrorMessages.get(type);
    logger.error(errorMessage);
    return errorMessage;
  }
}
