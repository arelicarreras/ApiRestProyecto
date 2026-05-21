package com.apirest.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.apirest.modelo.TicketTimeline;
import com.apirest.modelo.Ticket;
import com.apirest.modelo.Usuario;

public class TimelineDAO {
    public void registrarEvento(String codigoTicket, String estado, String actor, String observacion) {

        String sql = "INSERT INTO timeline_ticket (codigo_ticket, fecha_hora, estado, actor, observacion) "
                   + "VALUES (?, NOW(), ?, ?, ?)";

        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoTicket);
            ps.setString(2, estado);
            ps.setString(3, actor);
            ps.setString(4, observacion);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TicketTimeline> obtenerPorTicket(String codigoTicket) {
        List<TicketTimeline> lista = new ArrayList<>();
        String sql = "SELECT id, codigo_ticket, fecha_hora, estado, actor, observacion FROM timeline_ticket WHERE codigo_ticket = ? "
                   + "ORDER BY fecha_hora ASC";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigoTicket);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TicketTimeline t = new TicketTimeline();
                    t.setId(rs.getInt("id"));
                    Ticket ticket = new Ticket();
                    ticket.setCodigo(rs.getString("codigo_ticket"));
                    t.setTicket(ticket);
                    Usuario actor = new Usuario();
                    actor.setNombre(rs.getString("actor"));
                    t.setActor(actor);
                    t.setEstado(rs.getString("estado"));
                    t.setObservacion(rs.getString("observacion"));
                    t.setFechaEvento(rs.getTimestamp("fecha_hora"));
                    lista.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminarPorTicket(String codigoTicket) {
        String sql = "DELETE FROM timeline_ticket WHERE codigo_ticket = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigoTicket);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM timeline_ticket WHERE id = ?";
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
