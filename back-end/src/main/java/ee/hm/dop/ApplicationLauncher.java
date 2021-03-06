package ee.hm.dop;

import com.google.inject.Singleton;
import ee.hm.dop.config.EmbeddedJetty;
import ee.hm.dop.config.guice.GuiceInjector;
import ee.hm.dop.service.synchronizer.*;
import org.apache.commons.configuration2.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.xml.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.Executors;

import static ee.hm.dop.utils.ConfigurationProperties.SERVER_PORT;
import static java.lang.String.format;
import static org.opensaml.DefaultBootstrap.SYSPROP_HTTPCLIENT_HTTPS_DISABLE_HOSTNAME_VERIFICATION;

@Singleton
public class ApplicationLauncher {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationLauncher.class);

    private static final int DEFAULT_SERVER_PORT = 8080;
    private static final int MATERIAL_SYNCHRONIZATION_HOUR_OF_DAY = 1;
    private static final int AUTOMATICALLY_ACCEPT_REVIEWABLE_CHANGES_HOUR_OF_DAY = 2;
    private static final int AUTHENTICATION_STATE_CLEANER_HOUR_OF_DAY = 3;
    private static final int AUTHENTICATED_USER_CLEANER_HOUR_OF_DAY = 4;
    private static final int EHIS_INSTITUTION_SYNCHRONIZATION_HOUR_OF_DAY = 5;

    @Inject
    private static Configuration configuration;
    @Inject
    private static SynchronizeMaterialsExecutor synchronizeMaterialsExecutor;
    @Inject
    private static AutomaticallyAcceptReviewableChange automaticallyAcceptReviewableChange;
    @Inject
    private static AuthenticationStateCleaner authenticationStateCleaner;
    @Inject
    private static AuthenticatedUserCleaner authenticatedUserCleaner;
    @Inject
    private static EhisInstitutionUpdateExecutor ehisInstitutionUpdateExecutor;
    @Inject
    private static UpdatePortfolioMaterialsExecutor updatePortfolioMaterialsExecutor;


    public static void startApplication() {
        GuiceInjector.init();

        if (ApplicationManager.isApplicationRunning()) {
            logger.warn("Unable to start. Application is already running.");
        } else {
            startServer();
            addShutdownHook();
            startCommandListener();
            startExecutors();
            initOpenSaml();
            synchronizeMaterials();
            acceptReviewableChange();
            authenticatedUserCleaner();
            authenticationStateCleaner();
            ehisInstitutionUpdateExecutor();
            updatePortfolioMaterials();
        }
    }

    private static void acceptReviewableChange() {
        Executors.newSingleThreadExecutor().submit(() -> automaticallyAcceptReviewableChange.run());
    }

    private static void synchronizeMaterials() {
        Executors.newSingleThreadExecutor().submit(() -> synchronizeMaterialsExecutor.run());
    }

    private static void authenticationStateCleaner() {
        Executors.newSingleThreadExecutor().submit(() -> authenticationStateCleaner.run());
    }

    private static void authenticatedUserCleaner() {
        Executors.newSingleThreadExecutor().submit(() -> authenticatedUserCleaner.run());
    }

    private static void ehisInstitutionUpdateExecutor() {
        Executors.newSingleThreadExecutor().submit(() -> ehisInstitutionUpdateExecutor.run());
    }
    private static void updatePortfolioMaterials() {
        Executors.newSingleThreadExecutor().submit(() -> updatePortfolioMaterialsExecutor.run());
    }

    private static void startExecutors() {
        synchronizeMaterialsExecutor.scheduleExecution(MATERIAL_SYNCHRONIZATION_HOUR_OF_DAY);
        automaticallyAcceptReviewableChange.scheduleExecution(AUTOMATICALLY_ACCEPT_REVIEWABLE_CHANGES_HOUR_OF_DAY);
        authenticationStateCleaner.scheduleExecution(AUTHENTICATION_STATE_CLEANER_HOUR_OF_DAY);
        authenticatedUserCleaner.scheduleExecution(AUTHENTICATED_USER_CLEANER_HOUR_OF_DAY);
        ehisInstitutionUpdateExecutor.scheduleExecution(EHIS_INSTITUTION_SYNCHRONIZATION_HOUR_OF_DAY);
    }

    private static void startCommandListener() {
        Thread commandListener = new Thread(new ApplicationManager.CommandListener());
        commandListener.setName("command-listener");
        commandListener.setDaemon(true);
        commandListener.start();
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(ApplicationLauncher::stopServer, "shutdown-hook"));
    }

    private static void startServer() {
        try {
            int port = configuration.getInt(SERVER_PORT, DEFAULT_SERVER_PORT);
            logger.info(format("Starting application server on port [%s]", port));
            EmbeddedJetty.instance().start(port);
        } catch (Exception e) {
            logger.error("Error inicializing Jetty Server. Existing application.", e);
            System.exit(1);
        }
    }

    private static void initOpenSaml() {
        try {
            System.setProperty(SYSPROP_HTTPCLIENT_HTTPS_DISABLE_HOSTNAME_VERIFICATION, "true");
            DefaultBootstrap.bootstrap();
        } catch (ConfigurationException e) {
            logger.error("Error initializing OpenSAML library.");
        }
    }

    synchronized private static void stopServer() {
        logger.info("Stopping server...");
        try {
            EmbeddedJetty.instance().stop();
            logger.info("Server successfully stopped");
        } catch (Exception e) {
            logger.info("Error stopping server!", e);
        }
    }

    public static void stopApplication() throws Exception {
        GuiceInjector.init();
        ApplicationManager.stopApplication();
        stopExecutors();
    }

    private static void stopExecutors() {
        synchronizeMaterialsExecutor.stop();
        automaticallyAcceptReviewableChange.stop();
        logger.info("Executors have been stopped");
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "start".equalsIgnoreCase(args[0])) {
            startApplication();
        } else if ("stop".equalsIgnoreCase(args[0])) {
            stopApplication();
            logger.info("Everything is stopped, exiting system");
            System.exit(1);
        } else {
            logger.warn("Command does not exist. Use: start, stop or no command (default is start).");
        }
    }
}
