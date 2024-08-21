package com.odontologia.project.services;

import com.odontologia.project.models.Paciente;

import java.util.List;

public interface IPacienteService {
  Paciente guardarPaciente(Paciente paciente);

  Paciente buscarPaciente(Long id);

  List<Paciente> buscarTodosPacientes();

  Paciente actualizarPaciente(Paciente paciente);

  Paciente eliminarPaciente(Long id);
}
