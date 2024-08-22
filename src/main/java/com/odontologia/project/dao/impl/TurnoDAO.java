package com.odontologia.project.dao.impl;

import com.odontologia.project.dao.DatabaseConnection;
import com.odontologia.project.dao.IDao;
import com.odontologia.project.models.Odontologo;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.models.Turno;
import com.odontologia.project.services.impl.OdontologoService;
import com.odontologia.project.services.impl.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO implements IDao<Turno> {

  private static final Logger logger = LoggerFactory.getLogger(TurnoDAO.class);

  @Override
  public Turno guardar(Turno turno) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_INSERT = "INSERT INTO TURNOS " +
        "(FECHA, HORA, DNI_PACIENTE, MATRICULA_ODONTOLOGO) VALUES(?, ?, ?, ?)";

    try {
      conn.setAutoCommit(false);
      PreparedStatement pStmt = crearPreparedStatement(conn, SQL_INSERT, turno);

      pStmt.execute();
      ResultSet rs = pStmt.getGeneratedKeys();

      conn.setAutoCommit(true);

      if (rs.next()) {
        turno.setId(rs.getLong("ID"));
        logger.info("TURNO {} AGREGADO EXITOSAMENTE A LA BD.", turno.getId());
      }
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        logger.error("ERROR AL EJECUTAR EL ROLLBACK: {}", err.getMessage());
      } finally {
        logger.error("ERROR AL AGREGAR TURNO A LA BD: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return null;
  }

    @Override
  public Turno buscarPorId(Long id) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_SELECT_BY_ID = "SELECT * FROM TURNOS WHERE ID = ?";

    try {
      PreparedStatement pStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
      pStmt.setLong(1, id);
      ResultSet rs = pStmt.executeQuery();

      if (!rs.next()) throw new Exception("EL TURNO CON ID " + id + " NO EXISTE EN LA BD.");

      return crearTurno(rs);
    } catch (Exception err) {
      logger.error("ERROR AL BUSCAR TURNO: {}", err.getMessage());
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }
    return null;
  }

  @Override
  public List<Turno> buscarTodos() {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_SELECT_ALL = "SELECT * FROM TURNOS";
    List<Turno> listaTurnos = new ArrayList<>();

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);

      while (rs.next()) listaTurnos.add(crearTurno(rs));
    } catch (Exception err) {
      logger.error("ERROR AL BUSCAR TURNOS: {}", err.getMessage());
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return listaTurnos;
  }

  @Override
  public Turno actualizar(Turno turno) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_UPDATE = "UPDATE TURNOS SET " +
        "FECHA = ?, " +
        "HORA = ?, " +
        "DNI_PACIENTE = ?, " +
        "MATRICULA_ODONTOLOGO = ? " +
        "WHERE ID = ?";

    try {
      buscarPorId(turno.getId());

      conn.setAutoCommit(false);
      PreparedStatement pStmt = crearPreparedStatement(conn, SQL_UPDATE, turno);
      pStmt.setLong(5, turno.getId());

      pStmt.executeUpdate();
      ResultSet rs = pStmt.getGeneratedKeys();

      conn.setAutoCommit(true);

      if (rs.next()) logger.info("TURNO {} MODIFICADO EXITOSAMENTE.", turno.getId());
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        logger.error("ERROR AL EJECUTAR EL ROLLBACK: {}", err.getMessage());
      } finally {
        logger.error("ERROR AL MODIFICAR TURNO: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return turno;
  }

  @Override
  public Turno eliminarPorId(Long id) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_DELETE_BY_ID = "DELETE FROM TURNOS WHERE ID = ?";

    try {
      Turno turnoDB = buscarPorId(id);

      conn.setAutoCommit(false);
      PreparedStatement pStmt = conn.prepareStatement(SQL_DELETE_BY_ID);

      pStmt.setLong(1, id);

      pStmt.executeUpdate();
      conn.setAutoCommit(true);

      logger.info("TURNO {} ELIMINADO EXITOSAMENTE.", turnoDB.getId());

      return turnoDB;
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        logger.error("ERROR AL EJECUTAR EL ROLLBACK: {}", err.getMessage());
      } finally {
        logger.error("ERROR AL ELIMINAR TURNO: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return null;
  }

  private static PreparedStatement crearPreparedStatement(Connection conn, String SQL, Turno turno) throws SQLException {
    PreparedStatement pStmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

    pStmt.setDate(1, Date.valueOf(turno.getFecha()));
    pStmt.setTime(2, Time.valueOf(turno.getHora()));
    pStmt.setLong(3, turno.getPaciente().getDni());
    pStmt.setLong(4, turno.getOdontologo().getMatricula());

    return pStmt;
  }

  private static Turno crearTurno(ResultSet rs) throws SQLException {
    Long idDB = rs.getLong("ID");
    LocalDate fechaDB = rs.getDate("FECHA").toLocalDate();
    LocalTime horaDB = rs.getTime("HORA").toLocalTime();
    Long dni_pacienteDB = rs.getLong("DNI_PACIENTE");
    Long matricula_odontologoDB = rs.getLong("MATRICULA_ODONTOLOGO");

    PacienteService pacienteService = new PacienteService();
    Paciente paciente = pacienteService.buscarPaciente(dni_pacienteDB);

    OdontologoService odontologoService = new OdontologoService();
    Odontologo odontologo = odontologoService.buscarOdontologo(matricula_odontologoDB);

    return new Turno(idDB, fechaDB, horaDB, paciente, odontologo);
  }
}
