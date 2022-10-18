package com.vadmack.petter.app.annotation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.vadmack.petter.app.utils.AppUtils.SECURITY_REQUIREMENT_NAME;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SecurityRequirement(name = SECURITY_REQUIREMENT_NAME)
public @interface Secured {
}
