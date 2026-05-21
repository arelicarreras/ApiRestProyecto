package com.apirest.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.apirest.modelo.Ticket;
import com.apirest.modelo.TicketAdjunto;


public class TicketAdjuntoDAO {
	public List<TicketAdjunto> obtenerTodos() {
		List<TicketAdjunto> lista = new ArrayList<>();
	    String sql = """
	    		SELECT ta.id, ta.ticket, ta.nombreOriginal, ta.rutaArchivo, ta.tipoMime, ta.tamanio, 
	    		t.codigo, t.descripcion, t.estado
	    		FROM ticket_adjuntos ta JOIN ticket t ON ta.ticket = t.id ORDER BY ta.id
	    		""";
	    try (Connection con = Conexion.obtener();
	         Statement st = con.createStatement();
	         ResultSet rs = st.executeQuery(sql)) {
	        while (rs.next()) {
	        	TicketAdjunto  T= new TicketAdjunto();
	            T.setId(rs.getInt("id"));
	            
	            Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("ticket"));
                ticket.setCodigo(rs.getString("codigo"));
                ticket.setDescripcion(rs.getString("descripcion"));
                ticket.setEstado(rs.getString("estado"));
                
                T.setTicket(ticket);
	            T.setNombreOriginal(rs.getString("nombreOriginal"));
                T.setRutaArchivo(rs.getString("rutaArchivo"));
                T.setTipoMime(rs.getString("tipoMime"));
                T.setTamanio(rs.getLong("tamanio"));

	            lista.add(T);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return lista;
}
	public TicketAdjunto obtenerPorId(int id) {
		String sql = """
				SELECT ta.id, ta.ticket, ta.nombreOriginal, ta.rutaArchivo, ta.tipoMime, ta.tamanio,
				t.codigo, t.descripcion, t.estado 
				FROM ticket_adjuntos ta JOIN ticket t ON ta.ticket = t.id WHERE ta.id = ?
	    		""";
		try (Connection con = Conexion.obtener();
	            PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                	TicketAdjunto t = new TicketAdjunto();
						t .setId(rs.getInt("id"));
						
			            Ticket ticket = new Ticket();
			            ticket.setId(rs.getInt("ticket"));
			            ticket.setCodigo(rs.getString("codigo"));
			            ticket.setDescripcion(rs.getString("descripcion"));
			            ticket.setEstado(rs.getString("estado"));
			            
		                t.setTicket(ticket);
			            t.setNombreOriginal(rs.getString("nombreOriginal"));
		                t.setRutaArchivo(rs.getString("rutaArchivo"));
		                t.setTipoMime(rs.getString("tipoMime"));
		                t.setTamanio(rs.getLong("tamanio"));
					  return t;
					  }
	                }
					} catch (SQLException e) {
						 e.printStackTrace();
					}
					return null; 
				 }	
	
	public TicketAdjunto crear(TicketAdjunto t) {
	    if (t.getTicket() == null){
	        	return null;
	        }
	    String sql = "INSERT INTO ticket_adjuntos (ticket, nombreOriginal, rutaArchivo, tipoMime, tamanio) VALUES (?, ?, ?, ?, ?)";
	    try (Connection con = Conexion.obtener();
	        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	    	ps.setInt(1, t.getTicket().getId());
	        ps.setString(2, t.getNombreOriginal());
	        ps.setString(3, t.getRutaArchivo());
	        ps.setString(4, t.getTipoMime());
	        ps.setLong(5, t.getTamanio());
	        
	        int filas = ps.executeUpdate();
	        if (filas == 0) {
	        	return null;
	        }
	        
	        try(ResultSet rs = ps.getGeneratedKeys()){
	        	if (rs.next()) {
	        		t.setId(rs.getInt(1));
	        	}
	        }
	        return t;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public boolean actualizar(int id, TicketAdjunto t) {
		if (t.getTicket() == null) {
			return false;
		}
	    String sql = "UPDATE ticket_adjuntos SET ticket=?, nombreOriginal=?, rutaArchivo=?, tipoMime=?, tamanio=? WHERE id=?";
	    try (Connection con = Conexion.obtener();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, t.getTicket().getId());
	        ps.setString(2, t.getNombreOriginal());
	        ps.setString(3, t.getRutaArchivo());
	        ps.setString(4, t.getTipoMime());
	        ps.setLong(5, t.getTamanio());
	        ps.setInt(6, id);
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean eliminar(int id) {
	    String sql = "DELETE FROM ticket_adjuntos WHERE id=?";
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
