package com.apirest.recurso;

import java.util.List;

import com.apirest.db.TicketDAO;
import com.apirest.modelo.Ticket;
import com.apirest.util.EstadoTicket;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketResource {

    TicketDAO dao = new TicketDAO();

    @GET
    public List<Ticket> listar() {
        return dao.listar();
    }

    @GET
    @Path("/{codigo}")
    public Response obtener(@PathParam("codigo") String codigo) {
        List<Ticket> lista = dao.listar();
        for (Ticket t : lista) {
            if (t.getCodigo().equalsIgnoreCase(codigo)) {
                return Response.ok(t).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"mensaje\":\"Ticket no encontrado\"}")
                .build();
    }

    @POST
    public Response crear(Ticket t) {
        if (t.getCreadoPor() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Se requiere creadoPor\"}")
                    .build();
        }
        boolean ok = dao.crear(t);
        if (ok) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Ticket creado correctamente\", \"codigo\":\"" + t.getCodigo() + "\"}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"No se pudo crear el ticket\"}")
                .build();
    }

    
    @PUT
    @Path("/{codigo}/aceptar")
    public Response aceptar(
            @PathParam("codigo") String codigo,
            Ticket t) {

        if (t.getAsignadoA() == null || t.getCreadoPor() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Se requiere asignadoA (id) y creadoPor (nombre del técnico)\"}")
                    .build();
        }

        boolean ok = dao.aceptarTicket(
                codigo,
                t.getAsignadoA().getId(),
                t.getCreadoPor().getNombre(),
                "Ticket aceptado y autoasignado"
        );

        return respuesta(ok, "aceptado");
    }

    
    @PUT
    @Path("/{codigo}/rechazar")
    public Response rechazar(
            @PathParam("codigo") String codigo,
            Ticket t) {

        if (t.getCreadoPor() == null || t.getDescripcion() == null || t.getDescripcion().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Se requiere el actor (creadoPor.nombre) y una observación (descripcion)\"}")
                    .build();
        }

        boolean ok = dao.cambiarEstado(
                codigo,
                EstadoTicket.RECHAZADO,
                t.getCreadoPor().getNombre(),
                t.getDescripcion()
        );

        return respuesta(ok, "rechazado");
    }

    
    @PUT
    @Path("/{codigo}/validacion")
    public Response validacion(
            @PathParam("codigo") String codigo,
            Ticket t) {

        if (t.getCreadoPor() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Se requiere el actor (creadoPor.nombre)\"}")
                    .build();
        }

        boolean ok = dao.cambiarEstado(
                codigo,
                EstadoTicket.VALIDACION,
                t.getCreadoPor().getNombre(),
                t.getDescripcion() != null ? t.getDescripcion() : "Enviado a validación"
        );

        return respuesta(ok, "enviado a validación");
    }

    
    @PUT
    @Path("/{codigo}/devolver")
    public Response devolver(
            @PathParam("codigo") String codigo,
            Ticket t) {

        if (t.getCreadoPor() == null || t.getDescripcion() == null || t.getDescripcion().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Se requiere el actor (creadoPor.nombre) y una observación (descripcion)\"}")
                    .build();
        }

        boolean ok = dao.cambiarEstado(
                codigo,
                EstadoTicket.DEVUELTO,
                t.getCreadoPor().getNombre(),
                t.getDescripcion()
        );

        return respuesta(ok, "devuelto");
    }

    
    @PUT
    @Path("/{codigo}/finalizar")
    public Response finalizar(
            @PathParam("codigo") String codigo,
            Ticket t) {

        if (t.getCreadoPor() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Se requiere el actor (creadoPor.nombre)\"}")
                    .build();
        }

        boolean ok = dao.cambiarEstado(
                codigo,
                EstadoTicket.FINALIZADO,
                t.getCreadoPor().getNombre(),
                t.getDescripcion() != null ? t.getDescripcion() : "Ticket finalizado"
        );

        return respuesta(ok, "finalizado");
    }

    @DELETE
    @Path("/{codigo}")
    public Response eliminar(@PathParam("codigo") String codigo) {
        boolean ok = dao.eliminar(codigo);
        if (ok) {
            return Response.ok("{\"mensaje\":\"Ticket eliminado correctamente\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"No se pudo eliminar\"}")
                .build();
    }

    private Response respuesta(boolean ok, String accion) {
        if (ok) {
            return Response.ok("{\"mensaje\":\"Ticket " + accion + "\"}").build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No se pudo procesar. Verifica el estado actual del ticket o los datos enviados\"}")
                .build();
    }
}