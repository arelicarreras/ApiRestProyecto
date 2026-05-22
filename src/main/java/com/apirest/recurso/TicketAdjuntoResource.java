package com.apirest.recurso;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.apirest.db.TicketAdjuntoDAO;
import com.apirest.modelo.Ticket;
import com.apirest.modelo.TicketAdjunto;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/adjuntos")

@Produces(MediaType.APPLICATION_JSON)

@Consumes(MediaType.APPLICATION_JSON)

public class TicketAdjuntoResource {

    TicketAdjuntoDAO dao =
    new TicketAdjuntoDAO();


    // =====================================
    // LISTAR
    // =====================================

    @GET

    public List<TicketAdjunto> listar() {

        return dao.obtenerTodos();
    }


    // =====================================
    // OBTENER POR ID
    // =====================================

    @GET

    @Path("/{id}")

    public Response obtener(
        @PathParam("id") int id
    ) {

        TicketAdjunto a =
        dao.obtenerPorId(id);

        if (a == null) {

            return Response.status(
                Response.Status.NOT_FOUND
            )

            .entity(
                "{\"mensaje\":\"Adjunto no encontrado\"}"
            )

            .build();
        }

        return Response.ok(a).build();
    }


    // =====================================
    // CREAR
    // =====================================

    @POST

    public Response crear(TicketAdjunto a) {

        TicketAdjunto creado =
        dao.crear(a);

        return Response.status(
            Response.Status.CREATED
        )

        .entity(creado)

        .build();
    }


    // =====================================
    // SUBIR ARCHIVO
    // =====================================

    @POST

    @Path("/subir")

    @Consumes(MediaType.MULTIPART_FORM_DATA)

    public Response subir(

        @FormDataParam("archivo")
        InputStream archivoInput,

        @FormDataParam("archivo")
        FormDataContentDisposition detalle,

        @FormDataParam("ticketId")
        int ticketId

    ){

        try{

            String carpeta =

            System.getProperty("user.home")

            + "/tickets/";


            File dir =
            new File(carpeta);

            if(!dir.exists()){

                dir.mkdirs();
            }


            String nombre =
            detalle.getFileName();

            String ruta =
            carpeta + nombre;


            Files.copy(

                archivoInput,

                new File(ruta).toPath(),

                StandardCopyOption.REPLACE_EXISTING
            );


            Ticket ticket =
            new Ticket();

            ticket.setId(ticketId);


            TicketAdjunto a =
            new TicketAdjunto();

            a.setTicket(ticket);

            a.setNombreOriginal(nombre);

            a.setRutaArchivo(ruta);

            a.setTipoMime("archivo");

            a.setTamanio(
                new File(ruta).length()
            );


            TicketAdjunto creado =
            dao.crear(a);

            return Response.ok(creado)
            .build();

        }catch(Exception e){

            e.printStackTrace();

            return Response.serverError()

                .entity(e.getMessage())

                .build();
        }
    }


    // =====================================
    // DESCARGAR ARCHIVO
    // =====================================

    @GET

    @Path("/descargar/{id}")

    @Produces(MediaType.APPLICATION_OCTET_STREAM)

    public Response descargar(

        @PathParam("id") int id

    ){

        try{

            TicketAdjunto adjunto =
            dao.obtenerPorId(id);

            if(adjunto == null){

                return Response.status(
                    Response.Status.NOT_FOUND
                )

                .entity("Archivo no encontrado")

                .build();
            }


            File archivo =
            new File(
                adjunto.getRutaArchivo()
            );

            if(!archivo.exists()){

                return Response.status(
                    Response.Status.NOT_FOUND
                )

                .entity("Archivo no existe")

                .build();
            }


            return Response.ok(archivo)

                .header(

                    "Content-Disposition",

                    "attachment; filename=\"" +

                    adjunto.getNombreOriginal()

                    + "\""
                )

                .build();

        }catch(Exception e){

            e.printStackTrace();

            return Response.serverError()

                .entity(e.getMessage())

                .build();
        }
    }


    // =====================================
    // ACTUALIZAR
    // =====================================

    @PUT

    @Path("/{id}")

    public Response actualizar(

        @PathParam("id") int id,

        TicketAdjunto a
    ) {

        boolean ok =
        dao.actualizar(id, a);

        if (ok) {

            return Response.ok(

                "{\"mensaje\":\"Adjunto actualizado\"}"

            ).build();
        }

        return Response.status(
            Response.Status.BAD_REQUEST
        )

        .entity(
            "{\"error\":\"No se pudo actualizar\"}"
        )

        .build();
    }


    // =====================================
    // ELIMINAR
    // =====================================

    @DELETE

    @Path("/{id}")

    public Response eliminar(
        @PathParam("id") int id
    ) {

        boolean ok =
        dao.eliminar(id);

        if (ok) {

            return Response.ok(

                "{\"mensaje\":\"Adjunto eliminado\"}"

            ).build();
        }

        return Response.status(
            Response.Status.INTERNAL_SERVER_ERROR
        )

        .entity(
            "{\"error\":\"No se pudo eliminar\"}"
        )

        .build();
    }
}