package ee.hm.dop.dao;

import ee.hm.dop.model.Material;
import ee.hm.dop.model.Portfolio;
import ee.hm.dop.model.PortfolioMaterial;

import java.math.BigInteger;
import java.util.List;

public class PortfolioMaterialDao extends AbstractDao<PortfolioMaterial> {

    public Class<PortfolioMaterial> entity() {
        return PortfolioMaterial.class;
    }

    public void connectMaterialToPortfolio(Material material, Portfolio portfolio) {
        getEntityManager()
                .createNativeQuery("INSERT INTO PortfolioMaterial (portfolio,material) VALUES (:portfolio,:material)")
                .setParameter("portfolio", portfolio)
                .setParameter("material", material)
                .executeUpdate();
    }

    public boolean materialToPortfolioConnected(Material material, Portfolio portfolio) {
        List<Long> portfolioList = getEntityManager()
                .createQuery("SELECT pm.portfolio.id FROM PortfolioMaterial pm WHERE pm.material =:material")
                .setParameter("material", material)
                .getResultList();

        return portfolioList.contains(portfolio.getId());
    }

    public boolean hasData() {
        BigInteger portfolioList = (BigInteger) getEntityManager()
                .createNativeQuery("select exists(select * from PortfolioMaterial) as exi")
                .getSingleResult();
        return portfolioList.intValue() > 0;
    }
}
