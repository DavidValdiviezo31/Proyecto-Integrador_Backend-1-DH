package com.odontologia.project.services.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class OdontologoErrors {
  private static final Map<OdontologoErrorTypes, String> ErrorMessages = new HashMap<>();

  private static final Logger logger = LoggerFactory.getLogger(OdontologoErrors.class);

  static {
    ErrorMessages.put(OdontologoErrorTypes.ID_NULL, "La ID del Odontólogo no puede ser nula.");
    ErrorMessages.put(OdontologoErrorTypes.MATRICULA_NULL, "La MATRÍCULA del Odontólogo no puede ser nula.");
    ErrorMessages.put(OdontologoErrorTypes.MATRICULA_EXIST, "La MATRÍCULA ingresada ya existe en el sistema.");
    ErrorMessages.put(OdontologoErrorTypes.NOMBRE_NULL, "El NOMBRE del Odontólogo no puede ser nulo.");
    ErrorMessages.put(OdontologoErrorTypes.APELLIDO_NULL, "El APELLIDO del Odontólogo no puede ser nulo.");
    ErrorMessages.put(OdontologoErrorTypes.NOT_FOUND, "El Odontólogo buscado no existe: ");
  }

  public static String getErrorMessage(OdontologoErrorTypes type) {
    String errorMessage = ErrorMessages.get(type);
    logger.error(errorMessage);
    return errorMessage;
  }
}
