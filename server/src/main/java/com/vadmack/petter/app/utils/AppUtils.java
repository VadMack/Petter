package com.vadmack.petter.app.utils;

import com.vadmack.petter.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppUtils {
  public static <T> T checkFound(Optional<T> optional, String msg) {
    if (optional.isEmpty()) {
      throw new NotFoundException(msg);
    }
    return optional.get();
  }
}
