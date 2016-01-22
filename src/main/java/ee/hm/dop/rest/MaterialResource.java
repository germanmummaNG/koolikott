package ee.hm.dop.rest;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.net.HttpURLConnection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ee.hm.dop.model.BrokenContent;
import ee.hm.dop.model.ImproperContent;
import ee.hm.dop.model.Material;
import ee.hm.dop.model.User;
import ee.hm.dop.model.UserLike;
import ee.hm.dop.service.MaterialService;
import ee.hm.dop.service.UserService;

@Path("material")
public class MaterialResource extends BaseResource {

    @Inject
    private MaterialService materialService;

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Material get(@QueryParam("materialId") long materialId) {
        return materialService.get(materialId);
    }

    @GET
    @Path("getNewestMaterials")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Material> getNewestMaterials(@QueryParam("numberOfMaterials") int numberOfMaterials) {
        return materialService.getNewestMaterials(numberOfMaterials);
    }

    @GET
    @Path("getPopularMaterials")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Material> getPopularMaterials(@QueryParam("numberOfMaterials") int numberOfMaterials) {
        return materialService.getPopularMaterials(numberOfMaterials);
    }

    @POST
    @Path("increaseViewCount")
    public Response increaseViewCount(Material material) {
        Long materialId = material.getId();

        Material originalMaterial = materialService.get(materialId);
        if (originalMaterial == null) {
            return Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Invalid material").build();
        }

        materialService.increaseViewCount(originalMaterial);
        return Response.status(HttpURLConnection.HTTP_OK).build();
    }

    @POST
    @Path("like")
    public void likeMaterial(Material material) {
        materialService.addUserLike(material, getLoggedInUser(), true);
    }

    @POST
    @Path("dislike")
    public void dislikeMaterial(Material material) {
        materialService.addUserLike(material, getLoggedInUser(), false);
    }

    @POST
    @Path("recommend")
    @RolesAllowed({ "ADMIN" })
    public void recommendMaterial(Material material) {
        materialService.addRecommendation(material, getLoggedInUser());
    }

    @POST
    @Path("removeRecommendation")
    @RolesAllowed({ "ADMIN" })
    public void removedMaterialRecommendation(Material material) {
        materialService.removeRecommendation(material, getLoggedInUser());
    }

    @POST
    @Path("getUserLike")
    public UserLike getUserLike(Material material) {
        return materialService.getUserLike(material, getLoggedInUser());
    }

    @POST
    @Path("removeUserLike")
    public void removeUserLike(Material material) {
        materialService.removeUserLike(material, getLoggedInUser());
    }

    @GET
    @Path("/getPicture")
    @Produces("image/png")
    public Response getPictureById(@QueryParam("materialId") long id) {
        Material material = new Material();
        material.setId(id);
        byte[] pictureData = materialService.getMaterialPicture(material);

        if (pictureData != null) {
            return Response.ok(pictureData).build();
        } else {
            return Response.status(HttpURLConnection.HTTP_NOT_FOUND).build();
        }
    }

    @GET
    @Path("getByCreator")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Material> getByCreator(@QueryParam("username") String username) {
        if (isBlank(username)) {
            throwBadRequestException("Username parameter is mandatory");
        }

        User creator = userService.getUserByUsername(username);
        if (creator == null) {
            return null;
        }

        return materialService.getByCreator(creator);
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public void delete(Material material) {
        materialService.delete(material, getLoggedInUser());
    }

    @POST
    @Path("restore")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public void restore(Material material) {
        materialService.restore(material, getLoggedInUser());
    }

    @POST
    @RolesAllowed({ "USER", "ADMIN", "PUBLISHER" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Material createMaterial(Material material) {
        material = materialService.createMaterial(material, getLoggedInUser(), true);
        return material;
    }

    @POST
    @Path("setImproper")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "USER", "ADMIN", "PUBLISHER" })
    public ImproperContent setImproperMaterial(Material material) {
        return materialService.addImproperMaterial(material, getLoggedInUser());
    }

    @GET
    @Path("getImproper")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public List<ImproperContent> getImproperMaterials() {
        return materialService.getImproperMaterials();
    }

    @POST
    @Path("setBroken")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "USER", "ADMIN", "PUBLISHER" })
    public BrokenContent setBrokenMaterial(Material material) {
        return materialService.addBrokenMaterial(material, getLoggedInUser());
    }

    @GET
    @Path("getBroken")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public List<BrokenContent> getBrokenMaterial() {
        return materialService.getBrokenMaterials();
    }

    @GET
    @Path("setNotBroken")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public void setNotBroken(@QueryParam("materialId") long materialId) {
        materialService.setMaterialNotBroken(materialId);
    }

    @GET
    @Path("hasSetBroken")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "USER", "ADMIN", "PUBLISHER" })
    public Boolean hasSetBroken(@QueryParam("materialId") long materialId) {
        return materialService.hasSetBroken(materialId, getLoggedInUser());
    }

    @GET
    @Path("isBroken")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public Boolean isBroken(@QueryParam("materialId") long materialId) {
        return materialService.isBroken(materialId, getLoggedInUser());
    }

    @GET
    @Path("getDeleted")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public List<Material> getDeletedMaterials() {
        return materialService.getDeletedMaterials();
    }

    @GET
    @Path("hasSetImproper")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "USER", "ADMIN", "PUBLISHER" })
    public Boolean hasSetImproper(@QueryParam("materialId") long materialId) {
        return materialService.hasSetImproper(materialId, getLoggedInUser());
    }

    @POST
    @Path("setNotImproper/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public void removeImproperPortfolios(@PathParam("id") Long id) {
        materialService.removeImproperMaterials(id);
    }

    @GET
    @Path("isSetImproper")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public Boolean isSetImproper(@QueryParam("materialId") long materialId) {
        return materialService.isSetImproper(materialId);
    }

    private void throwBadRequestException(String message) {
        throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity(message).build());
    }
}
