package com.odontologia.project.services.impl;

import com.odontologia.project.dao.IDao;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.services.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {

  @Autowired
  private IDao<Paciente> daoPaciente;

    @Override
  public Paciente guardarPaciente(Paciente paciente) {
    return daoPaciente.guardar(paciente);
  }

  @Override
  public Paciente buscarPaciente(Long id) {
    return daoPaciente.buscarPorId(id);
  }

  @Override
  public List<Paciente> buscarTodosPacientes() {
    return daoPaciente.buscarTodos();
  }

  @Override
  public Paciente actualizarPaciente(Paciente paciente) {
    return daoPaciente.actualizar(paciente);
  }

  @Override
  public Paciente eliminarPaciente(Long id) {
    return daoPaciente.eliminarPorId(id);
  }
}
