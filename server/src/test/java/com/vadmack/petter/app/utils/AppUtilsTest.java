package com.vadmack.petter.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadmack.petter.app.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class AppUtilsTest {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  private static class TestItem {
    int field1;
    Integer field2;
    List<Integer> field3;
  }

  @Test
  void checkFoundSuccess() {
    TestItem testItem = new TestItem(1, 2, List.of(1, 2, 3));
    assertEquals(testItem, AppUtils.checkFound(Optional.of(testItem), "Error msg"));
  }

  @Test
  void checkFoundException() {
    String expectedMsg = "Error msg";
    NotFoundException thrown = assertThrows(
            NotFoundException.class,
            () -> AppUtils.checkFound(Optional.empty(), expectedMsg),
            "Expected AppUtils.checkFound() to throw, but it didn't"
    );
    assertEquals(expectedMsg, thrown.getMessage());
  }

  @Test
  void objectToJSON() throws Exception {
    TestItem testItem = new TestItem(1, 2, List.of(1, 2, 3));
    assertEquals(testItem, new ObjectMapper().readValue(AppUtils.objectToJSON(testItem), TestItem.class));
  }
}