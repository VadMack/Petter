package com.vadmack.petter.user;

import lombok.Builder;

@Builder
public record Address(String country, String city, String street, String houseNumber, String metroStation) {
}
