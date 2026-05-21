package com.apirest.recurso;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/test")
public class TestResource {

    @GET
    public String hola() {
        return "Hola Mundo";
    }
}