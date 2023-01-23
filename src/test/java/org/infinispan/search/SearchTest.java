package org.infinispan.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.infinispan.search.generator.ModelGenerator.EFFECTIVE;
import static org.infinispan.search.generator.ModelGenerator.MEMORY;
import static org.infinispan.search.generator.ModelGenerator.MICROSERVICES;
import static org.infinispan.search.generator.ModelGenerator.MULTIPROCESSOR;
import static org.infinispan.search.generator.ModelGenerator.PERFORMANCE;
import static org.infinispan.search.generator.ModelGenerator.PRACTICE;
import static org.infinispan.search.generator.ModelGenerator.UTIL;

import javax.inject.Inject;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.dsl.QueryResult;
import org.infinispan.search.generator.ModelGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.quarkus.infinispan.client.Remote;
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
