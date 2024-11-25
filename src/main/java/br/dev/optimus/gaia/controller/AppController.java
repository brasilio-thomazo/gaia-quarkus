package br.dev.optimus.gaia.controller;

import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.dev.optimus.gaia.service.DockerService;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/app")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppController {
    @RestClient
    DockerService dockerService;

    @GET
    public Response index() {
        return null;
    }

    @GET
    @Path("/docker/info")
    public Response info() {
        return Response.ok(dockerService.getInfo()).build();
    }

    @POST
    public Response create(@PathParam("id")UUID id) {
        return null;
    }
}
