package com.apirest.db;
import java.sql.Connection;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.apirest.modelo.Usuario;

public class UsuarioDAO {
	
	public Usuario login(String correo, String password) {
		String sql =
		"SELECT id, nombre, correo, departamento, rol FROM usuarios WHERE correo = ? AND password = ?";
		try (
			Connection con = Conexion.obtener();
			PreparedStatement ps =
			con.prepareStatement(sql)
		) {
			ps.setString(1, correo);
			ps.setString(2,encriptar(password));
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()){
					Usuario U = new Usuario();
					U.setId(rs.getInt("id"));
					U.setNombre(rs.getString("nombre"));
					U.setCorreo(rs.getString("correo"));
					U.setDepartamento(rs.getString("departamento"));
					U.setRol(rs.getString("rol"));
					return U;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}
	private String encriptar(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes("UTF-8"));

			StringBuilder sb = new StringBuilder();

			for(byte b : hash){
				sb.append(
					String.format("%02x", b)
				);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Usuario> obtenerTodos() {
		List<Usuario> lista = new ArrayList<>();
	    String sql = "SELECT id, nombre, correo, departamento, rol FROM usuarios ORDER BY id";
	    try (Connection con = Conexion.obtener();
	         Statement st = con.createStatement();
	         ResultSet rs = st.executeQuery(sql)) {
	        while (rs.next()) {
	            Usuario U = new Usuario();
	            U.setId(rs.getInt("id"));
	            U.setNombre(rs.getString("nombre"));
	            U.setCorreo(rs.getString("correo"));
	            U.setDepartamento(rs.getString("departamento"));
	            U.setRol(rs.getString("rol"));
	            lista.add(U);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return lista;
}
	public Usuario obtenerPorId(int id) {
		String sql = "SELECT id, nombre, correo, departamento, rol FROM usuarios WHERE id = ?";
			try (Connection con = Conexion.obtener();
			PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id); 
			try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) { 
				Usuario U = new Usuario();
			U.setId(rs.getInt("id"));
            U.setNombre(rs.getString("nombre"));
            U.setCorreo(rs.getString("correo"));
            U.setDepartamento(rs.getString("departamento"));
            U.setRol(rs.getString("rol"));
			  return U;
				 }
			}
			} catch (SQLException e) {
				 e.printStackTrace();
			}
			return null; 
				 }	
	public Usuario crear(Usuario U) {
		 String sql = "INSERT INTO usuarios (nombre, correo, password, departamento, rol) VALUES (?, ?, ?, ?, ?)";
		 try (Connection con = Conexion.obtener();
		 PreparedStatement ps =con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		 ps.setString(1, U.getNombre());
		 ps.setString(2, U.getCorreo());
		 ps.setString(3, encriptar(U.getPassword()));
		 ps.setString(4, U.getDepartamento());
		 ps.setString(5, U.getRol());
		 ps.executeUpdate();
		 
		 try (ResultSet rs = ps.getGeneratedKeys()){
			 if (rs.next()) U.setId(rs.getInt(1));
		 }
		 } catch (SQLException e) {
		 e.printStackTrace();
		 }
		 return U; 
		 }	
	public boolean actualizar(int id, Usuario U) {
		 String sql = "UPDATE public.usuarios SET nombre=?, correo=?, departamento=?, rol=? WHERE id=?";
		 try (Connection con = Conexion.obtener();
		 PreparedStatement ps = con.prepareStatement(sql)) {
			 ps.setString(1, U.getNombre());
			 ps.setString(2, U.getCorreo());
			 ps.setString(3, U.getDepartamento());
			 ps.setString(4, U.getRol());
		     ps.setInt(5, id);
		 return ps.executeUpdate() > 0; 
		 } catch (SQLException e) {
		 e.printStackTrace();
		 return false;
		 }
		 }
	public boolean eliminar(int id) {
	    String sql = "DELETE FROM usuarios WHERE id=?";
	    try (Connection con = Conexion.obtener();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
