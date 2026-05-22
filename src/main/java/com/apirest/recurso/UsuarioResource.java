package com.apirest.recurso;
import java.util.List;
import com.apirest.db.UsuarioDAO;
import com.apirest.modelo.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    UsuarioDAO dao = new UsuarioDAO();

    @GET
    public List<Usuario> obtenerTodos() {
        return dao.obtenerTodos();
    }
    @GET
    @Path("/test")

    public String test(){

    	return "FUNCIONA";
    }
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") int id) {
        Usuario u = dao.obtenerPorId(id);
        if (u == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensaje\":\"Usuario no encontrado\"}")
                    .build();
        }
        return Response.ok(u).build();
    }

    @POST
    public Response crear(Usuario u) {
        Usuario creado = dao.crear(u);
        return Response.status(Response.Status.CREATED)
                .entity(creado)
                .build();
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response login(Usuario U){

    	Usuario usuario = dao.login(
    			U.getCorreo(),
    			U.getPassword()
    		);
    	if(usuario != null){
    		return Response.ok(usuario).build();
    	}
    	return Response.status(Response.Status.UNAUTHORIZED)
    			.entity("{\"error\":\"Credenciales incorrectas\"}")
    			.build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") int id, Usuario u) {
        boolean ok = dao.actualizar(id, u);
        if (ok) {
            return Response.ok("{\"mensaje\":\"Usuario actualizado\"}").build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"No se pudo actualizar\"}")
                .build();
    }
    @PUT
    @Path("/password/{correo}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response cambiarPassword(@PathParam("correo")String correo,String nuevaPassword){
        boolean ok = dao.cambiarPassword(correo, nuevaPassword);
        if(ok){return Response.ok().build();

        }else{
            return Response.status(500)
            .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        boolean ok = dao.eliminar(id);
        if (ok) {
            return Response.ok("{\"mensaje\":\"Usuario eliminado\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Error al eliminar\"}")
                .build();
    }
}