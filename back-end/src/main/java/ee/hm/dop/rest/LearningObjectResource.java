package ee.hm.dop.rest;

import ee.hm.dop.model.*;
import ee.hm.dop.model.enums.RoleString;
import ee.hm.dop.service.Like;
import ee.hm.dop.service.content.LearningObjectService;
import ee.hm.dop.service.content.dto.TagDTO;
import ee.hm.dop.service.metadata.TagService;
import ee.hm.dop.service.useractions.UserFavoriteService;
import ee.hm.dop.service.useractions.UserLikeService;
import ee.hm.dop.utils.NumberUtils;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;

import static com.google.common.primitives.Ints.min;

@Path("learningObject")
public class LearningObjectResource extends BaseResource {

    @Inject
    private TagService tagService;
    @Inject
    private UserFavoriteService userFavoriteService;
    @Inject
    private UserLikeService userLikeService;
    @Inject
    private LearningObjectService learningObjectService;

    @GET
    @Path("promoted")
    public List<LearningObject> promoted() {
        return learningObjectService.findAllPromoted();
    }

    @PUT
    @Path("{learningObjectId}/tags")
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LearningObject addTag(@PathParam("learningObjectId") Long learningObjectId, Tag newTag) {
        return tagService.addRegularTag(learningObjectId, newTag, getLoggedInUser());
    }

    @PUT
    @Path("{learningObjectId}/system_tags")
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TagDTO addSystemTag(@PathParam("learningObjectId") Long learningObjectId, Tag newTag) {
        return tagService.addSystemTag(learningObjectId, newTag, getLoggedInUser());
    }

    @GET
    @Path("favorite")
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR, RoleString.RESTRICTED})
    public UserFavorite hasSetFavorite(@QueryParam("id") Long id) {
        return userFavoriteService.hasFavorited(id, getLoggedInUser());
    }

    @POST
    @Path("favorite")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR})
    public UserFavorite favoriteLearningObject(LearningObject learningObject) {
        return userFavoriteService.addUserFavorite(learningObject, getLoggedInUser());
    }

    @DELETE
    @Path("favorite")
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR})
    public void removeUserFavorite(@QueryParam("id") long id) {
        userFavoriteService.removeUserFavorite(id, getLoggedInUser());
    }

    @POST
    @Path("favorite/delete")
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR})
    public void removeUserFavorite2(LearningObjectMiniDto loDto) {
        userFavoriteService.removeUserFavorite(loDto.getId(), getLoggedInUser());
    }

    @GET
    @Path("usersFavorite")
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR, RoleString.RESTRICTED})
    public SearchResult getUsersFavorites(@QueryParam("start") int start, @QueryParam("maxResults") int maxResults) {
        SearchResult result = userFavoriteService.getUserFavoritesSearchResult(getLoggedInUser(), 0, 2147483647);
        result.getItems().sort(Comparator.comparing(Searchable::getType).reversed());
        int stop = min(start + NumberUtils.zvl(maxResults, 12), result.getItems().size());
        result.setItems(result.getItems().subList(start, stop));
        result.setStart(start);
        return result;
    }

    @GET
    @Path("usersFavorite/count")
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR, RoleString.RESTRICTED})
    public Long getUsersFavoritesCount() {
        return userFavoriteService.getUserFavoritesSize(getLoggedInUser());
    }

    @POST
    @Path("getUserLike")
    public UserLike getUserLike(LearningObjectMiniDto learningObjectMiniDto) {
        return userLikeService.getUserLike(learningObjectMiniDto.convert(), getLoggedInUser());
    }

    @POST
    @Path("removeUserLike")
    public void removeUserLike(LearningObjectMiniDto learningObjectMiniDto) {
        userLikeService.removeUserLike(learningObjectMiniDto.convert(), getLoggedInUser());
    }

    @POST
    @Path("like")
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR})
    public void likeMaterial(LearningObjectMiniDto learningObjectMiniDto) {
        userLikeService.addUserLike(learningObjectMiniDto.convert(), getLoggedInUser(), Like.LIKE);
    }

    @POST
    @Path("dislike")
    @RolesAllowed({RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR})
    public void dislikeMaterial(LearningObjectMiniDto learningObjectMiniDto) {
        userLikeService.addUserLike(learningObjectMiniDto.convert(), getLoggedInUser(), Like.DISLIKE);
    }

    @POST
    @Path("increaseViewCount")
    public void increaseViewCount(LearningObjectMiniDto learningObjectMiniDto) {
        learningObjectService.incrementViewCount(learningObjectMiniDto.convert());
    }
}
