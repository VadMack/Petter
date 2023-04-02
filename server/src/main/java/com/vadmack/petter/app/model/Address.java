package com.vadmack.petter.app.model;

import lombok.Builder;

@Builder
public record Address(String country, String city, String street, String houseNumber, String metroStation) {
}
