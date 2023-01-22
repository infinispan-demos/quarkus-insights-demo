package org.infinispan.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.infinispan.search.ModelGenerator.EFFECTIVE;
import static org.infinispan.search.ModelGenerator.MEMORY;
import static org.infinispan.search.ModelGenerator.MICROSERVICES;
import static org.infinispan.search.ModelGenerator.MULTIPROCESSOR;
import static org.infinispan.search.ModelGenerator.PERFORMANCE;
import static org.infinispan.search.ModelGenerator.PRACTICE;
import static org.infinispan.search.ModelGenerator.UTIL;

import javax.inject.Inject;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.dsl.QueryResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchTest {

   @Inject
   @Remote("books")
   RemoteCache<String, Book> cache;

   private QueryFactory queryFactory;

   @Test
   public void fulltextQueries() {
      Query<Book> query = queryFactory.create("from insights.book b where b.description : :description");
      query.setParameter("description", "Java");

      QueryResult<Book> result = query.execute();
      assertThat(result.hitCount()).hasValue(5);
      assertThat(result.list()).extracting("title").containsExactlyInAnyOrder(EFFECTIVE, PRACTICE, UTIL, MEMORY, PERFORMANCE);
   }

   @Test
   public void rangedQueries_orderBy() {
      Query<Book> query = queryFactory.create("from insights.book b where b.price < 70.00 order by b.yearOfPublication desc");

      QueryResult<Book> result = query.execute();
      assertThat(result.hitCount()).hasValue(4);
      assertThat(result.list()).extracting("title").containsExactly(MICROSERVICES, PERFORMANCE, EFFECTIVE, UTIL);
   }

   @Test
   public void nestedQueries_projection() {
      Query<Object[]> query = queryFactory.create("select b.title, b.yearOfPublication from insights.book b where b.reviews.content : 'nice'");
      QueryResult<Object[]> result = query.execute();

      assertThat(result.list()).map(objects -> objects[0]).containsExactlyInAnyOrder(PRACTICE, UTIL, MEMORY, MULTIPROCESSOR);
      assertThat(result.list()).map(objects -> objects[1]).containsExactlyInAnyOrder(2006, 2015, 2022, 2020);
   }

   @Test
   public void testPagination() {
      QueryFactory queryFactory = Search.getQueryFactory(cache);
      Query<Book> query = queryFactory.create("from insights.book b order by title");
      QueryResult<Book> result;
      query.startOffset(0);

      // page 1
      query.maxResults(3);
      result = query.execute();
      assertThat(result.hitCount()).hasValue(7);
      assertThat(result.list()).extracting("title").containsExactly(EFFECTIVE, PRACTICE, MEMORY);

      // page 2
      query.startOffset(3);
      result = query.execute();
      assertThat(result.hitCount()).hasValue(7);
      assertThat(result.list()).extracting("title").containsExactly(PERFORMANCE, UTIL, MICROSERVICES);

      // page3
      query.startOffset(6);
      result = query.execute();
      assertThat(result.hitCount()).hasValue(7);
      assertThat(result.list()).extracting("title").containsExactly(MULTIPROCESSOR);
   }

   @BeforeAll
   public void beforeAll() {
      assertThat(cache).isNotNull(); // instance is injected
      cache.clear();
      cache.putAll(ModelGenerator.generateBooks());

      queryFactory = Search.getQueryFactory(cache);
   }
}
