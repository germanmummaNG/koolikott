package ee.hm.dop.service.content;

import ee.hm.dop.dao.OriginalPictureDao;
import ee.hm.dop.model.OriginalPicture;
import ee.hm.dop.model.Portfolio;
import ee.hm.dop.model.enums.Visibility;
import org.joda.time.DateTime;

import javax.inject.Inject;

public class PortfolioConverter {

    @Inject
    private OriginalPictureDao originalPictureDao;

    public Portfolio getPortfolioWithAllowedFieldsOnCreate(Portfolio portfolio) {
        Portfolio safePortfolio = new Portfolio();
        return commonConvert(safePortfolio, portfolio);
    }

    public Portfolio setPortfolioUpdatableFields(Portfolio to, Portfolio from) {
        commonConvert(to, from);
        if (changesToPublic(to, from)) to.setPublishedAt(DateTime.now());
        to.setVisibility(from.getVisibility());
        to.setPublicationConfirmed(from.isPublicationConfirmed());
        return to;
    }

    private boolean changesFromPublic(Portfolio to, Portfolio from) {
        return from.getVisibility().isNotPublic() && to.getVisibility().isPublic();
    }

    private boolean changesToPublic(Portfolio to, Portfolio from) {
        return from.getVisibility().isPublic() && to.getVisibility().isNotPublic();
    }

    private Portfolio commonConvert(Portfolio to, Portfolio from) {
        to.setTitle(from.getTitle());
        to.setSummary(from.getSummary());
        to.setTags(from.getTags());
        to.setTargetGroups(from.getTargetGroups());
        to.setTaxons(from.getTaxons());
        to.setChapters(from.getChapters());
        to.setPicture(from.getPicture());
        to.setLicenseType(from.getLicenseType());
        if (from.getPicture() != null) {
            if (from.getPicture().getId() == null) {
                to.setPicture(null);
            } else {
                OriginalPicture originalPicture = originalPictureDao.findById(from.getPicture().getId());
                to.getPicture().setData(originalPicture.getData());
                to.getPicture().setName(originalPicture.getName());
            }
        }
        return to;
    }
}
