package com.odontologia.project.services.impl;

import com.odontologia.project.exceptions.BadRequestException;
import com.odontologia.project.exceptions.EntityNotFoundException;
import com.odontologia.project.exceptions.InvalidInputException;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.models.Turno;
import com.odontologia.project.repositories.ITurnoRepository;
import com.odontologia.project.services.IOdontologoService;
import com.odontologia.project.services.IPacienteService;
import com.odontologia.project.services.ITurnoService;
import com.odontologia.project.services.errors.TurnoErrorTypes;
import com.odontologia.project.services.errors.TurnoErrors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TurnoService implements ITurnoService {
  private final ITurnoRepository iTurnoRepository;
  private final IPacienteService iPacienteService;
  private final IOdontologoService iOdontologoService;

  private final Logger logger = LoggerFactory.getLogger(TurnoService.class);

  @Override
  public Turno guardarTurno(Turno turno) {
    validarTurno(turno);

    Odontologo odontologo = iOdontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
    Paciente paciente = iPacienteService.buscarPacientePorId(turno.getPaciente().getId());

    turno.setOdontologo(odontologo);
    turno.setPaciente(paciente);

    logger.info("Turno para paciente {} con odontólogo {} guardado exitosamente.",
        paciente.getId(),
        odontologo.getId());
    return iTurnoRepository.save(turno);
  }

  @Override
  public Turno buscarTurnoPorId(Long id) {
    if (id == null)
      throw new InvalidInputException(TurnoErrors.getErrorMessage(TurnoErrorTypes.ID_NULL));

    return iTurnoRepository.findById(id)
        .orElseThrow(() ->
            new EntityNotFoundException(TurnoErrors.getErrorMessage(TurnoErrorTypes.NOT_FOUND) + id));
  }

  @Override
  public List<Turno> buscarTodosTurnos() {
    return iTurnoRepository.findAll();
  }

  @Override
  public Turno actualizarTurno(Turno turno) {
    validarTurno(turno);

    Turno turnoActualizado = buscarTurnoPorId(turno.getId());
    turnoActualizado.setHora(turno.getHora());
    turnoActualizado.setFecha(turno.getFecha());
    turnoActualizado.setOdontologo(turno.getOdontologo());
    turnoActualizado.setPaciente(turno.getPaciente());

    logger.info("Turno con id {} actualizado exitosamente.",
        turnoActualizado.getId());
    return iTurnoRepository.save(turnoActualizado);
  }

  @Override
  public Turno eliminarTurnoPorId(Long id) {
    Turno turnoEliminado = buscarTurnoPorId(id);

    iTurnoRepository.deleteById(id);

    logger.info("Turno con id {} eliminado exitosamente.", id);
    return turnoEliminado;
  }

  private void validarTurno(Turno turno) {
    if (turno.getFecha() == null)
      throw new InvalidInputException(TurnoErrors.getErrorMessage(TurnoErrorTypes.FECHA_NULL));

    if (turno.getHora() == null)
      throw new InvalidInputException(TurnoErrors.getErrorMessage(TurnoErrorTypes.HORA_NULL));

    if (Objects.isNull(turno.getOdontologo()))
      throw new BadRequestException(TurnoErrors.getErrorMessage(TurnoErrorTypes.ODONTOLOGO_NULL));

    if (Objects.isNull(turno.getPaciente()))
      throw new BadRequestException(TurnoErrors.getErrorMessage(TurnoErrorTypes.PACIENTE_NULL));
  }
}
