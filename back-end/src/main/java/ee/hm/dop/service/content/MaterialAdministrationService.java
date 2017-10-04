package ee.hm.dop.service.content;

import ee.hm.dop.dao.BrokenContentDao;
import ee.hm.dop.dao.MaterialDao;
import ee.hm.dop.model.BrokenContent;
import ee.hm.dop.model.Material;
import ee.hm.dop.model.Recommendation;
import ee.hm.dop.model.User;
import ee.hm.dop.service.solr.SolrEngineService;
import ee.hm.dop.utils.UserUtil;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public class MaterialAdministrationService {

    @Inject
    private MaterialDao materialDao;
    @Inject
    private BrokenContentDao brokenContentDao;

    public void setMaterialNotBroken(Material material) {
        brokenContentDao.deleteBrokenMaterials(material.getId());
    }

    public List<Material> getDeletedMaterials() {
        return materialDao.findDeletedMaterials();
    }

    public Long getDeletedMaterialsCount() {
        return materialDao.findDeletedMaterialsCount();
    }

    public List<BrokenContent> getBrokenMaterials(User user) {
        UserUtil.mustBeModeratorOrAdmin(user);
        if (UserUtil.isAdmin(user)) {
            return brokenContentDao.getBrokenMaterials();
        } else {
            return brokenContentDao.getBrokenMaterials(user);
        }
    }

    public Long getBrokenMaterialCount(User user) {
        UserUtil.mustBeModeratorOrAdmin(user);
        if (UserUtil.isAdmin(user)) {
            return brokenContentDao.getBrokenCount();
        } else {
            return brokenContentDao.getBrokenCount(user);
        }
    }

    public Boolean isBroken(long materialId) {
        return isNotEmpty(brokenContentDao.findByMaterial(materialId));
    }
}
