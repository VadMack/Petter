package com.vadmack.petter.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadmack.petter.app.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class AppUtilsTest {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  private static class TestItem {
    private int field1;
    private Integer field2;
    private List<Integer> field3;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  private static class TestItemNested {
    private TestItem testItem;
    private Map<String, TestItem> map;
    private Set<TestItem> set;
  }

  private record TestItemRecord(int field1, Integer field2) {

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

  @Test
  void objectToJSONNested() throws Exception {
    TestItemNested testItem = new TestItemNested(new TestItem(1, 2, List.of(1, 2, 3)),
            Map.of("key1", new TestItem(1, 2, List.of(1, 2, 3))),
            Collections.singleton(new TestItem(1, 2, List.of(1, 2, 3))));
    assertEquals(testItem, new ObjectMapper().readValue(AppUtils.objectToJSON(testItem), TestItemNested.class));
  }

  @Test
  void objectToJSONRecord() throws Exception {
    TestItemRecord testItem = new TestItemRecord(1, null);
    assertEquals(testItem, new ObjectMapper().readValue(AppUtils.objectToJSON(testItem), TestItemRecord.class));
  }
}