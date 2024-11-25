package br.dev.optimus.gaia.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContainerCreateResponse(
        @JsonProperty("Id") String id,
        @JsonProperty("Warnings") String[] warnings) {
}
