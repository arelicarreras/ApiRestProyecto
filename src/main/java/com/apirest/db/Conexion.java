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
    // Formato: jdbc:postgresql://HOST:PUERTO/NOMBRE_BASE_DE_DATOS
	private static final String URL = 
		    "jdbc:postgresql://ep-solitary-glitter-aqn7c3za-pooler.c-8.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channelBinding=require";
	private static final String USER = "neondb_owner";
	private static final String PASSWORD = "npg_lrM2msodNQ0w"; // <-- cambia si es

 
    /**
     * Retorna una nueva conexión abierta a PostgreSQL.
     * IMPORTANTE: el llamador es responsable de cerrarla con con.close().
     * Siempre úsala dentro de un bloque try-with-resources:
     *   try (Connection con = Conexion.obtener()) { ... }
     */
    public static Connection obtener() throws SQLException {
        // DriverManager busca el driver registrado para 'jdbc:postgresql'.
        // El driver se registra automáticamente al incluirlo en el classpath (Maven).
    	System.out.println(">>>>CONECTANDO A BD sistema_tickets...");
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
