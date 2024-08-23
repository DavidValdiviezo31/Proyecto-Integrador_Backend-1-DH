package com.odontologia.project.services.impl;

import com.odontologia.project.dao.IDao;
import com.odontologia.project.dao.impl.TurnoLOCAL;
import com.odontologia.project.models.Turno;
import com.odontologia.project.services.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {

  @Autowired
  private IDao<Turno> daoTurno;

  @Override
  public Turno guardarTurno(Turno turno) {
    return daoTurno.guardar(turno);
  }

  @Override
  public Turno buscarTurno(Long id) {
    return daoTurno.buscarPorId(id);
  }

  @Override
  public List<Turno> buscarTodosTurnos() {
    return daoTurno.buscarTodos();
  }

  @Override
  public Turno actualizarTurno(Turno turno) {
    return daoTurno.actualizar(turno);
  }

  @Override
  public Turno eliminarTurno(Long id) {
    return daoTurno.eliminarPorId(id);
  }
}
