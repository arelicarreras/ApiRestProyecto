package com.apirest.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.apirest.modelo.Ticket;
import com.apirest.modelo.Usuario;
import com.apirest.util.EstadoTicket;

public class TicketDAO {
    public boolean crear(Ticket t) {
        String sql = "INSERT INTO ticket (descripcion, departamento, creadoPor, fechaCreacion) VALUES (?, ?, ?, NOW())";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql, new String[] {"codigo"})) {
            ps.setString(1, t.getDescripcion());
            ps.setString(2, t.getDepartamento());
            ps.setInt(3, t.getCreadoPor().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()){
            	if (rs.next()) {
            		t.setCodigo(rs.getString(1));
            	}
            }
            registrarTimeline(
                    t.getCodigo(),
                    EstadoTicket.CREADO,
                    t.getCreadoPor().getNombre(),
                    "Ticket creado"
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean cambiarEstado(String codigo, String nuevoEstado, String actor, String observacion) {
        String estadoActual = obtenerEstadoActual(codigo);
        if (estadoActual == null) {
        	return false;
        }
        if (!esTransicionValida(estadoActual, nuevoEstado)) {
            System.out.println("Transición inválida: " + estadoActual + " → " + nuevoEstado
            );
            return false;
        }
        String sql = "UPDATE ticket SET estado = ?, fechaCierre = CASE WHEN ? = ? THEN NOW() ELSE fechaCierre END WHERE codigo = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setString(2, nuevoEstado);
            ps.setString(3, EstadoTicket.FINALIZADO);
            ps.setString(4, codigo);
            ps.executeUpdate();
            registrarTimeline(codigo, nuevoEstado, actor, observacion
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean esTransicionValida(String estadoActual, String nuevoEstado) {
        switch (estadoActual) {
            case EstadoTicket.CREADO:
                return nuevoEstado.equals(EstadoTicket.ASIGNADO)
                        || nuevoEstado.equals(EstadoTicket.RECHAZADO);

            case EstadoTicket.ASIGNADO:
                return nuevoEstado.equals(EstadoTicket.VALIDACION)
                        || nuevoEstado.equals(EstadoTicket.RECHAZADO)
                        || nuevoEstado.equals(EstadoTicket.DEVUELTO);

            case EstadoTicket.VALIDACION:
                return nuevoEstado.equals(EstadoTicket.FINALIZADO)
                        || nuevoEstado.equals(EstadoTicket.DEVUELTO)
                        || nuevoEstado.equals(EstadoTicket.ASIGNADO);

            case EstadoTicket.DEVUELTO:
                return nuevoEstado.equals(EstadoTicket.ASIGNADO)
                        || nuevoEstado.equals(EstadoTicket.VALIDACION);

            case EstadoTicket.RECHAZADO:
                return nuevoEstado.equals(EstadoTicket.ASIGNADO);

            case EstadoTicket.FINALIZADO:
                return false;

            default:
                return false;
        }
    }
    
    public boolean aceptarTicket(
            String codigo,
            int idUsuario,
            String actor,
            String observacion
    ) {
        String estadoActual = obtenerEstadoActual(codigo);
        if (!EstadoTicket.CREADO.equals(estadoActual)
        	    && !EstadoTicket.ASIGNADO.equals(estadoActual)
        	    && !EstadoTicket.DEVUELTO.equals(estadoActual)
        	    && !EstadoTicket.RECHAZADO.equals(estadoActual)) {

        	    return false;
        	}
        String sql = """
            UPDATE ticket
            SET estado = ?, asignadoA = ? WHERE codigo = ?
            """;

        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, EstadoTicket.ASIGNADO);
            ps.setInt(2, idUsuario);
            ps.setString(3, codigo);
            int filas = ps.executeUpdate();
            
            if (filas == 0) {
            	return false;
            }

            registrarTimeline(codigo, EstadoTicket.ASIGNADO, actor, observacion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    private String obtenerEstadoActual(String codigo) {
        String sql = "SELECT estado FROM ticket WHERE codigo = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("estado");
                } else {
                    System.out.println("Ticket no encontrado: " + codigo);
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void registrarTimeline(String codigoTicket, String estado, String actor, String observacion) {
        String sql = "INSERT INTO timeline_ticket (codigo_ticket, fecha_hora, estado, actor, observacion) VALUES (?, NOW(), ?, ?, ?)";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigoTicket);
            ps.setString(2, estado);
            ps.setString(3, actor);
            ps.setString(4, observacion);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> listar() {
        List<Ticket> lista = new ArrayList<>();
        String sql = """
        		SELECT t.id, t.codigo, t.descripcion, t.departamento, t.estado, t.fechaCreacion, t.fechaCierre,
	    		t.creadoPor, t.asignadoA, u.id AS usuario_id, u.nombre AS usuario_nombre, u.correo AS usuario_correo, 
	    		u.departamento AS dep_usuario, u.rol AS usuario_rol, 
	    		a.id AS asignado_id, a.nombre AS asignado_nombre, a.correo AS asignado_correo, 
	    		a.departamento AS asignado_departamento, a.rol AS asignado_rol
	    		FROM ticket t JOIN usuarios u ON t.creadoPor = u.id
	    		LEFT JOIN usuarios a ON t.asignadoA = a.id ORDER BY t.fechaCreacion ASC
        		""";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getInt("id"));
                t.setCodigo(rs.getString("codigo"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setDepartamento(rs.getString("departamento"));
                t.setEstado(rs.getString("estado"));
                
                Usuario creador = new Usuario();
                creador.setId(rs.getInt("usuario_id"));
                creador.setNombre(rs.getString("usuario_nombre"));
                creador.setCorreo(rs.getString("usuario_correo"));
                creador.setDepartamento(rs.getString("dep_usuario"));
                creador.setRol(rs.getString("usuario_rol"));
                
                t.setCreadoPor(creador);
                if (rs.getObject("asignado_id") != null) {
                    Usuario asignado = new Usuario();

                    asignado.setId(rs.getInt("asignado_id"));
                    asignado.setNombre(rs.getString("asignado_nombre"));
                    asignado.setCorreo(rs.getString("asignado_correo"));
                    asignado.setDepartamento( rs.getString("asignado_departamento"));
                    asignado.setRol(rs.getString("asignado_rol"));

                    t.setAsignadoA(asignado);
                }
                t.setFechaCreacion(
                        rs.getTimestamp("fechaCreacion")
                );
                t.setFechaCierre(
                        rs.getTimestamp("fechaCierre")
                );
                lista.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM ticket WHERE codigo = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}