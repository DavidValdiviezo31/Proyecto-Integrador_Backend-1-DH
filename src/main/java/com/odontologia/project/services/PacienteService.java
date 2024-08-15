package com.odontologia.project.services;

import com.odontologia.project.dao.IDao;
import com.odontologia.project.dao.impl.PacienteDAO;
import com.odontologia.project.models.Paciente;

import java.util.List;

public class PacienteService {
  private IDao<Paciente> daoPaciente;

  public PacienteService() {
    this.daoPaciente = new PacienteDAO();
  }

  public Paciente guardarPaciente(Paciente paciente) {
    return daoPaciente.guardar(paciente);
  }

  public Paciente buscarPaciente(Long dni) {
    return daoPaciente.buscarPorId(dni);
  }

  public List<Paciente> buscarTodosPacientes() {
    return daoPaciente.buscarTodos();
  }

  public Paciente actualizarPaciente(Paciente paciente) {
    return daoPaciente.actualizar(paciente);
  }

  public Paciente eliminarPaciente(Long dni) {
    return daoPaciente.eliminarPorId(dni);
  }
}
