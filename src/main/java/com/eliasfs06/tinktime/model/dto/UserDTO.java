package com.eliasfs06.tinktime.model.dto;

import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.UserRole;

import java.text.ParseException;

public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String role;

    private PersonDTO person;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        if (user.getUserRole() != null) this.role = user.getUserRole().toString();
        if (user.getPerson() != null) this.person = new PersonDTO(user.getPerson());
    }

    public User toUser() throws ParseException {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setUserRole(UserRole.valueOf(this.role));
        user.setPerson(this.person.toPerson());
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public UserDTO getDTO() {
        return this;
    }
}
