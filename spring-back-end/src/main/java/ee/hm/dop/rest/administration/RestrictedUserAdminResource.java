package ee.hm.dop.rest.administration;

import ee.hm.dop.model.User;
import ee.hm.dop.model.enums.RoleString;
import ee.hm.dop.rest.BaseResource;
import ee.hm.dop.service.useractions.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("admin/restrictedUser")
public class RestrictedUserAdminResource extends BaseResource{

    @Inject
    private UserService userService;

    @GetMapping
    @Secured(RoleString.ADMIN)
    public List<User> getRestrictedUsers() {
        return userService.getRestrictedUsers(getLoggedInUser());
    }

    @GetMapping
    @RequestMapping("count")
    @Secured(RoleString.ADMIN)
    public Long getRestrictedUsersCount() {
        return userService.getRestrictedUsersCount(getLoggedInUser());
    }

}
