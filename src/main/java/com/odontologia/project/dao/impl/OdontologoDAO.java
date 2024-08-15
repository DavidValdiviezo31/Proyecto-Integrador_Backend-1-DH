package com.odontologia.project.dao.impl;

import com.odontologia.project.dao.DatabaseConnection;
import com.odontologia.project.dao.IDao;
import com.odontologia.project.models.Odontologo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OdontologoDAO implements IDao<Odontologo> {

  private static final Logger logger = LogManager.getLogger(OdontologoDAO.class);

  @Override
  public Odontologo guardar(Odontologo odontologo) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_INSERT = "INSERT INTO ODONTOLOGOS " +
        "(MATRICULA, NOMBRE, APELLIDO) VALUES(?, ?, ?)";

    try {
      logger.info("AGREGANDO ODONTOLOGO {} A LA BD", odontologo.getMatricula());

      conn.setAutoCommit(false);
      PreparedStatement pStmt = crearPreparedStatement(conn, SQL_INSERT, odontologo);

      pStmt.execute();
      ResultSet rs = pStmt.getGeneratedKeys();

      conn.setAutoCommit(true);

      if (rs.next()) logger.info("ODONTOLOGO {} AGREGADO EXITOSAMENTE A LA BD.", odontologo.getMatricula());

    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        logger.error("ERROR AL AGREGAR ODONTOLOGO A LA BD: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

    return odontologo;
  }

  @Override
  public Odontologo buscarPorId(Long matricula) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_SELECT_BY_ID = "SELECT * FROM ODONTOLOGOS WHERE MATRICULA = ?";

    try {
      PreparedStatement pStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
      pStmt.setLong(1, matricula);
      ResultSet rs = pStmt.executeQuery();

      if (!rs.next()) throw new Exception("EL ODONTOLOGO CON DNI " + matricula + " NO EXISTE EN LA BD.");

      return crearOdontologo(rs);

    } catch (Exception err) {
      logger.error("ERROR AL BUSCAR ODONTOLOGO: {}", err.getMessage());
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

    return null;
  }

  @Override
  public List<Odontologo> buscarTodos() {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    List<Odontologo> listaOdontologos = new ArrayList<>();

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);

      while (rs.next()) listaOdontologos.add(crearOdontologo(rs));

      System.out.println("---------------LISTA DE ODONTOLOGOS---------------");
      listaOdontologos.forEach(System.out::println);
    } catch (Exception err) {
      err.printStackTrace();
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        err.printStackTrace();
      }
    }
    return listaOdontologos;
  }

  @Override
  public Odontologo actualizar(Odontologo odontologo) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_UPDATE = "UPDATE ODONTOLOGOS SET " +
        "MATRICULA = ?, " +
        "NOMBRE = ?, " +
        "APELLIDO = ? " +
        "WHERE MATRICULA = ?";

    try {
      Odontologo odontologoDB = buscarPorId(odontologo.getMatricula());

      if (odontologoDB != null) {

        conn.setAutoCommit(false);
        PreparedStatement pStmt = crearPreparedStatement(conn, SQL_UPDATE, odontologo);
        pStmt.setLong(4, odontologo.getMatricula());

        pStmt.executeUpdate();
        ResultSet rs = pStmt.getGeneratedKeys();

        conn.setAutoCommit(true);

        if (rs.next()) logger.info("ODONTOLOGO {} MODIFICADO EXITOSAMENTE.", odontologo.getMatricula());
      }
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        logger.error("ERROR AL MODIFICAR ODONTOLOGO: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

    return odontologo;
  }

  @Override
  public Odontologo eliminarPorId(Long matricula) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_DELETE_BY_ID = "DELETE FROM ODONTOLOGOS WHERE MATRICULA = ?";

    try {
      Odontologo odontologo = buscarPorId(matricula);
      if (Objects.isNull(odontologo)) throw new Exception("EL ODONTOLOGO " + matricula + " NO EXISTE EN LA BD.");

      conn.setAutoCommit(false);
      PreparedStatement pStmt = conn.prepareStatement(SQL_DELETE_BY_ID);

      pStmt.setLong(1, matricula);

      pStmt.executeUpdate();
      conn.setAutoCommit(true);

      logger.info("ODONTOLOGO {} ELIMINADO EXITOSAMENTE.", odontologo.getMatricula());
      return odontologo;
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        logger.error("ERROR AL ELIMINAR ODONTOLOGO: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        err.printStackTrace();
      }
    }

    return null;
  }

  private static Odontologo crearOdontologo(ResultSet rs) throws SQLException {
    Long matriculaDB = rs.getLong("MATRICULA");
    String nombreDB = rs.getString("NOMBRE");
    String apellidoDB = rs.getString("APELLIDO");

    return new Odontologo(matriculaDB, nombreDB, apellidoDB);
  }

  private static PreparedStatement crearPreparedStatement(Connection conn, String SQL, Odontologo odontologo) throws SQLException {
    PreparedStatement pStmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

    pStmt.setLong(1, odontologo.getMatricula());
    pStmt.setString(2, odontologo.getNombre());
    pStmt.setString(3, odontologo.getApellido());

    return pStmt;
  }
}
