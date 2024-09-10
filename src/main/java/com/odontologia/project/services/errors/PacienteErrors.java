package com.odontologia.project.services.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class PacienteErrors {
  private static final HashMap<PacienteErrorTypes, String> ErrorMessages = new HashMap<>();

  private static final Logger logger = LoggerFactory.getLogger(PacienteErrors.class);

  static {
    ErrorMessages.put(PacienteErrorTypes.ID_NULL, "La ID del Paciente no puede ser nula.");
    ErrorMessages.put(PacienteErrorTypes.DNI_NULL, "El DNI del Paciente no puede ser nulo.");
    ErrorMessages.put(PacienteErrorTypes.DNI_EXIST, "El DNI ingresado ya existe en el sistema.");
    ErrorMessages.put(PacienteErrorTypes.NOMBRE_NULL, "El NOMBRE del Paciente no puede ser nulo.");
    ErrorMessages.put(PacienteErrorTypes.APELLIDO_NULL, "El APELLIDO del Paciente no puede ser nulo.");
    ErrorMessages.put(PacienteErrorTypes.FECHA_ALTA_NULL, "La FECHA ALTA del Paciente no puede ser nula. ");
    ErrorMessages.put(PacienteErrorTypes.NOT_FOUND, "El Paciente buscado no existe. ");
  }

  public static String getErrorMessage(PacienteErrorTypes type) {
    String errorMessage = ErrorMessages.get(type);
    logger.error(errorMessage);
    return errorMessage;
  }
}
