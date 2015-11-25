package ee.hm.dop.oaipmh.estcore;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;

import ee.hm.dop.model.Author;
import ee.hm.dop.model.Domain;
import ee.hm.dop.model.EducationalContext;
import ee.hm.dop.model.Language;
import ee.hm.dop.model.LanguageString;
import ee.hm.dop.model.Material;
import ee.hm.dop.model.ResourceType;
import ee.hm.dop.model.Tag;
import ee.hm.dop.model.Taxon;
import ee.hm.dop.oaipmh.ParseException;
import ee.hm.dop.service.AuthorService;
import ee.hm.dop.service.LanguageService;
import ee.hm.dop.service.ResourceTypeService;
import ee.hm.dop.service.TagService;
import ee.hm.dop.service.TaxonService;

/**
 * Created by mart on 6.11.15.
 */
@RunWith(EasyMockRunner.class)
public class MaterialParserEstCoreTest {

    @TestSubject
    private MaterialParserEstCore materialParser = new MaterialParserEstCore();

    @Mock
    private LanguageService languageService;

    @Mock
    private AuthorService authorService;

    @Mock
    private TagService tagService;

    @Mock
    private ResourceTypeService resourceTypeService;

    @Mock
    private TaxonService taxonService;

    @Test(expected = ee.hm.dop.oaipmh.ParseException.class)
    public void parseXMLisNull() throws ParseException {
        materialParser.parse(null);
    }

    @Test(expected = ee.hm.dop.oaipmh.ParseException.class)
    public void parseDocumentIsEmpty() throws ParseException {
        Document document = createMock(Document.class);
        materialParser.parse(document);
    }

    @Test
    public void parse() throws Exception {
        File fXmlFile = getResourceAsFile("oaipmh/estcore/parseEstcore.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Language english = new Language();
        english.setId(1L);
        english.setName("English");

        Language estonian = new Language();
        estonian.setId(2L);
        estonian.setName("Estonian");

        Author author1 = new Author();
        author1.setName("Jonathan");
        author1.setSurname("Doe");

        Author author2 = new Author();
        author2.setName("Andrew");
        author2.setSurname("Balaam");

        LanguageString description1 = new LanguageString();
        description1.setLanguage(english);
        description1.setText("description 1");

        LanguageString description2 = new LanguageString();
        description2.setLanguage(estonian);
        description2.setText("description 2");

        Tag tag1 = new Tag();
        tag1.setId(325L);
        tag1.setName("tag1");
        Tag tag2 = new Tag();
        tag2.setId(326L);
        tag2.setName("tag2");

        ResourceType resourceType1 = new ResourceType();
        resourceType1.setId(448L);
        resourceType1.setName("AUDIO");

        ResourceType resourceType2 = new ResourceType();
        resourceType2.setId(559L);
        resourceType2.setName("VIDEO");

        EducationalContext educationalContext1 = new EducationalContext();
        educationalContext1.setName("PRESCHOOLEDUCATION");

        EducationalContext educationalContext2 = new EducationalContext();
        educationalContext2.setName("BASICEDUCATION");

        EducationalContext educationalContext3 = new EducationalContext();
        educationalContext3.setName("SECONDARYEDUCATION");

        Domain domain1 = new Domain();
        domain1.setName("Me_and_the_environment");
        domain1.setEducationalContext(educationalContext2);

        Domain domain2 = new Domain();
        domain2.setName("Mathematics");
        domain2.setEducationalContext(educationalContext1);

        expect(languageService.getLanguage("en")).andReturn(english).times(3);
        expect(languageService.getLanguage("et")).andReturn(estonian).times(2);
        expect(authorService.getAuthorByFullName(author1.getName(), author1.getSurname())).andReturn(author1);
        expect(authorService.getAuthorByFullName(author2.getName(), author2.getSurname())).andReturn(author2);
        expect(tagService.getTagByName(tag1.getName())).andReturn(tag1);
        expect(tagService.getTagByName(tag2.getName())).andReturn(tag2);
        expect(resourceTypeService.getResourceTypeByName(resourceType1.getName())).andReturn(resourceType1);
        expect(resourceTypeService.getResourceTypeByName(resourceType2.getName())).andReturn(resourceType2);
        expect(taxonService.getTaxonByEstCoreName(educationalContext1.getName())).andReturn(
                educationalContext1).anyTimes();
        expect(taxonService.getTaxonByEstCoreName(educationalContext2.getName())).andReturn(
                educationalContext2).anyTimes();
        expect(taxonService.getTaxonByEstCoreName(educationalContext3.getName())).andReturn(
                educationalContext3).anyTimes();
        expect(taxonService.getTaxonByEstCoreName("Mina ja keskkond")).andReturn(domain1).times(2);
        expect(taxonService.getTaxonByEstCoreName(domain2.getName())).andReturn(domain2);

        LanguageString title1 = new LanguageString();
        title1.setLanguage(english);
        title1.setText("first title");

        LanguageString title2 = new LanguageString();
        title2.setLanguage(estonian);
        title2.setText("teine pealkiri");

        List<LanguageString> titles = new ArrayList<>();
        titles.add(title1);
        titles.add(title2);

        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

        List<LanguageString> descriptions = new ArrayList<>();
        descriptions.add(description1);
        descriptions.add(description2);

        List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        List<ResourceType> resourceTypes = new ArrayList<>();
        resourceTypes.add(resourceType1);
        resourceTypes.add(resourceType2);

        List<Taxon> taxon = new ArrayList<>();
        taxon.add(educationalContext1);
        taxon.add(educationalContext2);
        taxon.add(educationalContext3);
        taxon.add(domain1);
        taxon.add(domain2);

        replay(languageService, authorService, tagService, resourceTypeService, taxonService);

        Document doc = dBuilder.parse(fXmlFile);
        Material material = materialParser.parse(doc);

        verify(languageService, authorService, tagService, resourceTypeService, taxonService);

        assertEquals(titles, material.getTitles());
        assertEquals("https://oxygen.netgroupdigital.com/rest/repoMaterialSource", material.getSource());
        assertEquals(english, material.getLanguage());
        assertEquals(authors, material.getAuthors());
        assertEquals(descriptions, material.getDescriptions());
        assertEquals(tags, material.getTags());
        assertEquals(resourceTypes, material.getResourceTypes());
    }

    private File getResourceAsFile(String resourcePath) throws URISyntaxException {
        URI resource = getClass().getClassLoader().getResource(resourcePath).toURI();
        return new File(resource);
    }
}
