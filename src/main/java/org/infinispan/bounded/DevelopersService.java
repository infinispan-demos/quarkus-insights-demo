package org.infinispan.bounded;

import io.quarkus.infinispan.client.CacheInvalidate;
import io.quarkus.infinispan.client.CacheInvalidateAll;
import io.quarkus.infinispan.client.CacheResult;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DevelopersService {

   public static final String DEVELOPERS_CACHE_NAME = "developers";

   static final Map<String, Developer> REF_DATA = Map.ofEntries(
         new AbstractMap.SimpleEntry("wburns", new Developer("Will", "Burns", "Infinispan")),
         new AbstractMap.SimpleEntry("fax2dev", new Developer("Fabio Massimo", "Ercoli", "Infinispan")),
         new AbstractMap.SimpleEntry("ttarrant", new Developer("Tristan", "Tarrant", "Infinispan"))
   );

   Map<String, Developer> data = new HashMap<>(REF_DATA);

   public void addDeveloper(String nickname, Developer developer) {
      Log.info(developer);
      data.put(nickname, developer);
   }

   @CacheResult(cacheName = DevelopersService.DEVELOPERS_CACHE_NAME)
   public Developer getDeveloper(String nickname) {
      Log.info("Getting from service call " + nickname);
      return data.get(nickname);
   }

   @CacheInvalidate(cacheName = DevelopersService.DEVELOPERS_CACHE_NAME)
   public void removeDeveloper(String nickname) {
      data.remove(nickname);
   }

   @CacheInvalidateAll(cacheName = DevelopersService.DEVELOPERS_CACHE_NAME)
   public void removeAll() {
      data.clear();
      data.putAll(REF_DATA);
   }
}
