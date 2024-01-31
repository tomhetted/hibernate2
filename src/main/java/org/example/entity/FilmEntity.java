package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Entity
@Table(name = "film", schema = "hibernate2")
@Data
public class FilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @Type(type = "text")
    private String description;

    @Column(name = "release_year", columnDefinition = "year")
    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity language;

    @ManyToOne
    @JoinColumn(name = "original_language_id", nullable = false)
    private LanguageEntity originalLanguage;

    @Column(name = "rental_duration", nullable = false)
    private Byte rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Short length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal replacementCost;

    @Convert(converter = RatingAttributeConverter.class)
    @Column(name = "rating", columnDefinition = "ENUM('G','PG','PG-13','R','NC-17') default 'G'")
    private FilmRating rating;

    @Column(name = "special_features", columnDefinition = "set('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes')")
    private String specialFeatures;
    //private Set<SpecialFeature> specialFeatures;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "film_actor", joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actor_id"))
    private Set<ActorEntity> actors;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "film_category", joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private Set<CategoryEntity> categories;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    public Set<SpecialFeature> getSpecialFeature() {
        if (isNull(specialFeatures) || specialFeatures.isEmpty()) {
            return null;
        }
        Set<SpecialFeature> result = new HashSet<>();
        String[] features = specialFeatures.split(",");
        for (String feature : features) {
            result.add(SpecialFeature.getFeatureByValue(feature));
        }
        result.remove(null);
        return result;
    }

    public void setSpecialFeatures(Set<SpecialFeature> features) {
        if (isNull(features)) {
            specialFeatures = null;
        }
        specialFeatures = features.stream().map(SpecialFeature::getValue).collect(Collectors.joining(","));
    }

    public enum FilmRating {
        G("G"),
        PG("PG"),
        PG_13("PG-13"),
        R("R"),
        NC_17("NC-17");

        private String value;

        FilmRating(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum SpecialFeature {
        TRAILERS("Trailers"),
        COMMENTARIES("Commentaries"),
        DELETED_SCENES("Deleted Scenes"),
        BEHIND_THE_SCENES("Behind the Scenes");
        private String value;

        SpecialFeature(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static SpecialFeature getFeatureByValue(String value) {
            if (isNull(value) || value.isEmpty()) {
                return null;
            }
            SpecialFeature[] specialFeatures = SpecialFeature.values();
            for (SpecialFeature specialFeature : specialFeatures) {
                if (specialFeature.value.equals(value)) {
                    return specialFeature;
                }
            }
            return null;
        }
    }

}
