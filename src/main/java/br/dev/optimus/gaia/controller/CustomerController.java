package br.dev.optimus.gaia.controller;

import br.dev.optimus.gaia.model.Customer;
import br.dev.optimus.gaia.repository.CustomerRepository;
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

@Path("customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
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
    public Response create(Customer.DTO dto) {
        return Response.status(Response.Status.CREATED).entity(repository.create(dto)).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Customer.DTO dto) {
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
