package ee.hm.dop.utils;

public interface ConfigurationProperties {

    // Database
    String DATABASE_URL = "db.url";
    String DATABASE_USERNAME = "db.username";
    String DATABASE_PASSWORD = "db.password";

    // Server
    String SERVER_PORT = "server.port";
    String COMMAND_LISTENER_PORT = "command.listener.port";

    // Search
    String SEARCH_SERVER = "search.server";

    // TAAT
    String TAAT_SSO = "taat.sso";
    String TAAT_CONNECTION_ID = "taat.connectionId";
    String TAAT_ASSERTION_CONSUMER_SERVICE_INDEX = "taat.assertionConsumerServiceIndex";

    String TAAT_METADATA_FILEPATH = "taat.metadata.filepath";
    String TAAT_METADATA_ENTITY_ID = "taat.metadata.entityID";

    // Keystore for TAAT
    String KEYSTORE_FILENAME = "keystore.filename";
    String KEYSTORE_PASSWORD = "keystore.password";
    String KEYSTORE_SIGNING_ENTITY_ID = "keystore.signingEntityID";
    String KEYSTORE_SIGNING_ENTITY_PASSWORD = "keystore.signingEntityPassword";

}
