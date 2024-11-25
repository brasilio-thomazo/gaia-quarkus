package br.dev.optimus.gaia.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DockerResponse {
    public record ContainerCreate(
            @JsonProperty("Id") String id,
            @JsonProperty("Warnings") String[] warnings) {
    }
}
