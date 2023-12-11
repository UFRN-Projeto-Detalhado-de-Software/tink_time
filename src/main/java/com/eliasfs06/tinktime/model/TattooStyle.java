package com.eliasfs06.tinktime.model;

import java.util.Arrays;
import java.util.List;

public enum TattooStyle {
    TRIBAL("Tribal"),
    REALISM("Realism"),
    NEW_SCHOOL("New School"),
    OLD_SCHOOL("Old School"),
    NEO_TRADITIONAL("Neo-Traditional"),
    WATERCOLOR("Watercolor"),
    GEOMETRIC("Geometric"),
    BLACKWORK("Blackwork"),
    MINIMALIST("Minimalist"),
    JAPANESE("Japanese"),
    CHICANO("Chicano"),
    BIOMECHANICAL("Biomechanical"),
    ABSTRACT("Abstract"),
    SCRIPT("Script"),
    MAORI("Maori"),
    POLYNESIAN("Polynesian"),
    CELTIC("Celtic"),
    CHINESE_TRADITIONAL("Chinese Traditional"),
    DOTWORK("Dotwork"),
    HORROR("Horror");

    private final String description;

    TattooStyle(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<TattooStyle> getAllStyles() {
        return Arrays.asList(TattooStyle.values());
    }
}
