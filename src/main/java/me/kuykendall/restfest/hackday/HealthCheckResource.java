package me.kuykendall.restfest.hackday;

import net.hamnaberg.json.*;

import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@RequestScoped
@Path("/health")
public class HealthCheckResource {

    @Context
    UriInfo uriInfo;

    /**
     * Service call that returns an insult in the JSON format.
     *
     * @return A JSON representation of an insult.
     */
    @GET
    @Produces(MediaType.COLLECTION_JSON)
    public Response getInsultAsCj() {
        Collection collection = Collection.builder(uriInfo.getRequestUri())
                .addItem(Item.builder(uriInfo.getRequestUri())
                        .addProperty(Property.value("insult",
                                new Value.StringValue("it works")))
                        .build())
                .build();
        return Response
                .ok(collection.toString())
                .build();
    }
}
