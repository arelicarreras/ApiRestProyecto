package com.apirest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para obtener conexiones a PostgreSQL.
 * Centraliza los parámetros de conexión en un solo lugar.
 * Uso: Connection con = Conexion.obtener();
 */
public class Conexion {
    
    // ── Parámetros de conexión ────────────────────────────────────
    // Se leen desde variables de entorno definidas en Render
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASS");

    /**
     * Retorna una nueva conexión abierta a PostgreSQL.
     * IMPORTANTE: el llamador es responsable de cerrarla con con.close().
     * Siempre úsala dentro de un bloque try-with-resources:
     *   try (Connection con = Conexion.obtener()) { ... }
     */
    public static Connection obtener() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e) {
            System.out.println("DRIVER NO ENCONTRADO");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Verifica si la conexión funciona. Llama este método UNA vez
     * para confirmar que la configuración es correcta.
     */
    public static boolean probarConexion() {
        try (Connection con = obtener()) {
            System.out.println("✅ Conexión a PostgreSQL exitosa!");
            System.out.println("   Base de datos: " + con.getCatalog());
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            System.err.println("   Verifica: PostgreSQL activo, credenciales, puerto 5433");
            return false;
        }
    }
}
