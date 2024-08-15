package com.odontologia.project.services;

import com.odontologia.project.dao.IDao;
import com.odontologia.project.dao.impl.OdontologoDAO;
import com.odontologia.project.models.Odontologo;

import java.util.List;

public class OdontologoService {
  private IDao<Odontologo> daoOdontologo;

  public OdontologoService() {
    this.daoOdontologo = new OdontologoDAO();
  }

  public Odontologo guardarOdontologo(Odontologo odontologo) {
    return daoOdontologo.guardar(odontologo);
  }

  public Odontologo buscarOdontologo(Long matricula) {
    return daoOdontologo.buscarPorId(matricula);
  }

  public List<Odontologo> buscarTodosOdontologos() {
    return daoOdontologo.buscarTodos();
  }

  public Odontologo actualizarOdontologo(Odontologo odontologo) {
    return daoOdontologo.actualizar(odontologo);
  }

  public Odontologo eliminarOdontologo(Long matricula) {
    return daoOdontologo.eliminarPorId(matricula);
  }

}
