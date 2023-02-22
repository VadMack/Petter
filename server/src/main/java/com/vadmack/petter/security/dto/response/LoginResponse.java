package com.vadmack.petter.security.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vadmack.petter.user.dto.UserGetDto;

public record LoginResponse(UserGetDto user, String refreshToken, @JsonIgnore String jwtToken) {
}
