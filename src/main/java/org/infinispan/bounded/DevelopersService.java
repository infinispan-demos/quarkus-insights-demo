package org.infinispan.bounded;

import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DevelopersService {

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

   public Developer getDeveloper(String nickname) {
      Log.info("Getting from service call " + nickname);
      return data.get(nickname);
   }

   public void removeDeveloper(String nickname) {
      Log.info("Remove " + nickname);
      data.remove(nickname);
   }

   public void removeAll() {
      Log.info("Clear all");
      data.clear();
      data.putAll(REF_DATA);
   }
}
