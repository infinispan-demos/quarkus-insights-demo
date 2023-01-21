package org.infinispan;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
   @Produces(MediaType.TEXT_PLAIN)
   @WithSpan(value = "wait-for-all-response-span", kind = SpanKind.CLIENT)
   public List<Object> putAll() {
      CompletableFuture[] promises = IntStream.range(0, 10).boxed()
            .map(value -> greetingsCache.putAsync(value.toString(), Character.toString('A' + value)))
            .toList().toArray(new CompletableFuture[0]);

      return Arrays.stream(promises).map(item -> item.join()).toList();
   }
}
