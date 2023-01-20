package org.infinispan.search;

import static org.infinispan.search.ModelGenerator.EFFECTIVE;
import static org.infinispan.search.ModelGenerator.MEMORY;
import static org.infinispan.search.ModelGenerator.MICROSERVICES;
import static org.infinispan.search.ModelGenerator.MULTIPROCESSOR;
import static org.infinispan.search.ModelGenerator.PERFORMANCE;
import static org.infinispan.search.ModelGenerator.PRACTICE;
import static org.infinispan.search.ModelGenerator.UTIL;
import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.commons.util.CloseableIterator;
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
public class IndexedQueriesTest {

   @Inject
   @Remote("books")
   RemoteCache<String, Book> cache;

   @BeforeAll
   public void beforeAll() {
      assertThat(cache).isNotNull(); // instance is injected
      cache.clear();
      cache.putAll(ModelGenerator.generateBooks());
   }

   @Test
   public void testLowercaseNormalizedField() {
      QueryFactory queryFactory = Search.getQueryFactory(cache);
      Query<Book> query = queryFactory.create("from insights.book b where b.title = :title");
      QueryResult<Book> result;

      query.setParameter("title", PERFORMANCE);
      result = query.execute();
      assertThat(result.hitCount()).hasValue(1);
      assertThat(result.list()).extracting("title").containsExactlyInAnyOrder(PERFORMANCE);

      query.setParameter("title", "javA pERformANCE: In-DepTH advice FOR Tuning and Programming Java 8, 11, and Beyond");
      result = query.execute();
      assertThat(result.hitCount()).hasValue(1);
      assertThat(result.list()).extracting("title").containsExactlyInAnyOrder(PERFORMANCE);
   }

   @Test
   public void testAnalysis() {
      QueryFactory queryFactory = Search.getQueryFactory(cache);
      Query<Book> query = queryFactory.create("from insights.book b where b.description : :description");
      QueryResult<Book> result;

      query.setParameter("description", "Java");
      result = query.execute();
      assertThat(result.hitCount()).hasValue(5);
      assertThat(result.list()).extracting("title").containsExactlyInAnyOrder(EFFECTIVE, PRACTICE, UTIL, MEMORY, PERFORMANCE);

      query.setParameter("description", "bOOk"); // the parameter is also normalized
      result = query.execute();
      assertThat(result.hitCount()).hasValue(3);
      assertThat(result.list()).extracting("title").containsExactlyInAnyOrder(UTIL, PERFORMANCE, MICROSERVICES);
   }

   @Test
   public void testRangedQueryOnNumericField_decimalField_andOrderBy() {
      QueryFactory queryFactory = Search.getQueryFactory(cache);
      Query<Book> query;
      QueryResult<Book> result;

      query = queryFactory.create("from insights.book b where b.yearOfPublication <= 2017 or b.yearOfPublication > 2020 order by b.yearOfPublication");
      result = query.execute();
      assertThat(result.hitCount()).hasValue(5);
      assertThat(result.list()).extracting("title").containsExactly(PRACTICE, UTIL, EFFECTIVE, MICROSERVICES, MEMORY);

      query = queryFactory.create("from insights.book b where b.price < 70.00 order by b.yearOfPublication desc");
      result = query.execute();
      assertThat(result.hitCount()).hasValue(4);
      assertThat(result.list()).extracting("title").containsExactly(MICROSERVICES, PERFORMANCE, EFFECTIVE, UTIL);
   }

   @Test
   public void testProjection() {
      QueryFactory queryFactory = Search.getQueryFactory(cache);
      Query<Object[]> query = queryFactory.create(
            "select b.title, b.yearOfPublication from insights.book b where b.author.numberOfPublishedBooks > 10 order by b.price");
      QueryResult<Object[]> result = query.execute();

      assertThat(result.list()).map(objects -> objects[0]).containsExactly(PERFORMANCE, EFFECTIVE, MICROSERVICES, MULTIPROCESSOR, MEMORY);
      assertThat(result.list()).map(objects -> objects[1]).containsExactly(2020, 2017, 2021, 2020, 2022);
   }

   @Test
   public void testNestedQuery() {
      QueryFactory queryFactory = Search.getQueryFactory(cache);
      Query<Book> query;
      QueryResult<Book> result;

      query = queryFactory.create("from insights.book b where b.reviews.content : 'nice'");
      result = query.execute();
      assertThat(result.hitCount()).hasValue(4);
      assertThat(result.list()).extracting("title").containsExactlyInAnyOrder(PRACTICE, UTIL, MEMORY, MULTIPROCESSOR);

      query = queryFactory.create("from insights.book b where b.reviews.score = 8.75");
      result = query.execute();
      assertThat(result.list()).extracting("title").containsExactlyInAnyOrder(EFFECTIVE, PRACTICE, UTIL, MEMORY, MULTIPROCESSOR);

      query = queryFactory.create("from insights.book b where b.reviews.score = 8.75 and b.reviews.content : 'nice'");
      result = query.execute();
      assertThat(result.list()).extracting("title").containsExactlyInAnyOrder(PRACTICE, UTIL, MEMORY, MULTIPROCESSOR);
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

   @Test
   public void testScrolling() {
      // this is a stateful pagination

      QueryFactory queryFactory = Search.getQueryFactory(cache);
      Query<Book> query = queryFactory.create("from insights.book b order by title");
      try (CloseableIterator<Book> iterator = query.iterator()) {
         assertThat(iterator).toIterable().extracting("title")
               .containsExactly(EFFECTIVE, PRACTICE, MEMORY, PERFORMANCE, UTIL, MICROSERVICES, MULTIPROCESSOR);
      }
   }
}
