package com.odontologia.project.services.impl;

import com.odontologia.project.dao.IDao;
import com.odontologia.project.dao.impl.DomicilioDAO;
import com.odontologia.project.models.Domicilio;
import com.odontologia.project.services.IDomicilioService;

import java.util.List;

public class DomicilioService implements IDomicilioService {

  private final IDao<Domicilio> daoDomicilio;

  public DomicilioService() {
    this.daoDomicilio = new DomicilioDAO();
  }

  @Override
  public Domicilio guardarDomicilio(Domicilio domicilio) {
    return daoDomicilio.guardar(domicilio);
  }

  @Override
  public Domicilio buscarDomicilio(Long id) {
    return daoDomicilio.buscarPorId(id);
  }

  @Override
  public List<Domicilio> buscarTodosDomicilios() {
    return daoDomicilio.buscarTodos();
  }

  @Override
  public Domicilio actualizarDomicilio(Domicilio domicilio) {
    return daoDomicilio.actualizar(domicilio);
  }

  @Override
  public Domicilio eliminarDomicilio(Long id) {
    return daoDomicilio.eliminarPorId(id);
  }
}
