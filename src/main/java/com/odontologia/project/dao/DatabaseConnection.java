package com.odontologia.project.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
  private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

  public static Connection getConnection() throws Exception {
    Class.forName("org.h2.Driver");
    return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/h2db/odontologia", "sa", "");
  }

  public static Connection startConnection() {
    Connection conn = null;

    try {
      conn = getConnection();
      if (!conn.isClosed()) return conn;
    } catch(Exception e) {
      logger.error("ERROR AL INICIAR LA CONEXIÓN CON LA BD: {}", e.getMessage());
    }
    return conn;
  }

  public static void endConnection(Connection conn)  {
    try {
      if (conn != null) conn.close();
    } catch(Exception e) {
      logger.error("ERROR AL CERRAR LA CONEXION CON LA BD: {}", e.getMessage());
    }
  }
}
