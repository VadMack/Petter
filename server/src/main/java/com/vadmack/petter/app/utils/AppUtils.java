package com.vadmack.petter.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vadmack.petter.app.exception.NotFoundException;
import com.vadmack.petter.app.exception.ServerSideException;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class AppUtils {

  public static final String SECURITY_REQUIREMENT_NAME = "bearerAuth";

  private static final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

  public static <T> T checkFound(Optional<T> optional, String msg) {
    if (optional.isEmpty()) {
      throw new NotFoundException(msg);
    }
    return optional.get();
  }

  public static String objectToJSON(Object object) {
    try {
      return ow.writeValueAsString(object);
    } catch (JsonProcessingException ex) {
      throw new ServerSideException("An error occurred during object serialization: " + ex.getMessage());
    }
  }
}
