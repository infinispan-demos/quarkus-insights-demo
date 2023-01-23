package org.infinispan;

import java.util.ArrayList;
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

   @PUT
   public void initData() {
//      booksCache.clear();
//      booksCache.putAll(ModelGenerator.generateBooks());
   }

   @GET
   @Path("/description/{term}")
   public List<Book> query(@PathParam("term") String term) {
      return new ArrayList<>();
   }
}
