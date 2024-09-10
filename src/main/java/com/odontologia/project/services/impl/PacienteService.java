package com.odontologia.project.services.impl;

import com.odontologia.project.exceptions.EntityNotFoundException;
import com.odontologia.project.exceptions.InvalidInputException;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.repositories.IPacienteRepository;
import com.odontologia.project.services.IPacienteService;
import com.odontologia.project.services.errors.PacienteErrorTypes;
import com.odontologia.project.services.errors.PacienteErrors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService implements IPacienteService {
  private final IPacienteRepository iPacienteRepository;

  private final Logger logger = LoggerFactory.getLogger(PacienteService.class);

  @Override
  public Paciente guardarPaciente(Paciente paciente) {
    validarPaciente(paciente);

    logger.info("Paciente con DNI {} guardado exitosamente", paciente.getDni());
    return iPacienteRepository.save(paciente);
  }

  @Override
  public Paciente buscarPacientePorId(Long id) {
    if (id == null)
      throw new InvalidInputException(PacienteErrors.getErrorMessage(PacienteErrorTypes.ID_NULL));

    return iPacienteRepository.findById(id)
        .orElseThrow(() ->
            new EntityNotFoundException(PacienteErrors.getErrorMessage(PacienteErrorTypes.NOT_FOUND) + id));
  }

  @Override
  public List<Paciente> buscarTodosPacientes() {
    return iPacienteRepository.findAll();
  }

  @Override
  public Paciente actualizarPaciente(Paciente paciente) {
    validarPaciente(paciente);

    Paciente pacienteActualizado = buscarPacientePorId(paciente.getId());
    pacienteActualizado.setNombre(paciente.getNombre());
    pacienteActualizado.setApellido(paciente.getApellido());
    pacienteActualizado.setDni(paciente.getDni());
    pacienteActualizado.setDomicilio(paciente.getDomicilio());
    pacienteActualizado.setFechaAlta(paciente.getFechaAlta());

    logger.info("Paciente {} {} con id {} actualizado exitosamente.",
        pacienteActualizado.getNombre(),
        pacienteActualizado.getDni(),
        pacienteActualizado.getId());
    return iPacienteRepository.save(pacienteActualizado);
  }

  @Override
  public Paciente eliminarPacientePorId(Long id) {
    Paciente pacienteEliminado = buscarPacientePorId(id);

    iPacienteRepository.deleteById(id);

    logger.info("Paciente con id {} eliminado exitosamente.", id);
    return pacienteEliminado;
  }

  private void validarPaciente(Paciente paciente) {
    if (paciente.getDni() == null)
      throw new InvalidInputException(PacienteErrors.getErrorMessage(PacienteErrorTypes.DNI_NULL));

    if (iPacienteRepository.existsByDni(paciente.getDni()))
      throw new RuntimeException(PacienteErrors.getErrorMessage(PacienteErrorTypes.DNI_EXIST) + paciente.getDni());

    if (paciente.getNombre() == null)
      throw new InvalidInputException(PacienteErrors.getErrorMessage(PacienteErrorTypes.NOMBRE_NULL));

    if (paciente.getApellido() == null)
      throw new InvalidInputException(PacienteErrors.getErrorMessage(PacienteErrorTypes.APELLIDO_NULL));

    if (paciente.getFechaAlta() == null)
      throw new InvalidInputException(PacienteErrors.getErrorMessage(PacienteErrorTypes.FECHA_ALTA_NULL));
  }
}
