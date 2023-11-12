package com.eliasfs06.tinktime.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Empregado extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aboutMe;

    @ElementCollection
    @CollectionTable(name = "styles", joinColumns = @JoinColumn(name = "artist_id"))
    @Enumerated(EnumType.STRING)
    private List<TattoStyle> styles;

    @OneToOne
    private User user;

    @OneToOne
    private Agenda agenda;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public List<TattoStyle> getStyles() {
        return styles;
    }

    public void setStyles(List<TattoStyle> styles) {
        this.styles = styles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
