package ee.hm.dop.service.content;

import ee.hm.dop.dao.PortfolioDao;
import ee.hm.dop.dao.PortfolioLogDao;
import ee.hm.dop.dao.ReducedLearningObjectDao;
import ee.hm.dop.dao.TaxonDao;
import ee.hm.dop.model.*;
import ee.hm.dop.service.permission.PortfolioPermission;
import ee.hm.dop.utils.UserUtil;
import ee.hm.dop.utils.ValidatorUtil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ee.hm.dop.utils.ValidatorUtil.permissionError;

public class PortfolioGetter {

    @Inject
    private PortfolioDao portfolioDao;
    @Inject
    private ReducedLearningObjectDao reducedLearningObjectDao;
    @Inject
    private PortfolioPermission portfolioPermission;
    @Inject
    private TaxonDao taxonDao;
    @Inject
    private PortfolioLogDao portfolioLogDao;

    public Portfolio get(Long portfolioId, User loggedInUser) {
        if (UserUtil.isAdminOrModerator(loggedInUser)) {
            return portfolioDao.findById(portfolioId);
        }
        Portfolio portfolio = portfolioDao.findByIdNotDeleted(portfolioId);
        if (!portfolioPermission.canView(loggedInUser, portfolio)) {
            throw permissionError();
        }
        return portfolio;
    }

    public SearchResult getByCreatorResult(User creator, User loggedInUser, int start, int maxResults) {
        List<Searchable> searchables = new ArrayList<>(getByCreator(creator, loggedInUser, start, maxResults));
        Long size = getCountByCreator(creator);
        return new SearchResult(searchables, size, start);
    }

    public List<PortfolioLog> getPortfolioHistoryAll(Long portfolioId) {
        return portfolioLogDao.findByIdAllPortfolioLogs(portfolioId);
    }

    public PortfolioLog getPortfolioHistory(long portfolioHistoryId) {
        return portfolioLogDao.findById(portfolioHistoryId);
    }

    public List<ReducedLearningObject> getByCreator(User creator, User loggedInUser, int start, int maxResults) {
        return reducedLearningObjectDao.findPortfolioByCreator(creator, start, maxResults).stream()
                .filter(p -> portfolioPermission.canInteract(loggedInUser, p))
                .collect(Collectors.toList());
    }

    public Long getCountByCreator(User creator) {
        return portfolioDao.findCountByCreator(creator);
    }

    public Portfolio findValid(Portfolio portfolio) {
        return ValidatorUtil.findValid(portfolio, (Function<Long, Portfolio>) portfolioDao::findByIdNotDeleted);
    }

    public boolean showUnreviewedPortfolio(Long id, User user) {
        if (UserUtil.isAdmin(user)) {
            return true;
        } else if (UserUtil.isModerator(user)) {
            return taxonDao.getUserTaxonsWithChildren(user).contains(portfolioDao.findById(id).getTaxons());
        } else
            return false;
    }
}
