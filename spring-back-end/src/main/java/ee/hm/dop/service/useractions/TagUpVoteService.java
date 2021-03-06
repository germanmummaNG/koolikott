package ee.hm.dop.service.useractions;

import javax.inject.Inject;

import ee.hm.dop.dao.TagUpVoteDao;
import ee.hm.dop.model.LearningObject;
import ee.hm.dop.model.Tag;
import ee.hm.dop.model.TagUpVote;
import ee.hm.dop.model.User;
import ee.hm.dop.service.content.LearningObjectService;
import ee.hm.dop.service.solr.SolrEngineService;
import ee.hm.dop.utils.UserUtil;
import ee.hm.dop.utils.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class TagUpVoteService {

    @Inject
    private TagUpVoteDao tagUpVoteDao;
    @Inject
    private SolrEngineService solrEngineService;
    @Inject
    private LearningObjectService learningObjectService;

    public TagUpVote get(long id, User user) {
        TagUpVote tagUpVote = tagUpVoteDao.findById(id);
        if (tagUpVote != null) {
            mustHaveAccess(tagUpVote, user);
        }
        return tagUpVote;
    }

    public TagUpVote upVote(TagUpVote tagUpVote, User user) {
        mustHaveAccess(tagUpVote, user);

        tagUpVote.setUser(user);

        TagUpVote returnTagUpVote = tagUpVoteDao.createOrUpdate(tagUpVote);
        solrEngineService.updateIndex();

        return returnTagUpVote;
    }

    public void delete(TagUpVote tagUpVote, User user) {
        mustHaveAccess(tagUpVote, user);
        tagUpVoteDao.setDeleted(tagUpVote);
        solrEngineService.updateIndex();
    }

    public Long getUpVoteCountFor(Tag tag, LearningObject learningObject) {
        return tagUpVoteDao.findByLearningObjectAndTagCount(learningObject, tag);
    }

    public TagUpVote getTagUpVote(Tag tag, LearningObject learningObject, User user) {
        if (learningObjectService.canAccess(user, learningObject)) {
            return tagUpVoteDao.findByTagAndUserAndLearningObject(tag, user, learningObject);
        }
        return null;
    }

    private void mustHaveAccess(TagUpVote tagUpVote, User user) {
        if (tagUpVote.getId() != null && !Objects.equals(user.getId(), tagUpVote.getUser().getId())
                || !learningObjectService.canAccess(user, tagUpVote.getLearningObject())) {
            throw ValidatorUtil.permissionError();
        }
    }
}
