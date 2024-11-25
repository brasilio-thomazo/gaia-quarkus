package br.dev.optimus.gaia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

public class ContainerDTO {
    @JsonProperty("Image")
    private String image;
    @JsonProperty("Env")
    private List<String> environments;
    @JsonProperty("Cmd")
    private List<String> cmd;
    @JsonProperty("Volumes")
    private List<HashMap<String, String>> volumes;
    @JsonProperty("NetworkingConfig")
    private NetworkingConfig networkingConfig;

    public static class NetworkingConfig {
        @JsonProperty("EndpointsConfig")
        private HashMap<String, Object> endpoints;

        public NetworkingConfig() {
        }

        public NetworkingConfig(HashMap<String, Object> endpoints) {
            this.endpoints = endpoints;
        }

        public HashMap<String, Object> getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(HashMap<String, Object> endpoints) {
            this.endpoints = endpoints;
        }
    }

    public static class Builder {
        private String image;
        private List<String> environments;
        private List<String> cmd;
        private List<HashMap<String, String>> volumes;
        private NetworkingConfig networkingConfig;

        public Builder image(String image) {
            this.image = image.toLowerCase();
            return this;
        }

        public Builder environments(List<String> environments) {
            this.environments = environments;
            return this;
        }

        public Builder cmd(List<String> cmd) {
            this.cmd = cmd;
            return this;
        }

        public Builder volumes(List<HashMap<String, String>> volumes) {
            this.volumes = volumes;
            return this;
        }

        public Builder networkingConfig(NetworkingConfig networkingConfig) {
            this.networkingConfig = networkingConfig;
            return this;
        }
    }

    public ContainerDTO() {
    }

    public ContainerDTO(Builder builder) {
        this.image = builder.image;
        this.environments = builder.environments;
        this.cmd = builder.cmd;
        this.volumes = builder.volumes;
        this.networkingConfig = builder.networkingConfig;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getImage() {
        return image;
    }

    public ContainerDTO setImage(String image) {
        this.image = image.toLowerCase();
        return this;
    }

    public List<String> getEnvironments() {
        return environments;
    }

    public ContainerDTO setEnvironments(List<String> environments) {
        this.environments = environments;
        return this;
    }

    public List<String> getCmd() {
        return cmd;
    }

    public ContainerDTO setCmd(List<String> cmd) {
        this.cmd = cmd;
        return this;
    }

    public List<HashMap<String, String>> getVolumes() {
        return volumes;
    }

    public ContainerDTO setVolumes(List<HashMap<String, String>> volumes) {
        this.volumes = volumes;
        return this;
    }

    public NetworkingConfig getNetworkingConfig() {
        return networkingConfig;
    }

    public ContainerDTO setNetworkingConfig(NetworkingConfig networkingConfig) {
        this.networkingConfig = networkingConfig;
        return this;
    }
}
