package me.kuykendall.restfest.hackday;

import me.kuykendall.restfest.hackday.dao.OperatingReactorDAO;
import me.kuykendall.restfest.hackday.dao.XLSOperatingReactorDAO;
import me.kuykendall.restfest.hackday.model.OperatingReactor;
import net.hamnaberg.json.*;

import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@RequestScoped
@Path("/operating-reactor")
public class OperatingReactorResource {

    @Context
    UriInfo uriInfo;

    /**
     * Service call that returns an insult in the JSON format.
     *
     * @return A JSON representation of an insult.
     */
    @GET
    @Path("/{docketNumber}")
    @Produces({MediaType.COLLECTION_JSON, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getInsultAsCj(@PathParam("docketNumber") String docketNumber) throws Exception {
        OperatingReactorDAO operatingReactorDAO = new XLSOperatingReactorDAO();
        OperatingReactor operatingReactor = operatingReactorDAO.getOperatingReactorByDocketNumber(docketNumber);

        Collection collection = Collection.builder(uriInfo.getRequestUri())
                .addItem(Item.builder(uriInfo.getRequestUri())
                        .addProperty(Property.value("plantName", new Value.StringValue(operatingReactor.getPlantName())))
                        .addProperty(Property.value("website", new Value.StringValue(operatingReactor.getWebPage())))
                        .addProperty(Property.value("docketNumber", new Value.StringValue(operatingReactor.getDocketNumber())))
                        .build())
                .build();
        return Response
                .ok(collection.toString())
                .build();
    }
}
