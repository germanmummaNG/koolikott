package ee.hm.dop.dao;

import ee.hm.dop.model.AdminLearningObject;
import ee.hm.dop.model.ReviewableChange;
import ee.hm.dop.model.User;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.List;

public class ReviewableChangeDao extends AbstractDao<ReviewableChange> {

    @Inject
    private TaxonDao taxonDao;

    public List<ReviewableChange> getAllByLearningObject(Long learningObjectId) {
        return getEntityManager().createQuery("FROM ReviewableChange clo " +
                "       WHERE clo.learningObject.id = :id" +
                "       AND clo.reviewed = 0", entity()) //
                .setParameter("id", learningObjectId).getResultList();
    }

    @Deprecated
    public List<ReviewableChange> findAllUnreviewedOld() {
        return getEntityManager()
                .createNativeQuery("SELECT f.*\n" +
                        "FROM ReviewableChange f\n" +
                        "  JOIN LearningObject o ON f.learningObject = o.id\n" +
                        "WHERE f.reviewed = 0\n" +
                        "  AND (o.visibility = 'PUBLIC' OR o.visibility = 'NOT_LISTED')\n" +
                        "  AND NOT exists(SELECT 1 FROM ImproperContent ic " +
                        "                   WHERE ic.learningObject = f.learningObject " +
                        "                   AND ic.reviewed = 0)\n" +
                        "  AND NOT exists(SELECT 1 FROM BrokenContent bc " +
                        "                   WHERE bc.material = f.learningObject" +
                        "                   AND bc.deleted = 0 )" +
                        "  AND NOT exists(SELECT 1 FROM FirstReview bc " +
                        "                   WHERE bc.learningObject = f.learningObject" +
                        "                   AND bc.reviewed = 0 )" +
                        "ORDER BY f.createdAt ASC, f.id ASC", entity())
                .setMaxResults(300)
                .getResultList();
    }

    @Deprecated
    public List<ReviewableChange> findAllUnreviewedOld(User user) {
        return getEntityManager()
                .createNativeQuery("SELECT f.*\n" +
                        "FROM ReviewableChange f\n" +
                        "  JOIN LearningObject o ON f.learningObject = o.id\n" +
                        "  JOIN LearningObject_Taxon lt ON lt.learningObject = o.id\n" +
                        "WHERE f.reviewed = 0\n" +
                        "  AND (o.visibility = 'PUBLIC' OR o.visibility = 'NOT_LISTED')\n" +
                        "  AND NOT exists(SELECT 1 FROM ImproperContent ic " +
                        "                   WHERE ic.learningObject = f.learningObject " +
                        "                   AND ic.reviewed = 0)\n" +
                        "  AND NOT exists(SELECT 1 FROM BrokenContent bc " +
                        "                   WHERE bc.material = f.learningObject" +
                        "                   AND bc.deleted = 0 )" +
                        "  AND NOT exists(SELECT 1 FROM FirstReview bc " +
                        "                   WHERE bc.learningObject = f.learningObject" +
                        "                   AND bc.reviewed = 0 )" +
                        "  AND lt.taxon IN (:taxonIds)\n" +
                        "ORDER BY f.createdAt ASC, f.id ASC", entity())
                .setParameter("taxonIds", taxonDao.getUserTaxonsWithChildren(user))
                .setMaxResults(300)
                .getResultList();
    }


    public List<AdminLearningObject> findAllUnreviewed() {
        return getEntityManager()
                .createQuery("SELECT DISTINCT lo\n" +
                        "FROM AdminLearningObject lo\n" +
                        "JOIN FETCH lo.reviewableChanges r " +
                        "WHERE r.reviewed = 0 " +
                        "   AND (lo.visibility = 'PUBLIC' OR lo.visibility = 'NOT_LISTED')\n" +
                        "   AND lo.id NOT IN(SELECT ic.learningObject\n" +
                        "                     FROM FirstReview ic\n" +
                        "                     WHERE ic.learningObject = lo\n" +
                        "                           AND ic.reviewed = 0)\n" +
                        "   AND lo.id NOT IN(SELECT ic.learningObject\n" +
                        "                     FROM ImproperContent ic\n" +
                        "                     WHERE ic.learningObject = lo\n" +
                        "                           AND ic.reviewed = 0)\n" +
                        "   AND lo.id NOT IN(SELECT ic.material\n" +
                        "                     FROM BrokenContent ic\n" +
                        "                     WHERE ic.material = lo\n" +
                        "                           AND ic.deleted = 0)" +
                        "ORDER BY r.createdAt ASC, r.id ASC", AdminLearningObject.class)
                .setMaxResults(300)
                .getResultList();
    }


