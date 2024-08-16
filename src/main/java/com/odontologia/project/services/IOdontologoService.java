package com.odontologia.project.services;

import com.odontologia.project.models.Odontologo;
import com.odontologia.project.services.impl.OdontologoService;

import java.util.List;

public interface IOdontologoService {
    Odontologo guardarOdontologo(Odontologo odontologo);

    Odontologo buscarOdontologo(Long matricula);

    List<Odontologo> buscarTodosOdontologos();

    Odontologo actualizarOdontologo(Odontologo odontologo);

    Odontologo eliminarOdontologo(Long matricula);

}
