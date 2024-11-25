package br.dev.optimus.gaia.model;

import java.util.Set;

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
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String phone;
    private String email;
    private String document;
    private String address;
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<Contact> contacts;
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

    public static class Builder {

        private String name;
        private String phone;
        private String email;
        private String document;
        private String address;
        private Set<Contact> contacts;
        private boolean active;

        public Builder name(String name) {
            this.name = name.toUpperCase();
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email.toLowerCase();
            return this;
        }

        public Builder document(String document) {
            this.document = document;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder contacts(Set<Contact> contacts) {
            this.contacts = contacts;
            return this;
        }

        public Builder contacts(Contact... contacts) {
            this.contacts = Set.of(contacts);
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }

    }

    public static class Contact {

        private String name;
        private String phone;
        private String email;
        @JsonProperty("job_title")
        private String jobTitle;

        public Contact() {
        }

        public Contact(String name, String phone, String email, String jobTitle) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.jobTitle = jobTitle;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }
    }

    public record DTO(
            String name,
            String phone,
            String email,
            String document,
            String address,
            Set<Contact> contacts,
            boolean active) {

    }

    public Customer() {
    }

    public Customer(Builder builder) {
        this.name = builder.name;
        this.phone = builder.phone;
        this.email = builder.email;
        this.document = builder.document;
        this.address = builder.address;
        this.contacts = builder.contacts;
        this.active = builder.active;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toUpperCase();
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
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
