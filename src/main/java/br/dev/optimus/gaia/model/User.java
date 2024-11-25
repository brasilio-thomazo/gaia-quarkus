package br.dev.optimus.gaia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Group group;
    private String name;
    private String phone;
    @Column(name = "job_title")
    @JsonProperty("job_title")
    private String jobTitle;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
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

    public static class Builder {
        private Group group;
        private String name;
        private String phone;
        private String jobTitle;
        private String email;
        private String username;
        private String password;
        private boolean visible = true;
        private boolean editable = true;
        private boolean locked;

        public User build() {
            return new User(this);
        }

        public Builder group(Group group) {
            this.group = group;
            return this;
        }

        public Builder name(String name) {
            this.name = name.toUpperCase();
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public Builder email(String email) {
            this.email = email.toLowerCase();
            return this;
        }

        public Builder username(String username) {
            this.username = username.toLowerCase();
            return this;
        }

        public Builder password(String password) {
            this.password = BcryptUtil.bcryptHash(password);
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

        public Builder locked(boolean locked) {
            this.locked = locked;
            return this;
        }
    }

    public record DTO(Integer groupId, String name, String phone, String jobTitle, String email, String username, String password,
                      String passwordConfirm, boolean locked) {
    }

    public User() {
    }

    public User(Builder builder) {
        this.group = builder.group;
        this.name = builder.name;
        this.phone = builder.phone;
        this.jobTitle = builder.jobTitle;
        this.email = builder.email;
        this.username = builder.username;
        this.password = builder.password;
        this.visible = builder.visible;
        this.editable = builder.editable;
        this.locked = builder.locked;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BcryptUtil.bcryptHash(password);
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

