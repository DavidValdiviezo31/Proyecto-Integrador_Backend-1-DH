package com.odontologia.project.services;

import com.odontologia.project.models.Odontologo;

import java.util.List;

public interface IOdontologoService {
  Odontologo guardarOdontologo(Odontologo odontologo);

  Odontologo buscarOdontologo(Long id);

  List<Odontologo> buscarTodosOdontologos();

  Odontologo actualizarOdontologo(Odontologo odontologo);

  Odontologo eliminarOdontologo(Long id);
}
