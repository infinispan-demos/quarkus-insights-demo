package org.infinispan;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.infinispan.client.hotrod.RemoteCache;

import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.annotations.WithSpan;
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

   @PUT
   @Path("async/{calls}")
   @Produces(MediaType.TEXT_PLAIN)
   @WithSpan(value = "wait-for-async", kind = SpanKind.CLIENT)
   public String putAsync(@PathParam("calls") Integer calls) {
      CompletableFuture[] promises = IntStream.range(0, calls).boxed()
            .map(value -> greetingsCache.putAsync(value.toString(), Character.toString('A' + value)))
            .toList().toArray(new CompletableFuture[0]);

      CompletableFuture.allOf(promises).join();

      return "Executed " + calls + " calls.";
   }

   @PUT
   @Path("bulk/{calls}")
   @Produces(MediaType.TEXT_PLAIN)
   @WithSpan(value = "wait-for-putAll", kind = SpanKind.CLIENT)
   public String putAll(@PathParam("calls") Integer calls) {
      greetingsCache.putAll(IntStream.range(0, calls).boxed()
            .collect(Collectors.toMap(value -> value.toString(), value -> Character.toString('A' + value))));

      return "Executed " + calls + " calls.";
   }
}
