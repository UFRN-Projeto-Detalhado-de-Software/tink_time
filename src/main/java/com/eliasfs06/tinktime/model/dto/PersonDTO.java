package com.eliasfs06.tinktime.model.dto;

import com.eliasfs06.tinktime.model.Person;

public class PersonDTO {

    private Long id;

    private String name;

    private String birthDate;

    private String email;

    public PersonDTO() {
    }

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        if (person.getBirthDate() != null) this.birthDate = person.getBirthDate().toString();
        this.email = person.getEmail();
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
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
