package br.dev.optimus.gaia.controller;

import br.dev.optimus.gaia.model.Group;
import br.dev.optimus.gaia.repository.GroupRepository;
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

@Path("group")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroupController {

    private final GroupRepository repository;

    public GroupController(GroupRepository repository) {
        this.repository = repository;
    }

    @GET
    public Response index() {
        return Response.ok(repository.list()).build();
    }

    @GET
    @Path("{id}")
    public Response show(@PathParam("id") Integer id) {
        return Response.ok(repository.get(id)).build();
    }

    @POST
    @Transactional
    public Response create(Group.DTO dto) {
        return Response.status(Response.Status.CREATED).entity(repository.create(dto)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, Group.DTO dto) {
        return Response.ok(repository.update(id, dto)).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
        repository.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("{id}/restore")
    @Transactional
    public Response restore(@PathParam("id") Integer id) {
        return Response.ok(repository.restore(id)).build();
    }
}
