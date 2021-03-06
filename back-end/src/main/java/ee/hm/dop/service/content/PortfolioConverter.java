package ee.hm.dop.service.content;

import ee.hm.dop.dao.OriginalPictureDao;
import ee.hm.dop.model.Chapter;
import ee.hm.dop.model.ChapterBlock;
import ee.hm.dop.model.ChapterBlockLog;
import ee.hm.dop.model.ChapterLog;
import ee.hm.dop.model.OriginalPicture;
import ee.hm.dop.model.Portfolio;
import ee.hm.dop.model.PortfolioLog;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PortfolioConverter {

    @Inject
    private OriginalPictureDao originalPictureDao;

    public Portfolio setFieldsToNewPortfolio(Portfolio portfolio) {
        Portfolio safePortfolio = new Portfolio();
        return setCommonFields(safePortfolio, portfolio);
    }

    public PortfolioLog setFieldsToNewPortfolioLog(Portfolio portfolio) {
        PortfolioLog portfolioLog = new PortfolioLog();
        portfolioLog.setLearningObject(portfolio.getId());
        portfolioLog.setCreator(portfolio.getCreator());
        portfolioLog.setVisibility(portfolio.getVisibility());
        portfolioLog.setOriginalCreator(portfolio.getOriginalCreator());
        return setCommonFieldsToPortfolioLog(portfolioLog, portfolio);
    }

    public Portfolio setFieldsToExistingPortfolio(Portfolio to, Portfolio from) {
        setCommonFields(to, from);
        setUpdateFields(to, from);
        return to;
    }

    private void setUpdateFields(Portfolio to, Portfolio from) {
        if (changesToPublic(to, from)) to.setPublishedAt(DateTime.now());
        to.setVisibility(from.getVisibility());
        to.setPublicationConfirmed(from.isPublicationConfirmed());
    }

    private boolean changesToPublic(Portfolio to, Portfolio from) {
        return from.getVisibility().isPublic() && to.getVisibility().isNotPublic();
    }

    private Portfolio setCommonFields(Portfolio to, Portfolio from) {
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

    private PortfolioLog setCommonFieldsToPortfolioLog(PortfolioLog to, Portfolio from) {
        to.setTitle(from.getTitle());
        to.setSummary(from.getSummary());
        to.setTags(from.getTags());
        to.setTargetGroups(from.getTargetGroups());
        to.setTaxons(from.getTaxons());
        List<ChapterLog> chapterLogs = from.getChapters()
                .stream()
                .map(convertChapterToLog)
                .collect(Collectors.toList());
        to.setChapters(chapterLogs);
        to.setPicture((OriginalPicture) from.getPicture());
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

    private Function<Chapter, ChapterLog> convertChapterToLog = this::convertChapter;

    private ChapterLog convertChapter(Chapter chapter) {
        ChapterLog chapterLog = new ChapterLog();
        chapterLog.setId(chapter.getId());
        chapterLog.setTitle(chapter.getTitle());

        List<ChapterBlockLog> chapterBlockLogs = chapter
                .getBlocks()
                .stream()
                .map(convertChapterBlockToLog)
                .collect(Collectors.toList());

        chapterLog.setBlocks(chapterBlockLogs);

        return chapterLog;
    }

    private Function<ChapterBlock, ChapterBlockLog> convertChapterBlockToLog = PortfolioConverter::convertChapterBlock;

    private static ChapterBlockLog convertChapterBlock(ChapterBlock chapter) {
        ChapterBlockLog chapterBlockLog = new ChapterBlockLog();
        chapterBlockLog.setId(chapter.getId());
        chapterBlockLog.setHtmlContent(chapter.getHtmlContent());
        return chapterBlockLog;
    }
}
