package com.apirest.recurso;
import java.util.List;
import com.apirest.db.TimelineDAO;
import com.apirest.modelo.TicketTimeline;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimelineResource {
    TimelineDAO dao = new TimelineDAO();

    @GET
    @Path("/{codigo}")
    public List<TicketTimeline> obtenerPorTicket(@PathParam("codigo") String codigo) {
        return dao.obtenerPorTicket(codigo);
    }

    @DELETE
    @Path("/{codigo}")
    public Response eliminar(@PathParam("codigo") String codigo) {
        boolean ok = dao.eliminarPorTicket(codigo);
        if (ok) {
            return Response.ok("{\"mensaje\":\"Timeline eliminado\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
              .entity("{\"error\":\"No se pudo eliminar\"}")
                .build();
    }
}