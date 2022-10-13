package com.vadmack.petter.app.utils;

import com.vadmack.petter.app.exception.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class AppUtils {

  public static <T> T checkFound(Optional<T> optional, String msg) {
    if (optional.isEmpty()) {
      throw new NotFoundException(msg);
    }
    return optional.get();
  }
}
