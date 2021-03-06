package ee.hm.dop.service.reviewmanagement.newdto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ee.hm.dop.model.User;
import ee.hm.dop.model.taxon.Domain;
import ee.hm.dop.model.taxon.EducationalContext;
import ee.hm.dop.model.taxon.Subject;
import ee.hm.dop.rest.jackson.map.TaxonDeserializer;
import ee.hm.dop.rest.jackson.map.TaxonSerializer;

import java.util.List;

public class NewStatisticsRow {

    private User user;
    @JsonSerialize(using = TaxonSerializer.class)
    @JsonDeserialize(using = TaxonDeserializer.class)
    private EducationalContext educationalContext;
    @JsonSerialize(using = TaxonSerializer.class)
    @JsonDeserialize(using = TaxonDeserializer.class)
    private Domain domain;
    /**
     * domain row does not contain subject
     */
    @JsonSerialize(using = TaxonSerializer.class)
    @JsonDeserialize(using = TaxonDeserializer.class)
    private Subject subject;
    private List<NewStatisticsRow> subjects;
    private boolean noUsersFound;
    private boolean domainUsed;
    private Long reviewedLOCount;
    private Long approvedReportedLOCount;
    private Long deletedReportedLOCount;
    private Long acceptedChangedLOCount;
    private Long rejectedChangedLOCount;
    private Long portfolioCount;
    private Long publicPortfolioCount;
    private Long materialCount;
    private String domainTranslation;
    private String subjectTranslation;

    public String getDomainTranslation() {
        return domainTranslation;
    }

    public void setDomainTranslation(String domainTranslation) {
        this.domainTranslation = domainTranslation;
    }

    public String getSubjectTranslation() {
        return subjectTranslation;
    }

    public void setSubjectTranslation(String subjectTranslation) {
        this.subjectTranslation = subjectTranslation;
    }

    public List<NewStatisticsRow> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<NewStatisticsRow> subjects) {
        this.subjects = subjects;
    }

    public boolean isNoUsersFound() {
        return noUsersFound;
    }

    public void setNoUsersFound(boolean noUsersFound) {
        this.noUsersFound = noUsersFound;
    }

    public boolean isDomainUsed() {
        return domainUsed;
    }

    public void setDomainUsed(boolean domainUsed) {
        this.domainUsed = domainUsed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EducationalContext getEducationalContext() {
        return educationalContext;
    }

    public void setEducationalContext(EducationalContext educationalContext) {
        this.educationalContext = educationalContext;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Long getReviewedLOCount() {
        return reviewedLOCount;
    }

    public void setReviewedLOCount(Long reviewedLOCount) {
        this.reviewedLOCount = reviewedLOCount;
    }

    public Long getApprovedReportedLOCount() {
        return approvedReportedLOCount;
    }

    public void setApprovedReportedLOCount(Long approvedReportedLOCount) {
        this.approvedReportedLOCount = approvedReportedLOCount;
    }

    public Long getDeletedReportedLOCount() {
        return deletedReportedLOCount;
    }

    public void setDeletedReportedLOCount(Long deletedReportedLOCount) {
        this.deletedReportedLOCount = deletedReportedLOCount;
    }

    public Long getAcceptedChangedLOCount() {
        return acceptedChangedLOCount;
    }

    public void setAcceptedChangedLOCount(Long acceptedChangedLOCount) {
        this.acceptedChangedLOCount = acceptedChangedLOCount;
    }

    public Long getRejectedChangedLOCount() {
        return rejectedChangedLOCount;
    }

    public void setRejectedChangedLOCount(Long rejectedChangedLOCount) {
        this.rejectedChangedLOCount = rejectedChangedLOCount;
    }

    public Long getPortfolioCount() {
        return portfolioCount;
    }

    public void setPortfolioCount(Long portfolioCount) {
        this.portfolioCount = portfolioCount;
    }

    public Long getPublicPortfolioCount() {
        return publicPortfolioCount;
    }

    public void setPublicPortfolioCount(Long publicPortfolioCount) {
        this.publicPortfolioCount = publicPortfolioCount;
    }

    public Long getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(Long materialCount) {
        this.materialCount = materialCount;
    }
}
