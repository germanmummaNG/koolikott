package ee.hm.dop.rest;

import ee.hm.dop.service.solr.SuggestService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("suggest")
public class SuggestResource extends BaseResource {

    @Inject
    private SuggestService suggestService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response suggest(@QueryParam("q") String query){
        return suggestService.suggest(query);
    }
}
