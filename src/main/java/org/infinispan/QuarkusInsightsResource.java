package org.infinispan;

import org.infinispan.bounded.Developer;
import org.infinispan.bounded.DevelopersService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class QuarkusInsightsResource {

   DevelopersService developersService;

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String hello() {
      return "Hello Quarkus";
   }

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   @Path("/{nickname}")
   public Response getDeveloper(@PathParam("nickname") String nickname) {
      Developer developer = developersService.getDeveloper(nickname);
      if (developer == null) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok(developersService.getDeveloper(nickname)).build();
   }

   @POST
   @Produces(MediaType.TEXT_PLAIN)
   @Path("/{nickname}")
   public Response createDeveloper(@PathParam("nickname") String nickname, Developer developer) {
      developersService.addDeveloper(nickname, developer);
      return Response.ok().build();
   }


   @DELETE
   @Produces(MediaType.TEXT_PLAIN)
   @Path("/{nickname}")
   public Response removeDeveloper(@PathParam("nickname") String nickname) {
      developersService.removeDeveloper(nickname);
      return Response.ok().build();
   }

   @DELETE
   @Produces(MediaType.TEXT_PLAIN)
   public Response removeAll() {
      developersService.removeAll();
      return Response.ok().build();
   }

}
