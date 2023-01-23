package org.infinispan.search;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchTest {

   @BeforeAll
   public void beforeAll() {
      // assert cache instance injected

      // cleanup and load data

      // init query factory
   }

   @Test
   public void fulltextQueries() {
      // Query

      // Execute

      // Assert Hit Count

      // Assert Books
   }

   @Test
   public void rangedQueries_orderBy() {
      // Query

      // Execute

      // Assert Hit Count

      // Assert Titles
   }

   @Test
   public void nestedQueries_projection() {
      // Query projection

      // Execute

      // Assert Hit Count

      // Assert Titles

      // Assert Years
   }

   @Test
   public void testPagination() {
      // Query pagination

      // Execute

      // Assert Hit Count

      // Assert page 1

      // Assert page 2

      // Assert page 3

   }

}
