package com.vadmack.petter.app.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import static com.vadmack.petter.app.utils.AppUtils.SECURITY_REQUIREMENT_NAME;

@SecurityRequirement(name = SECURITY_REQUIREMENT_NAME)
public interface SecuredRestController {
}
