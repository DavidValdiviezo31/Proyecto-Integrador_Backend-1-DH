package com.odontologia.project.dao.impl;

import com.odontologia.project.dao.IDao;
import com.odontologia.project.models.Turno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TurnoLOCAL implements IDao<Turno> {

  private static final Logger logger = LoggerFactory.getLogger(TurnoLOCAL.class);
  private static final List<Turno> listaTurnos = new ArrayList<>();

  @Override
  public Turno guardar(Turno turno) {
    Long id = listaTurnos.size() + 1L;
    turno.setId(id);
    listaTurnos.add(turno);
    logger.info("TURNO {} AGREGADO EXITOSAMENTE A LA BD.", turno.getId());
    return turno;
  }

  @Override
  public Turno buscarPorId(Long id) {

    try {
      Turno turnoBuscado = listaTurnos.stream().filter(turno -> turno.getId().equals(id)).findFirst().orElse(null);

      if (Objects.isNull(turnoBuscado)) throw new Exception("EL TURNO INGRESADO NO EXISTE DENTRO DE LA BD.");

      return turnoBuscado;
    } catch (Exception err) {
      logger.error("ERROR AL BUSCAR TURNO: {}", err.getMessage());
    }

    return null;
  }

  @Override
  public List<Turno> buscarTodos() {
    return listaTurnos;
  }

  @Override
  public Turno actualizar(Turno turno) {
    try {
      Turno turnoDB = buscarPorId(turno.getId());

      if (turnoDB.getFecha() != turno.getFecha() && turno.getFecha() != null)
        turnoDB.setFecha(turno.getFecha());

      if (turnoDB.getHora() != turno.getHora() && turno.getHora() != null)
        turnoDB.setHora(turno.getHora());

      if (turnoDB.getOdontologo() != turno.getOdontologo() && !Objects.isNull(turno.getOdontologo()))
        turnoDB.setOdontologo(turno.getOdontologo());

      if (turnoDB.getPaciente() != turno.getPaciente() && !Objects.isNull(turno.getPaciente()))
        turnoDB.setPaciente(turno.getPaciente());

      logger.info("TURNO {} MODIFICADO EXITOSAMENTE.", turno.getId());

      return turnoDB;
    } catch (Exception err) {
      logger.error("ERROR AL MODIFICAR TURNO: {}", err.getMessage());
    }

    return null;
  }

  @Override
  public Turno eliminarPorId(Long id) {

    try {
      Turno turnoDB = buscarPorId(id);

      listaTurnos.remove(turnoDB);

      logger.info("TURNO {} ELIMINADO EXITOSAMENTE.", turnoDB.getId());

      return turnoDB;
    } catch (Exception err) {
      logger.error("ERROR AL ELIMINAR TURNO: {}", err.getMessage());
    }

    return null;
  }
}
