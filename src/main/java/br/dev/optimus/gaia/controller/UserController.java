package br.dev.optimus.gaia.controller;

import br.dev.optimus.gaia.model.User;
import br.dev.optimus.gaia.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GET
    public Response index() {
        return Response.ok(repository.list()).build();
    }

    @GET
    @Path("{id}")
    public Response show(@PathParam("id") Long id) {
        return Response.ok(repository.get(id)).build();
    }

    @POST
    @Transactional
    public Response create(User.DTO dto) {
        repository.create(dto);
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, User.DTO dto) {
        return Response.ok(repository.update(id, dto)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        repository.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("{id}/restore")
    @Transactional
    public Response restore(@PathParam("id") Long id) {
        return Response.ok(repository.restore(id)).build();
    }
}
