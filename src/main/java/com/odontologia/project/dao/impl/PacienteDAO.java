package com.odontologia.project.dao.impl;

import com.odontologia.project.dao.DatabaseConnection;
import com.odontologia.project.dao.IDao;
import com.odontologia.project.models.Domicilio;
import com.odontologia.project.models.Paciente;
import com.odontologia.project.services.impl.DomicilioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PacienteDAO implements IDao<Paciente> {

  private static final Logger logger = LoggerFactory.getLogger(PacienteDAO.class);

  @Override
  public Paciente guardar(Paciente paciente) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_INSERT = "INSERT INTO PACIENTES " +
        "(DNI, NOMBRE, APELLIDO, DOMICILIO_ID, FECHA_ALTA) VALUES(?, ?, ?, ?, ?)";

    try {
      DomicilioService domicilioService = new DomicilioService();
      Domicilio domicilio = domicilioService.guardarDomicilio(paciente.getDomicilio());
      paciente.setDomicilio(domicilio);

      conn.setAutoCommit(false);
      PreparedStatement pStmt = crearPreparedStatement(conn, SQL_INSERT, paciente);

      pStmt.execute();
      ResultSet rs = pStmt.getGeneratedKeys();

      conn.setAutoCommit(true);

      if (rs.next()) {
        paciente.setId(rs.getLong("ID"));
        logger.info("PACIENTE {} AGREGADO EXITOSAMENTE A LA BD.", paciente.getId());
      }
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        logger.error("ERROR AL EJECUTAR EL ROLLBACK: {}", err.getMessage());
      } finally {
        logger.error("ERROR AL AGREGAR PACIENTE A LA BD: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return paciente;
  }

  @Override
  public Paciente buscarPorId(Long id) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_SELECT_BY_ID = "SELECT * FROM PACIENTES WHERE ID = ?";

    try {
      PreparedStatement pStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
      pStmt.setLong(1, id);
      ResultSet rs = pStmt.executeQuery();

      if (!rs.next()) throw new Exception("EL PACIENTE CON ID " + id + " NO EXISTE EN LA BD.");

      return crearPaciente(rs);
    } catch (Exception err) {
      logger.error("ERROR AL BUSCAR PACIENTE: {}", err.getMessage());
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
  public List<Paciente> buscarTodos() {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_SELECT_ALL = "SELECT * FROM PACIENTES";
    List<Paciente> listaPacientes = new ArrayList<>();

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);

      while (rs.next()) listaPacientes.add(crearPaciente(rs));
    } catch (Exception err) {
      logger.error("ERROR AL BUSCAR PACIENTES: {}", err.getMessage());
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return listaPacientes;
  }

  @Override
  public Paciente actualizar(Paciente paciente) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_UPDATE = "UPDATE PACIENTES SET " +
        "DNI = ?, " +
        "NOMBRE = ?, " +
        "APELLIDO = ?, " +
        "DOMICILIO_ID = ?, " +
        "FECHA_ALTA = ? " +
        "WHERE ID = ?";

    try {
      Paciente pacienteDB = buscarPorId(paciente.getId());

      conn.setAutoCommit(false);
      paciente.setDomicilio(pacienteDB.getDomicilio());

      PreparedStatement pStmt = crearPreparedStatement(conn, SQL_UPDATE, paciente);
      pStmt.setLong(6, paciente.getId());

      pStmt.executeUpdate();
      ResultSet rs = pStmt.getGeneratedKeys();

      conn.setAutoCommit(true);

      if (rs.next()) logger.info("PACIENTE {} MODIFICADO EXITOSAMENTE.", paciente.getDni());
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        logger.error("ERROR AL EJECUTAR EL ROLLBACK: {}", err.getMessage());
      } finally {
        logger.error("ERROR AL MODIFICAR PACIENTE: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return paciente;
  }

  @Override
  public Paciente eliminarPorId(Long id) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_DELETE_BY_ID = "DELETE FROM PACIENTES WHERE ID = ?";

    try {
      Paciente pacienteDB = buscarPorId(id);

      conn.setAutoCommit(false);
      PreparedStatement pStmt = conn.prepareStatement(SQL_DELETE_BY_ID);

      pStmt.setLong(1, id);

      pStmt.executeUpdate();
      DomicilioService domicilioService = new DomicilioService();
      domicilioService.eliminarDomicilio(pacienteDB.getDomicilio().getId());
      conn.setAutoCommit(true);

      logger.info("PACIENTE {} ELIMINADO EXITOSAMENTE.", pacienteDB.getDni());

      return pacienteDB;
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        logger.error("ERROR AL EJECUTAR EL ROLLBACK: {}", err.getMessage());
      } finally {
        logger.error("ERROR AL ELIMINAR PACIENTE: {}", err.getMessage());
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

  private static Paciente crearPaciente(ResultSet rs) throws SQLException {
    DomicilioService domicilioService = new DomicilioService();
    Long idDB = rs.getLong("ID");
    Long dniDB = rs.getLong("DNI");
    String nombreDB = rs.getString("NOMBRE");
    String apellidoDB = rs.getString("APELLIDO");
    Domicilio domicilioDB = domicilioService.buscarDomicilio(rs.getLong("DOMICILIO_ID"));
    LocalDate fechaAltaDB = rs.getDate("FECHA_ALTA").toLocalDate();

    return new Paciente(idDB, dniDB, nombreDB, apellidoDB, domicilioDB, fechaAltaDB);
  }

  private static PreparedStatement crearPreparedStatement(Connection conn, String SQL, Paciente paciente) throws SQLException {
    PreparedStatement pStmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

    pStmt.setLong(1, paciente.getDni());
    pStmt.setString(2, paciente.getNombre());
    pStmt.setString(3, paciente.getApellido());
    pStmt.setLong(4, paciente.getDomicilio().getId());
    pStmt.setDate(5, Date.valueOf(paciente.getFechaAlta()));

    return pStmt;
  }
}
