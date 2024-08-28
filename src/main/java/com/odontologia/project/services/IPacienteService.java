package com.odontologia.project.services;

import com.odontologia.project.models.Paciente;

import java.util.List;

public interface IPacienteService {
  Paciente guardarPaciente(Paciente paciente);

  Paciente buscarPacientePorId(Long id);

  List<Paciente> buscarTodosPacientes();

  Paciente actualizarPaciente(Paciente paciente);

  Paciente eliminarPacientePorId(Long id);
}
