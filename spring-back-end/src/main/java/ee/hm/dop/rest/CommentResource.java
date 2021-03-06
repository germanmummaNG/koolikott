package ee.hm.dop.rest;

import ee.hm.dop.model.Comment;
import ee.hm.dop.model.LearningObject;
import ee.hm.dop.model.enums.RoleString;
import ee.hm.dop.service.useractions.CommentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("comment")
@Secured({ RoleString.USER, RoleString.ADMIN, RoleString.MODERATOR })
public class CommentResource extends BaseResource {

    @Inject
    private CommentService commentService;

    @PostMapping
    public LearningObject addComment(@RequestBody AddComment form) {
        return commentService.addComment(form.getComment(), form.getLearningObject(), getLoggedInUser());
    }

    public static class AddComment {
        private Comment comment;
        private LearningObject learningObject;

        public Comment getComment() {
            return comment;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public LearningObject getLearningObject() {
            return learningObject;
        }

        public void setLearningObject(LearningObject learningObject) {
            this.learningObject = learningObject;
        }
    }

}
