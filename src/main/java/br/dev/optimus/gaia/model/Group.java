package br.dev.optimus.gaia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<String> permissions;
    private boolean visible;
    private boolean editable;
    private boolean locked;
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private long createdAt;
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private long updatedAt;
    @Column(name = "deleted_at")
    @JsonProperty("deleted_at")
    private long deletedAt;

    public record DTO(String name, Set<String> permissions, boolean locked) {
    }

    public static class Builder {
        private String name;
        private Set<String> permissions;
        private boolean visible = true;
        private boolean editable = true;
        private boolean locked;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder permissions(Set<String> permissions) {
            this.permissions = permissions;
            return this;
        }

        public Builder permissions(String... permissions) {
            this.permissions = Set.of(permissions);
            return this;
        }

        public Builder locked(boolean locked) {
            this.locked = locked;
            return this;
        }

        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder editable(boolean editable) {
            this.editable = editable;
            return this;
        }

        public Group build() {
            return new Group(this);
        }
    }

    public Group() {
    }

    public Group(Builder builder) {
        this.name = builder.name;
        this.permissions = builder.permissions;
        this.locked = builder.locked;
        this.visible = builder.visible;
        this.editable = builder.editable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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
