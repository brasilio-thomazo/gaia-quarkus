package br.dev.optimus.gaia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "apps")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "container")
    private String container;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private int port;
    private String image;
    private int replicas;
    @JdbcTypeCode(value = SqlTypes.JSON)
    private List<String> environments;
    @JdbcTypeCode(value = SqlTypes.JSON)
    private List<HashMap<String, String>> volumes;
    private boolean listening;
    private boolean active;
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private long createdAt;
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private long updatedAt;
    @Column(name = "deleted_at")
    @JsonProperty("deleted_at")
    private long deletedAt;

    public record DTO(String container,
                      String name,
                      int port,
                      String image,
                      int replicas,
                      List<String> environments,
                      List<HashMap<String, String>> volumes,
                      boolean listening,
                      boolean active) {

    }

    public static class Builder {

        private String container;
        private String name;
        private int port;
        private String image;
        private int replicas;
        private List<String> environments;
        private List<HashMap<String, String>> volumes;
        private boolean listening;
        private boolean active;

        public Builder container(String container) {
            this.container = container;
            return this;
        }

        public Builder name(String name) {
            this.name = name.toLowerCase();
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder image(String image) {
            this.image = image.toLowerCase();
            return this;
        }

        public Builder replicas(int replicas) {
            this.replicas = replicas;
            return this;
        }

        public Builder environments(List<String> environments) {
            this.environments = Objects.requireNonNullElse(environments, List.of());
            return this;
        }

        public Builder volumes(List<HashMap<String, String>> volumes) {
            this.volumes = Objects.requireNonNullElse(volumes, List.of());
            return this;
        }

        public Builder listening(boolean listening) {
            this.listening = listening;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public App build() {
            return new App(this);
        }
    }

    public App() {
    }

    public App(Builder builder) {
        this.container = builder.container;
        this.name = builder.name;
        this.port = builder.port;
        this.image = builder.image;
        this.replicas = builder.replicas;
        this.environments = builder.environments;
        this.volumes = builder.volumes;
        this.listening = builder.listening;
        this.active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public List<String> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<String> environments) {
        this.environments = environments;
    }

    public List<HashMap<String, String>> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<HashMap<String, String>> volumes) {
        this.volumes = volumes;
    }

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }
}
