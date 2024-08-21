package com.odontologia.project.services;

import com.odontologia.project.models.Domicilio;

import java.util.List;

public interface IDomicilioService {
  Domicilio guardarDomicilio(Domicilio domicilio);

  Domicilio buscarDomicilio(Long id);

  List<Domicilio> buscarTodosDomicilios();

  Domicilio actualizarDomicilio(Domicilio domicilio);

  Domicilio eliminarDomicilio(Long id);
}
