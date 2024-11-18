package hu.telecom.xmltojava.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private UUID id;
    private String name;
    private String username;
    private String address;

    @JsonProperty("roles")
    private Roles roles;

    public static class Roles {
        private Set<String> role = new HashSet<>();

        public Set<String> getRole() {
            return role;
        }

        public void setRole(Set<String> role) {
            this.role = role;
        }
    }

    public User() {}

    public User(UUID id, String name, String username, String address, Roles roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", roles=" + roles.getRole() +
                '}';
    }
}
