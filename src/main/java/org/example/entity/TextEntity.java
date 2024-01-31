package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "film_text", schema = "hibernate2")
@Getter
@Setter
public class TextEntity {
    @Id
    @Column(name = "film_id")
    private Short id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    @Type(type = "text")
    private String description;

    @MapsId
    @OneToOne
    @JoinColumn(name = "film_id")
    private FilmEntity film;

}
