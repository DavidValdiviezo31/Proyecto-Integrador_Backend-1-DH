package com.odontologia.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
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
      e.printStackTrace();
    }
    return conn;
  }

  public static void endConnection(Connection conn)  {
    try {
      if (conn != null) conn.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
