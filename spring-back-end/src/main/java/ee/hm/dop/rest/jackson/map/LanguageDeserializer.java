package ee.hm.dop.rest.jackson.map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ee.hm.dop.model.Language;
import ee.hm.dop.service.metadata.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Converts JSON language code to Language object
 */
@Component
@AllArgsConstructor
public class LanguageDeserializer extends JsonDeserializer<Language> {

    private LanguageService languageService;

    @Override
    public Language deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
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
