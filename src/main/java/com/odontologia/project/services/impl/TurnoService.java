package com.odontologia.project.services.impl;

import com.odontologia.project.exceptions.BadRequestException;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.models.Turno;
import com.odontologia.project.repositories.IOdontologoRepository;
import com.odontologia.project.repositories.IPacienteRepository;
import com.odontologia.project.repositories.ITurnoRepository;
import com.odontologia.project.services.ITurnoService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TurnoService implements ITurnoService {
  private final ITurnoRepository iTurnoRepository;
  private final IPacienteRepository iPacienteRepository;
  private final IOdontologoRepository iOdontologoRepository;

  @Override
  public Turno guardarTurno(Turno turno) {

    List<String> errores = new ArrayList<>();

    if (!iOdontologoRepository.findById(turno.getOdontologo().getId()).isPresent()) {
      errores.add("No existe el odontólogo.");
    }
    if (!iPacienteRepository.findById(turno.getPaciente().getId()).isPresent()) {
      errores.add("No existe el paciente.");
    }
    if (!errores.isEmpty()) {
      throw new BadRequestException(String.join(" ", errores));
    }
    Odontologo odontologo = iOdontologoRepository.findById(turno.getOdontologo().getId()).get();
    Paciente paciente = iPacienteRepository.findById(turno.getPaciente().getId()).get();

    turno.setOdontologo(odontologo);
    turno.setPaciente(paciente);
    return iTurnoRepository.save(turno);
  }

  @Override
  public Turno buscarTurnoPorId(Long id) {
    return iTurnoRepository.findById(id).orElse(null);
  }

  @Override
  public List<Turno> buscarTodosTurnos() {
    return iTurnoRepository.findAll();
  }

  @Override
  public Turno actualizarTurno(Turno turno) {
    Turno turnoActualizado = buscarTurnoPorId(turno.getId());

    turnoActualizado.setHora(turno.getHora());
    turnoActualizado.setFecha(turno.getFecha());
    turnoActualizado.setOdontologo(turno.getOdontologo());
    turnoActualizado.setPaciente(turno.getPaciente());

    return iTurnoRepository.save(turnoActualizado);
  }

  @Override
  public Turno eliminarTurnoPorId(Long id) {
    Turno turnoEliminado = buscarTurnoPorId(id);

    if (Objects.isNull(turnoEliminado)) return null;

    iTurnoRepository.deleteById(id);
    return turnoEliminado;
  }
}