    public List<AdminLearningObject> findAllUnreviewed(User user) {
        return getEntityManager()
                .createQuery("SELECT DISTINCT lo\n" +
                        "FROM AdminLearningObject lo\n" +
                        "JOIN FETCH lo.reviewableChanges r " +
                        "JOIN lo.taxons lt " +
                        "WHERE r.reviewed = 0 " +
                        "   AND (lo.visibility = 'PUBLIC' OR lo.visibility = 'NOT_LISTED')\n" +
                        "   AND lo.id NOT IN(SELECT ic.learningObject\n" +
                        "                     FROM FirstReview ic\n" +
                        "                     WHERE ic.learningObject = lo\n" +
                        "                           AND ic.reviewed = 0)\n" +
                        "   AND lo.id NOT IN(SELECT ic.learningObject\n" +
                        "                     FROM ImproperContent ic\n" +
                        "                     WHERE ic.learningObject = lo\n" +
                        "                           AND ic.reviewed = 0)\n" +
                        "   AND lo.id NOT IN(SELECT ic.material\n" +
                        "                     FROM BrokenContent ic\n" +
                        "                     WHERE ic.material = lo\n" +
                        "                           AND ic.deleted = 0)" +
                        "   AND lt.id in (:taxonIds)" +
                        "ORDER BY r.createdAt ASC, r.id ASC", AdminLearningObject.class)
                .setParameter("taxonIds", taxonDao.getUserTaxonsWithChildren(user))
                .setMaxResults(300)
                .getResultList();
    }

    public long findCountOfUnreviewed() {
        return ((BigInteger) getEntityManager()
                .createNativeQuery("SELECT count(1) AS c\n" +
                        "FROM LearningObject lo\n" +
                        "   JOIN ReviewableChange r ON r.learningObject = lo.id\n" +
                        "WHERE (lo.visibility = 'PUBLIC' OR lo.visibility = 'NOT_LISTED')\n" +
                        "  AND r.reviewed = 0 \n" +
                        "  AND lo.id NOT IN(SELECT ic.learningObject FROM ImproperContent ic " +
                        "                   WHERE ic.learningObject = lo.id " +
                        "                   AND ic.reviewed = 0)\n" +
                        "  AND lo.id NOT IN(SELECT ic.material FROM BrokenContent ic " +
                        "                   WHERE ic.material = lo.id" +
                        "                   AND ic.deleted = 0 ) " +
                        "  AND lo.id NOT IN(SELECT ic.learningObject FROM FirstReview ic " +
                        "                   WHERE ic.learningObject = lo.id " +
                        "                   AND ic.reviewed = 0)"
                )
                .getSingleResult()).longValue();
    }

    public long findCountOfUnreviewed(User user) {
        return ((BigInteger) getEntityManager()
                .createNativeQuery("SELECT count(DISTINCT lo.id) AS c\n" +
                        "FROM LearningObject lo\n" +
                        "   JOIN LearningObject_Taxon lt ON lt.learningObject = lo.id\n" +
                        "   JOIN ReviewableChange r ON r.learningObject = lo.id\n" +
                        "WHERE (lo.visibility = 'PUBLIC' OR lo.visibility = 'NOT_LISTED')\n" +
                        "  AND r.reviewed = 0 \n" +
                        "  AND lo.id NOT IN(SELECT ic.learningObject FROM ImproperContent ic " +
                        "                   WHERE ic.learningObject = lo.id " +
                        "                   AND ic.reviewed = 0)\n" +
                        "  AND lo.id NOT IN(SELECT ic.material FROM BrokenContent ic " +
                        "                   WHERE ic.material = lo.id" +
                        "                   AND ic.deleted = 0 ) " +
                        "  AND lo.id NOT IN(SELECT ic.learningObject FROM FirstReview ic " +
                        "                   WHERE ic.learningObject = lo.id " +
                        "                   AND ic.reviewed = 0)" +
                        "  AND lt.taxon IN (:taxonIds)")
                .setParameter("taxonIds", taxonDao.getUserTaxonsWithChildren(user))
                .getSingleResult()).longValue();
    }
}
