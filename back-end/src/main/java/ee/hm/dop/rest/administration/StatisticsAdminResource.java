package ee.hm.dop.rest.administration;

import ee.hm.dop.model.enums.RoleString;
import ee.hm.dop.rest.BaseResource;
import ee.hm.dop.service.reviewmanagement.dto.FileFormat;
import ee.hm.dop.service.reviewmanagement.dto.StatisticsFilterDto;
import ee.hm.dop.service.reviewmanagement.newdto.NewStatisticsResult;
import ee.hm.dop.service.statistics.NewStatisticsCsvExporter;
import ee.hm.dop.service.statistics.NewStatisticsExcelExporter;
import ee.hm.dop.service.statistics.NewStatisticsService;
import ee.hm.dop.utils.DOPFileUtils;
import ee.hm.dop.utils.DopConstants;
import ee.hm.dop.utils.io.CsvUtil;
import org.apache.commons.io.FileUtils;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.UnsupportedEncodingException;


@Path("admin/statistics/")
public class StatisticsAdminResource extends BaseResource {

    private static final String TEMP_FOLDER = CsvUtil.TEMP_FOLDER;

    @Inject
    private NewStatisticsService statisticsService;
    @Inject
    private NewStatisticsExcelExporter statisticsExcelExporter;
    @Inject
    private NewStatisticsCsvExporter statisticsCsvExporter;

    @POST
    @RolesAllowed({RoleString.ADMIN})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public NewStatisticsResult newsearch(StatisticsFilterDto searchFilter) {
        if (searchFilter == null || !searchFilter.isValidSearch()) {
            throw badRequest("Search parameters invalid");
        }
        return statisticsService.statistics(searchFilter, getLoggedInUser());
    }

    @GET
    @Path("export/download/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("filename") String filename) {
        String[] split = filename.split("\\.");
        return buildResponse(filename, FileFormat.valueOf(split[1]));
    }

    @POST
    @Path("export")
    @RolesAllowed({RoleString.ADMIN})
    @Consumes(MediaType.APPLICATION_JSON)
    public String searchExport(StatisticsFilterDto filter) {
        if (filter == null || !filter.isValidExportRequest()) {
            throw badRequest("Search parameters invalid");
        }

        String filename = CsvUtil.getUniqueFileName(filter.getFormat());
        NewStatisticsResult statistics = statisticsService.statistics(filter, getLoggedInUser());
        if (filter.getFormat() == FileFormat.xlsx) {
            statisticsExcelExporter.generateXlsx(filename, statistics);
        } else if (filter.getFormat() == FileFormat.xls) {
            statisticsExcelExporter.generateXls(filename, statistics);
        } else if (filter.getFormat() == FileFormat.csv) {
            statisticsCsvExporter.generate(filename, statistics);
        } else {
            throw new IllegalStateException("FileFormat is in unknown state: " + filter.getFormat());
        }
        return new File(filename).getName();
    }

    private Response buildResponse(String filename, FileFormat format) {
        String mediaType = DOPFileUtils.probeForMediaType(filename);
        String fileName = "statistika_aruanne." + format.name();

        try {
            File file = FileUtils.getFile(TEMP_FOLDER + "/" + filename);
            return Response.ok(file, mediaType)
                    .header(DopConstants.CONTENT_DISPOSITION, "Attachment; filename*=\"UTF-8''" + DOPFileUtils.encode(fileName) + "\"; filename=\"" + fileName + "\"")
                    .build();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
