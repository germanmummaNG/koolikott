package ee.hm.dop.rest.jackson.map;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ee.hm.dop.config.guice.GuiceInjector;
import ee.hm.dop.model.Language;
import ee.hm.dop.service.metadata.LanguageService;

/**
 * Converts JSON language code to Language object
 */
public class LanguageDeserializer extends JsonDeserializer<Language> {

    @Override
    public Language deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        LanguageService languageService = GuiceInjector.getInjector().getInstance(LanguageService.class);
        if (isEmpty(jp.getText())) {
            return null;
        }
        Language language = languageService.getLanguage(jp.getText());
        if (language == null) {
            throw new RuntimeException("Language does not exist " + jp.getText());
        }
        return language;
    }
}
