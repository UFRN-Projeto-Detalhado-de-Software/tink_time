package com.eliasfs06.tinktime.model.dto;

import com.eliasfs06.tinktime.model.Person;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.UserRole;
import com.eliasfs06.tinktime.model.enums.UnidadeFederacao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class RegisterDTO {
    @NotBlank(message = "{username.not.blank}")
    private String username;
    @NotBlank(message = "{password.not.blank}")
    private String password;
    @NotBlank(message = "{name.not.blank}")
    private String name;
    private Date birthDate;
    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    private String email;
    @NotNull
    private UserRole userRole;
    private UnidadeFederacao ufResidencia;

    public Person toPerson(){
        Person person = new Person();
        person.setName(this.getName());
        person.setBirthDate(this.getBirthDate());
        person.setEmail(this.getEmail());
        person.setUfResidencia(ufResidencia);
        return person;
    }

    public User toUser(){
        User user = new User();
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword());
        user.setUserRole(this.getUserRole());
        return user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UnidadeFederacao getUfResidencia() {
        return ufResidencia;
    }

    public void setUfResidencia(UnidadeFederacao ufResidencia) {
        this.ufResidencia = ufResidencia;
    }
}
