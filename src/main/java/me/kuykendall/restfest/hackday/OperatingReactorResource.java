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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Path("/operating-reactor")
public class OperatingReactorResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces({MediaType.COLLECTION_JSON, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getReactorCollectionAsCj() throws UnsupportedEncodingException, URISyntaxException {
        OperatingReactorDAO operatingReactorDAO = new XLSOperatingReactorDAO();
        OperatingReactorQueryInfo queryInfo = new OperatingReactorQueryInfo();
        List<OperatingReactor> operatingReactors = operatingReactorDAO.getOperatingReactors(queryInfo);


        List<Item> items = new ArrayList<>();
        for (OperatingReactor operatingReactor : operatingReactors) {

            String encodedDocketNumber = URLEncoder.encode(operatingReactor.getDocketNumber(), "UTF-8");
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();

            builder.path(encodedDocketNumber);

            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(OperatingReactorResource.class, "getReactorInstanceAsCj")
                    .build(operatingReactor.getDocketNumber());

//            List<Link> links = new ArrayList<>();
//            links.add(Link.create(new URI(operatingReactor.getWebPage().getUrl()), operatingReactor.getWebPage().getLabel()));

            Item item = Item.builder(uri)
                    .addProperty(Property.value("plantName", new Value.StringValue(operatingReactor.getPlantName())))
                    .addProperty(Property.value("docketNumber", new Value.StringValue(operatingReactor.getDocketNumber())))
//                    .addLinks(links)
                    .build();

            items.add(item);
        }

        Collection collection = Collection.builder(uriInfo.getRequestUri())
                .addItems(items)
                .build();
        return Response
                .ok(collection.toString())
                .build();

    }

    /**
     * Service call that returns an insult in the JSON format.
     *
     * @return A JSON representation of an insult.
     */
    @GET
    @Path("/{docketNumber}")
    @Produces({MediaType.COLLECTION_JSON, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    public Response getReactorInstanceAsCj(@PathParam("docketNumber") String docketNumber) throws URISyntaxException {
        OperatingReactorDAO operatingReactorDAO = new XLSOperatingReactorDAO();
        OperatingReactor operatingReactor = operatingReactorDAO.getOperatingReactorByDocketNumber(docketNumber);

        Collection collection = Collection.builder(uriInfo.getRequestUri())
                .addItem(Item.builder(uriInfo.getRequestUri())
                        .addProperty(Property.value("plantName", new Value.StringValue(operatingReactor.getPlantName())))
                        .addProperty(Property.value("docketNumber", new Value.StringValue(operatingReactor.getDocketNumber())))
                        .addLink(Link.create(new URI(operatingReactor.getWebPage().getUrl()), operatingReactor.getWebPage().getLabel()))
                        .build())
                .build();
        return Response
                .ok(collection.toString())
                .build();
    }
}
