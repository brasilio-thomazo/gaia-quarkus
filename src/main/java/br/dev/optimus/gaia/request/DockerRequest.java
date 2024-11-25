package br.dev.optimus.gaia.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

public class DockerRequest {
    public record ContainerCreate(
            @JsonProperty("Image") String image,
            @JsonProperty("Cmd") List<String> cmd,
            @JsonProperty("Env") List<String> env,
            @JsonProperty("Volumes") List<HashMap<String, String>> volumes,
            @JsonProperty("NetworkingConfig") HashMap<String, Object> endpoints
    ){}
}
