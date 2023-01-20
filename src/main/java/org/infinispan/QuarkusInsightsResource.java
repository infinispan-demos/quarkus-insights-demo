package org.infinispan;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.infinispan.client.hotrod.RemoteCache;

import io.quarkus.infinispan.client.Remote;

@Path("/hello")
public class QuarkusInsightsResource {

   // Step 1
   @Inject
   @Remote("greetings")
   RemoteCache<String, String> greetingsCache;

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String hello() {
      greetingsCache.put("123", "Hello Quarkus Insights");
      return "Hello Quarkus";
   }

}
