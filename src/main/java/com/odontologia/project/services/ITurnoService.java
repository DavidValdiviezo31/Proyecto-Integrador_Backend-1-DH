package com.odontologia.project.services;

import com.odontologia.project.models.Paciente;
import com.odontologia.project.models.Turno;

import java.util.List;

public interface ITurnoService {
  Turno guardarTurno(Turno turno);

  Turno buscarTurno(Long id);

  List<Turno> buscarTodosTurnos();

  Turno actualizarTurno(Turno turno);

  Turno eliminarTurno(Long id);
}
