# library Project

This project is based on the following repositories:
- [Infinispan Quarkus Quickstart](https://github.com/quarkusio/quarkus-quickstarts/tree/main/infinispan-client-quickstart)
- [Fabio Massimo Ercoli Quarkus playground](https://github.com/fax4ever/quarkus-play)


# Steps Building

## Dev services
1. Start Quarkus with Docker running
```bash
./mvnw quarkus:dev
```
2. Open Dev UI (d), open the Infinispan Console
3. Inject a cache in Greeting Resource
```java
    @Inject
    @Remote("greetings")
    RemoteCache<String, String> greetingsCache;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        greetingsCache.put("123", "Hello Quarkus Insights");
        return "Hello Quarkus";
    }
```
4. Command line 
```bash
http localhost:8080/hello 
```

## Health Check

1. Show that we have the Health Extension
2. In the DEV UI access to the Health UI and show the Readiness Endpoint available
3. Default enabled, can be disabled with
```properties
add the property
```

## Create Caches with configuration

1. Open the model 

2. Create a ca

## From the middle to index query

1. Add indexing model:
* create the package `org.infinispan.search`
* add java classes: `Author`, `Book`, `Review`, `BooksSchema`

2. Build and star the dev mode

``` sh
./mvnw clean install 
./mvnw quarkus:dev
```

3. Show the schemas on Infinispan console

4. Highlight the fact that in production you should set:

``` properties
quarkus.infinispan-client.use-schema-registration=false
```

5. Create the cache `books` (startupMode => Replicated, None, local-indexed, indexed entities => insights.book)
* download the file (yaml)

6. Copy file books.yaml => main/resources/
* add to application.properties

``` properties
quarkus.infinispan-client.cache.books.configuration-uri=books.yaml
```

7.  Play with tests
* Create test/java directory
* Create package `org.infinispan.search`
* Copy `ModelGenerator` and `SearchTest`
* Show the test cases

8. Run the query test
``` sh
./mvnw clean install
```

## Tracing to main

1. Run Jaeger container

``` sh
docker run --name jaeger \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p 16686:16686 \
  -p 4317:4317 \
  --rm \
  jaegertracing/all-in-one:1.36
```

2. Find the bridge network address ip for it:

``` sh
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' jaeger
```

3. In `application.properties` replace the:

``` properties
quarkus.opentelemetry.enabled=false
```

with:

``` properties
quarkus.infinispan-client.devservices.tracing.enabled=true
# Replace 172.17.0.2 with the output of docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' jaeger
quarkus.infinispan-client.devservices.tracing.exporter.otlp.endpoint=http://172.17.0.2:4317

quarkus.opentelemetry.tracer.exporter.otlp.endpoint=http://localhost:4317
```

4. Add methods to the `QuarkusInsightsResource` endpoint

``` java
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
```

5. Build and run the dev mode:

``` sh
./mvnw clean install 
./mvnw quarkus:dev
```

6. Curl the endpoint

``` sh
http PUT localhost:8080/hello/async/10
http PUT localhost:8080/hello/bulk/10
```