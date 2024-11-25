package br.dev.optimus.gaia.service;

import br.dev.optimus.gaia.dto.ContainerDTO;
import br.dev.optimus.gaia.request.DockerRequest;
import br.dev.optimus.gaia.response.DockerResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey = "docker")
public interface DockerService {

    @GET
    @Path("/info")
    JsonNode getInfo();

    @POST
    @Path("/container/create")
    DockerResponse.ContainerCreate createContainer(@QueryParam("name") String name, DockerRequest.ContainerCreate request);
}
