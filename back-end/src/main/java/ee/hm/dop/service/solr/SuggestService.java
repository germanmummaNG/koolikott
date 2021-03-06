package ee.hm.dop.service.solr;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class SuggestService {

    @Inject
    private SolrEngineService solrEngineService;

    public Response suggest(String query) {
        if (query.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<String> suggestResponse = solrEngineService.suggest(query);
        return Response.ok(suggestResponse).build();
    }

}
