package ee.hm.dop.service.proxy;

import ee.hm.dop.utils.DopConstants;
import ee.hm.dop.utils.UrlUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.UnknownHostException;

import static java.lang.String.format;

/**
 * Sometimes material has a link to pdf from another site
 * When we embedded, responsible browser expects the content to have the same origin as our origin
 * (ekoolikott.ee)
 * In case origins differ, we must create a substitute link
 */
public class MaterialProxy {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Response getProxyUrl(Long id, String url_param) throws IOException {
        HttpClient client = new HttpClient();
        client.setHostConfiguration(hostConfig(url_param));
        GetMethod get = getMethod(url_param);

        try {
            client.executeMethod(get);
        } catch (UnknownHostException | IllegalArgumentException | ConnectException e) {
            logger.info("Could not contact host {}. LearningObject id: {} Error: {}. Returning empty response.", url_param, id, e.getMessage(), e);
            return noContent();
        }

        String attachmentLocation = attachmentLocation(get, DopConstants.PDF_EXTENSION, DopConstants.PDF_MIME_TYPE);
        if (attachmentLocation.equals(DopConstants.CONTENT_DISPOSITION)) {
            String contentDisposition = get.getResponseHeaders(DopConstants.CONTENT_DISPOSITION)[0].getValue().replace("attachment", "Inline");
            return buildContentDispositionResponse(get.getResponseBodyAsStream(), contentDisposition);
        }
        if (attachmentLocation.equals(DopConstants.CONTENT_TYPE)) {
            // Content-Disposition is missing, try to extract the filename from url instead
            String fileName = url_param.substring(url_param.lastIndexOf("/") + 1);
            String contentDisposition = format("Inline; filename=\"%s\"", fileName);
            return buildContentDispositionResponse(get.getResponseBodyAsStream(), contentDisposition);
        }
        return noContent();
    }

    private Response buildContentDispositionResponse(InputStream responseBody, String contentDisposition) throws IOException {
        return Response.ok(responseBody, DopConstants.PDF_MIME_TYPE).header(DopConstants.CONTENT_DISPOSITION,
                contentDisposition).build();
    }

    private HostConfiguration hostConfig(String url_param) {
        HostConfiguration hostConfiguration = new HostConfiguration();
        hostConfiguration.setHost(UrlUtil.getHost(url_param));
        return hostConfiguration;
    }

    private GetMethod getMethod(String url_param) throws URIException {
        try {
            return new GetMethod(url_param);
        } catch (IllegalArgumentException e) {
            return new GetMethod(URIUtil.encodePath(url_param));
        }
    }

    private String attachmentLocation(GetMethod get, String extension, String mime_type) {
        Header[] contentDisposition = get.getResponseHeaders(DopConstants.CONTENT_DISPOSITION);
        Header[] contentType = get.getResponseHeaders(DopConstants.CONTENT_TYPE);
        if (contentDisposition.length > 0 && contentDisposition[0].getValue().toLowerCase().endsWith(extension + "\"")) {
            return DopConstants.CONTENT_DISPOSITION;
        }
        if (contentType.length > 0 && contentType[0].getValue().toLowerCase().endsWith(mime_type)) {
            return DopConstants.CONTENT_TYPE;
        }
        return "Invalid";
    }

    private Response noContent() {
        return Response.noContent().build();
    }
}
