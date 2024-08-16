package com.odontologia.project.dao.impl;

import com.odontologia.project.dao.DatabaseConnection;
import com.odontologia.project.dao.IDao;
import com.odontologia.project.models.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacienteDAO implements IDao<Paciente> {

  private static final Logger logger = LoggerFactory.getLogger(PacienteDAO.class);

  @Override
  public Paciente guardar(Paciente paciente) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_INSERT = "INSERT INTO PACIENTES " +
        "(DNI, NOMBRE, APELLIDO, DOMICILIO, FECHA_ALTA) VALUES(?, ?, ?, ?, ?)";

    try {
      logger.info("AGREGANDO PACIENTE {} A LA BD", paciente.getDni());

      conn.setAutoCommit(false);
      PreparedStatement pStmt = crearPreparedStatement(conn, SQL_INSERT, paciente);

      pStmt.execute();
      ResultSet rs = pStmt.getGeneratedKeys();

      conn.setAutoCommit(true);

      if (rs.next()) logger.info("PACIENTE {} AGREGADO EXITOSAMENTE A LA BD.", paciente.getDni());

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
  public Paciente buscarPorId(Long dni) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_SELECT_BY_ID = "SELECT * FROM PACIENTES WHERE DNI = ?";

    try {
      PreparedStatement pStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
      pStmt.setLong(1, dni);
      ResultSet rs = pStmt.executeQuery();

      if (!rs.next()) throw new Exception("EL PACIENTE CON DNI " + dni + " NO EXISTE EN LA BD.");

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

      System.out.println("---------------LISTA DE PACIENTES---------------");
      listaPacientes.forEach(System.out::println);
    } catch (Exception err) {
      logger.error("ERROR AL BUSCAR TURNOS: {}", err.getMessage());
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
        "DOMICILIO = ?, " +
        "FECHA_ALTA = ? " +
        "WHERE DNI = ?";

    try {
      Paciente pacienteDB = buscarPorId(paciente.getDni());

      if (Objects.isNull(pacienteDB)) throw new Exception("EL PACIENTE " + paciente.getDni() + " NO EXISTE EN LA BD.");

      conn.setAutoCommit(false);
      PreparedStatement pStmt = crearPreparedStatement(conn, SQL_UPDATE, paciente);
      pStmt.setLong(6, paciente.getDni());

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
  public Paciente eliminarPorId(Long dni) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_DELETE_BY_ID = "DELETE FROM PACIENTES WHERE DNI = ?";

    try {
      Paciente paciente = buscarPorId(dni);
      if (Objects.isNull(paciente)) throw new Exception("EL PACIENTE " + dni + " NO EXISTE EN LA BD.");

      conn.setAutoCommit(false);
      PreparedStatement pStmt = conn.prepareStatement(SQL_DELETE_BY_ID);

      pStmt.setLong(1, dni);

      pStmt.executeUpdate();
      conn.setAutoCommit(true);

      logger.info("PACIENTE {} ELIMINADO EXITOSAMENTE.", paciente.getDni());

      return paciente;
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
    Long dniDB = rs.getLong("DNI");
    String nombreDB = rs.getString("NOMBRE");
    String apellidoDB = rs.getString("APELLIDO");
    String domicilioDB = rs.getString("DOMICILIO");
    LocalDate fechaAltaDB = rs.getDate("FECHA_ALTA").toLocalDate();

    return new Paciente(dniDB, nombreDB, apellidoDB, domicilioDB, fechaAltaDB);
  }

  private static PreparedStatement crearPreparedStatement(Connection conn, String SQL, Paciente paciente) throws SQLException {
    PreparedStatement pStmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

    pStmt.setLong(1, paciente.getDni());
    pStmt.setString(2, paciente.getNombre());
    pStmt.setString(3, paciente.getApellido());
    pStmt.setString(4, paciente.getDomicilio());
    pStmt.setDate(5, Date.valueOf(paciente.getFechaAlta()));

    return pStmt;
  }
}
