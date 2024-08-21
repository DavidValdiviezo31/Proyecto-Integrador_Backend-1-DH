package com.odontologia.project.services.impl;

import com.odontologia.project.dao.IDao;
import com.odontologia.project.dao.impl.OdontologoDAO;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.services.IOdontologoService;

import java.util.List;

public class OdontologoService implements IOdontologoService {
  private final IDao<Odontologo> daoOdontologo;

  public OdontologoService() {
    this.daoOdontologo = new OdontologoDAO();
  }

  @Override
  public Odontologo guardarOdontologo(Odontologo odontologo) {
    return daoOdontologo.guardar(odontologo);
  }

  @Override
  public Odontologo buscarOdontologo(Long id) {
    return daoOdontologo.buscarPorId(id);
  }

  @Override
  public List<Odontologo> buscarTodosOdontologos() {
    return daoOdontologo.buscarTodos();
  }

  @Override
  public Odontologo actualizarOdontologo(Odontologo odontologo) {
    return daoOdontologo.actualizar(odontologo);
  }

  public Odontologo eliminarOdontologo(Long id) {
    return daoOdontologo.eliminarPorId(id);
  }
}
