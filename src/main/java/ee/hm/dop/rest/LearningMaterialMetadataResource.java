package ee.hm.dop.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ee.hm.dop.model.Domain;
import ee.hm.dop.model.EducationalContext;
import ee.hm.dop.service.TaxonService;

@Path("learningMaterialMetadata")
public class LearningMaterialMetadataResource {

    @Inject
    private TaxonService taxonService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("educationalContext")
    public List<EducationalContext> getEducationalContext() {
        return taxonService.getAllEducationalContext();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("domain")
    public List<Domain> getAllDomainsByEducationalContext(
            @QueryParam("educationalContext") String educationalContextName) {
        EducationalContext educationalContext = taxonService
                .getEducationalContextByName(educationalContextName.toUpperCase());
        return taxonService.getAllDomainsByEducationalContext(educationalContext);
    }
}
