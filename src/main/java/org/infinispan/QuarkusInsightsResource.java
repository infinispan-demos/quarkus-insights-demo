package org.infinispan;

import io.quarkus.infinispan.client.Remote;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.data.ModelGenerator;
import org.infinispan.model.Book;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.dsl.QueryResult;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/hello")
public class QuarkusInsightsResource {

   // Step 1
   @Inject
   @Remote("greetings")
   RemoteCache<String, String> greetingsCache;

   @Inject
   @Remote("books")
   RemoteCache<String, Book> booksCache;

   @Inject
   @Remote("books-indexed")
   RemoteCache<String, Book> booksIndexed;

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String hello() {
      greetingsCache.put("123", "Hello Quarkus Insights");
      return "Hello Quarkus";
   }

   @GET
   @Path("/books")
   @Produces(MediaType.TEXT_PLAIN)
   public String books() {
      Map<String, Book> generatedBooks = ModelGenerator.generateBooks();
      booksCache.putAll(generatedBooks);
      return "Books added";
   }

   @GET
   @Path("/books-indexed")
   @Produces(MediaType.TEXT_PLAIN)
   public String booksIndexed() {
      Map<String, Book> generatedBooks = ModelGenerator.generateBooks();
      booksIndexed.putAll(generatedBooks);
      return "Books Indexed added";
   }

   @GET
   @Path("/books-search")
   @Produces(MediaType.TEXT_PLAIN)
   public List<Book> booksSearch() {
      QueryFactory queryFactory = Search.getQueryFactory(booksIndexed);
      Query<Book> query = queryFactory.create("from insights.book b where b.description : :description");
      query.setParameter("description", "Java");
      return query.execute().list();
   }

   @GET
   @Path("/authors-indexed")
   @Produces(MediaType.TEXT_PLAIN)
   public String authorsIndexed() {
      Map<String, Book> generatedBooks = ModelGenerator.generateBooks();
      booksIndexed.putAll(generatedBooks);
      return "Authors Indexed added";
   }


}
