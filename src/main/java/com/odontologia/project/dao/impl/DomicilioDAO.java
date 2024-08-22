package com.odontologia.project.dao.impl;

import com.odontologia.project.dao.DatabaseConnection;
import com.odontologia.project.dao.IDao;
import com.odontologia.project.models.Domicilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDAO implements IDao<Domicilio> {

  private static final Logger logger = LoggerFactory.getLogger(DomicilioDAO.class);

  @Override
  public Domicilio guardar(Domicilio domicilio) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_INSERT = "INSERT INTO DOMICILIOS " +
        "(CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES(?, ?, ?, ?)";

    try {
      conn.setAutoCommit(false);
      PreparedStatement pStmt = crearPreparedStatement(conn, SQL_INSERT, domicilio);

      pStmt.execute();
      ResultSet rs = pStmt.getGeneratedKeys();

      conn.setAutoCommit(true);

      if (rs.next()) {
        domicilio.setId(rs.getLong("ID"));
        logger.info("DOMICILIO {} AGREGADO EXITOSAMENTE A LA BD.", domicilio.getId());
      }
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        logger.error("ERROR AL EJECUTAR EL ROLLBACK: {}", err.getMessage());
      } finally {
        logger.error("ERROR AL AGREGAR DOMICILIO A LA BD: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return domicilio;
  }

  @Override
  public Domicilio buscarPorId(Long id) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_SELECT_BY_ID = "SELECT * FROM DOMICILIOS WHERE ID = ?";

    try {
      PreparedStatement pStmt = conn.prepareStatement(SQL_SELECT_BY_ID);
      pStmt.setLong(1, id);
      ResultSet rs = pStmt.executeQuery();

      if (!rs.next()) throw new Exception("EL DOMICILIO CON ID " + id + " NO EXISTE EN LA BD.");

      return crearDomicilio(rs);
    } catch (Exception err) {
      logger.error("ERROR AL BUSCAR DOMICILIO: {}", err.getMessage());
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
  public List<Domicilio> buscarTodos() {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_SELECT_ALL = "SELECT * FROM DOMICILIOS";
    List<Domicilio> listaDomicilios = new ArrayList<>();

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);

      while (rs.next()) listaDomicilios.add(crearDomicilio(rs));
    } catch (Exception err) {
      logger.error("ERROR AL BUSCAR DOMICILIOS: {}", err.getMessage());
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return listaDomicilios;
  }

  @Override
  public Domicilio actualizar(Domicilio domicilio) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_UPDATE = "UPDATE DOMICILIOS SET " +
        "CALLE = ?, " +
        "NUMERO = ?, " +
        "LOCALIDAD = ?, " +
        "PROVINCIA = ? " +
        "WHERE ID = ?";

    try {
      buscarPorId(domicilio.getId());

      conn.setAutoCommit(false);
      PreparedStatement pStmt = crearPreparedStatement(conn, SQL_UPDATE, domicilio);
      pStmt.setLong(5, domicilio.getId());

      pStmt.executeUpdate();
      ResultSet rs = pStmt.getGeneratedKeys();

      conn.setAutoCommit(true);

      if (rs.next()) logger.info("DOMICILIO {} MODIFICADO EXITOSAMENTE.", domicilio.getId());
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        logger.error("ERROR AL EJECUTAR EL ROLLBACK: {}", err.getMessage());
      } finally {
        logger.error("ERROR AL MODIFICAR DOMICILIO: {}", err.getMessage());
      }
    } finally {
      try {
        if (conn != null) DatabaseConnection.endConnection(conn);
      } catch (Exception err) {
        logger.error("ERROR AL CERRAR LA CONEXIÓN CON LA BD");
      }
    }

    return domicilio;
  }

  @Override
  public Domicilio eliminarPorId(Long id) {
    Connection conn = DatabaseConnection.startConnection();
    final String SQL_DELETE_BY_ID = "DELETE FROM DOMICILIOS WHERE ID = ?";

    try {
      Domicilio domicilioDB = buscarPorId(id);

      conn.setAutoCommit(false);
      PreparedStatement pStmt = conn.prepareStatement(SQL_DELETE_BY_ID);

      pStmt.setLong(1, id);

      pStmt.executeUpdate();
      conn.setAutoCommit(true);

      logger.info("DOMICILIO {} ELIMINADO EXITOSAMENTE.", domicilioDB.getId());

      return domicilioDB;
    } catch (Exception err) {
      try {
        if (conn != null) conn.rollback();
      } catch (Exception e) {
        logger.error("ERROR AL EJECUTAR EL ROLLBACK: {}", err.getMessage());
      } finally {
        logger.error("ERROR AL ELIMINAR DOMICILIO: {}", err.getMessage());
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

  private static Domicilio crearDomicilio(ResultSet rs) throws SQLException {
    Long idDB = rs.getLong("ID");
    String calleDB = rs.getString("CALLE");
    Integer numeroDB = rs.getInt("NUMERO");
    String localidadDB = rs.getString("LOCALIDAD");
    String provinciaDB = rs.getString("PROVINCIA");

    return new Domicilio(idDB, calleDB, numeroDB, localidadDB, provinciaDB);
  }

  private static PreparedStatement crearPreparedStatement(Connection conn, String SQL, Domicilio domicilio) throws SQLException {
    PreparedStatement pStmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

    pStmt.setString(1, domicilio.getCalle());
    pStmt.setInt(2, domicilio.getNumero());
    pStmt.setString(3, domicilio.getLocalidad());
    pStmt.setString(4, domicilio.getProvincia());

    return pStmt;
  }
}
