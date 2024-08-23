package com.odontologia.project.services.impl;

import com.odontologia.project.dao.IDao;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.services.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

  @Autowired
  private IDao<Odontologo> daoOdontologo;

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
