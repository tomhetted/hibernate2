package org.example.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingAttributeConverter implements AttributeConverter<FilmEntity.FilmRating, String> {

    @Override
    public String convertToDatabaseColumn(FilmEntity.FilmRating filmRating) {
        return filmRating != null ? filmRating.getValue() : null;
    }

    @Override
    public FilmEntity.FilmRating convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        for (FilmEntity.FilmRating rating : FilmEntity.FilmRating.values()) {
            if (rating.getValue().equals(value)) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
