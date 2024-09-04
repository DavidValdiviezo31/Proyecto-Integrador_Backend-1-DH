package com.odontologia.project.services.impl;

import com.odontologia.project.models.Turno;
import com.odontologia.project.repositories.ITurnoRepository;
import com.odontologia.project.services.ITurnoService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TurnoService implements ITurnoService {
  private final ITurnoRepository iTurnoRepository;

  @Override
  public Turno guardarTurno(Turno turno) {
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
