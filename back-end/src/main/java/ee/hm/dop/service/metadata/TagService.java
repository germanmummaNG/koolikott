package ee.hm.dop.service.metadata;

import ee.hm.dop.dao.LearningObjectDao;
import ee.hm.dop.dao.PortfolioDao;
import ee.hm.dop.dao.TagDao;
import ee.hm.dop.model.LearningObject;
import ee.hm.dop.model.Portfolio;
import ee.hm.dop.model.Tag;
import ee.hm.dop.model.User;
import ee.hm.dop.service.content.LearningObjectService;
import ee.hm.dop.service.content.PortfolioService;
import ee.hm.dop.service.solr.SolrEngineService;
import ee.hm.dop.utils.ValidatorUtil;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public class TagService {

    @Inject
    private TagDao tagDao;
    @Inject
    private LearningObjectDao learningObjectDao;
    @Inject
    private SolrEngineService solrEngineService;
    @Inject
    private LearningObjectService learningObjectService;
    @Inject
    private PortfolioService portfolioService;
    @Inject
    private PortfolioDao portfolioDao;

    public Tag getTagByName(String name) {
        return tagDao.findByName(name);
    }

    public LearningObject addRegularTag(Long learningObjectId, Tag tag, User loggedInUser) {
        LearningObject learningObject = learningObjectService.get(learningObjectId, loggedInUser);
        ValidatorUtil.mustHaveEntity(learningObject, learningObjectId);

        return addTag(learningObject, tag, loggedInUser);
    }

    private LearningObject addTag(LearningObject learningObject, Tag newTag, User user) {
        if (!learningObjectService.canAccess(user, learningObject)) {
            throw ValidatorUtil.permissionError();
        }

        List<Tag> tags = learningObject.getTags();
        if (tags.contains(newTag)) {
            throw new WebApplicationException(String.format("Tag already exists. Lo: %s. Tags: %s", learningObject.getId(), tags), Response.Status.BAD_REQUEST);
        }

        tags.add(newTag);
        LearningObject updatedLearningObject = learningObjectDao.createOrUpdate(learningObject);
        Portfolio portfolio = portfolioDao.findById(updatedLearningObject.getId());
        portfolioService.update(portfolio, user);

        solrEngineService.updateIndex();

        return updatedLearningObject;
    }
}
