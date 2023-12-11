package com.eliasfs06.tinktime.model;

import com.eliasfs06.tinktime.model.enums.UnidadeFederacao;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    private String email;

    @Enumerated(EnumType.STRING)
    private UnidadeFederacao ufResidencia;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public UnidadeFederacao getUfResidencia() {
        return ufResidencia;
    }

    public void setUfResidencia(UnidadeFederacao ufResidencia) {
        this.ufResidencia = ufResidencia;
    }
}
