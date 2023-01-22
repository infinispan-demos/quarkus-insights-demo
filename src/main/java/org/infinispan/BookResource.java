package org.infinispan;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.search.Book;
import org.infinispan.search.generator.ModelGenerator;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.logging.Log;

@Path("books")
public class BookResource {

   @Inject
   @Remote("books")
   RemoteCache<String, Book> booksCache;

   @PUT
   public void initData() {
      booksCache.clear();
      booksCache.putAll(ModelGenerator.generateBooks());
   }

   @GET
   @Path("/description/{term}")
   public List<Book> query(@PathParam("term") String term) {
      Query<Book> query = Search.getQueryFactory(booksCache)
            .create("from insights.book b where b.description : :description");
      query.setParameter("description", term);

      List<Book> books = query.execute().list();
      Log.info(books);
      return books;
   }
}
