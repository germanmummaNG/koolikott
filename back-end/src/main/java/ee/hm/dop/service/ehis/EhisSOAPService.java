package ee.hm.dop.service.ehis;

import ee.hm.dop.model.ehis.Person;
import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;

import static ee.hm.dop.utils.ConfigurationProperties.*;
import static java.lang.String.format;

public class EhisSOAPService implements IEhisSOAPService {

    private static Logger logger = LoggerFactory.getLogger(EhisSOAPService.class);
    @Inject
    private EhisParser ehisParser;
    @Inject
    private Configuration configuration;
    @Inject
    private SOAPConnection connection;
    @Inject
    private EhisV5RequestBuilder ehisV5RequestBuilder;
    @Inject
    private EhisV6RequestBuilder ehisV6RequestBuilder;
    @Inject
    private EhisV5ResponseAnalyzer ehisV5ResponseAnalyzer;
    @Inject
    private EhisV6ResponseAnalyzer ehisV6ResponseAnalyzer;

    @Override
    public Person getPersonInformation(String idCode) {
        try {
            boolean useV6 = configuration.getBoolean(XROAD_EHIS_USE_V6);
            SOAPMessage message;
            if (useV6) {
                message = ehisV6RequestBuilder.createGetPersonInformationSOAPMessage(idCode);
            } else {
                message = ehisV5RequestBuilder.createGetPersonInformationSOAPMessage(idCode);
            }

            if (logger.isInfoEnabled()) {
                log(message, "Sending message to EHIS: %s");
            }

            SOAPMessage response = sendSOAPMessage(message);

            if (logger.isInfoEnabled()) {
                log(response, "Received response from EHIS: %s");
            }

            String xmlResponse;
            if (useV6) {
                xmlResponse = ehisV6ResponseAnalyzer.parseSOAPResponse(response);
            } else {
                xmlResponse = ehisV5ResponseAnalyzer.parseSOAPResponse(response);
            }
            logger.info(format("Received response from EHIS: %s", xmlResponse));
            return ehisParser.parse(xmlResponse);
        } catch (Exception e) {
            logger.error("Error getting User information from EHIS.", e);
            return null;
        }
    }

    private void log(SOAPMessage message, String msg) throws SOAPException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        message.writeTo(out);
        String strMsg = new String(out.toByteArray(), StandardCharsets.UTF_8);
        logger.info(format(msg, strMsg));
    }

    private SOAPMessage sendSOAPMessage(SOAPMessage message) throws SOAPException, IOException {
        boolean useV6 = configuration.getBoolean(XROAD_EHIS_USE_V6);
        String endpoint = useV6 ? configuration.getString(XROAD_EHIS_V6_ENDPOINT) : configuration.getString(EHIS_ENDPOINT);

//        return connection.call(message, endpoint);
        try {
            return connection.call(message, addSoapTimeout(endpoint));
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException", e);
            throw e;
        } catch (IOException e) {
            logger.error("IOException", e);
            throw e;
        }
    }

    private URL addSoapTimeout(String endpoint) throws IOException {
        return new URL(new URL(endpoint), endpoint,
                new URLStreamHandler() {
                    @Override
                    protected URLConnection openConnection(URL url) throws IOException {
                        URL target = new URL(url.toString());
                        URLConnection connection = target.openConnection();
                        connection.setConnectTimeout(configuration.getInt(XROAD_EHIS_TIMEOUT_CONNECT));
                        connection.setReadTimeout(configuration.getInt(XROAD_EHIS_TIMEOUT_READ));
                        return (connection);
                    }
                });
    }
}
