package com.apri.ms.resenas.utilidad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos PostgreSQL
 * Base de datos independiente para el microservicio de reseñas
 */
public class ConexionDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/apri_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver de PostgreSQL", e);
        }
    }
    
    /**
     * Obtiene una conexión a la base de datos
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /**
     * Cierra la conexión de forma segura
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
