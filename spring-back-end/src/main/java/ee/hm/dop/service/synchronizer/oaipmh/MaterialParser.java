package ee.hm.dop.service.synchronizer.oaipmh;

import ee.hm.dop.model.Author;
import ee.hm.dop.model.IssueDate;
import ee.hm.dop.model.Language;
import ee.hm.dop.model.LanguageString;
import ee.hm.dop.model.Material;
import ee.hm.dop.model.PeerReview;
import ee.hm.dop.model.Publisher;
import ee.hm.dop.model.ResourceType;
import ee.hm.dop.model.Tag;
import ee.hm.dop.model.TargetGroup;
import ee.hm.dop.model.taxon.Domain;
import ee.hm.dop.model.taxon.EducationalContext;
import ee.hm.dop.model.taxon.Module;
import ee.hm.dop.model.taxon.Specialization;
import ee.hm.dop.model.taxon.Subject;
import ee.hm.dop.model.taxon.Subtopic;
import ee.hm.dop.model.taxon.Taxon;
import ee.hm.dop.model.taxon.Topic;
import ee.hm.dop.service.author.AuthorService;
import ee.hm.dop.service.author.PublisherService;
import ee.hm.dop.service.metadata.LanguageService;
import ee.hm.dop.service.metadata.ResourceTypeService;
import ee.hm.dop.service.metadata.TagService;
import ee.hm.dop.service.metadata.TargetGroupService;
import ee.hm.dop.service.metadata.TaxonService;
import ee.hm.dop.service.useractions.PeerReviewService;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.time.LocalDateTime;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.inject.Inject;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ee.hm.dop.service.synchronizer.oaipmh.MaterialParserUtil.getFirst;
import static ee.hm.dop.service.synchronizer.oaipmh.MaterialParserUtil.nodeStreamOf;
import static ee.hm.dop.service.synchronizer.oaipmh.MaterialParserUtil.notEmpty;
import static ee.hm.dop.service.synchronizer.oaipmh.MaterialParserUtil.value;
import static ee.hm.dop.service.synchronizer.oaipmh.MaterialParserUtil.valueToUpper;

@Slf4j
public abstract class MaterialParser {

    public static final String NO_SUCH_LANGUAGE_FOR_S_LANGUAGE_STRING_WILL_HAVE_NO_LANGUAGE = "No such language for '%s'. LanguageString will have no Language";
    public static final String MATERIAL_HAS_MORE_OR_LESS_THAN_ONE_SOURCE_CAN_T_BE_MAPPED = "Material has more or less than one source, can't be mapped.";
    public static final String ERROR_PARSING_DOCUMENT_INVALID_URL_S = "Error parsing document. Invalid URL %s";
    public static final String ERROR_PARSING_DOCUMENT_SOURCE = "Error parsing document source.";
    public static final String TAXON_PATH = "./*[local-name()='taxonPath']";
    public static final String PUBLISHER = "PUBLISHER";
    public static final String AUTHOR = "AUTHOR";
    private static final UrlValidator URL_VALIDATOR = new UrlValidator(new String[]{"http", "https"});
    private static final Map<String, String> taxonMap = MaterialParserUtil.getTaxonMap();
    private static final XPath xpath = XPathFactory.newInstance().newXPath();

    @Inject
    private ResourceTypeService resourceTypeService;
    @Inject
    private PeerReviewService peerReviewService;
    @Inject
    private PublisherService publisherService;
    @Inject
    private AuthorService authorService;
    @Inject
    private TargetGroupService targetGroupService;
    @Inject
    private TaxonService taxonService;

    public Material parse(Document doc) throws ParseException {
        try {
            Material material = new Material();
            doc.getDocumentElement().normalize();

            setIdentifier(material, doc);
            setContributorsData(material, doc);
            setTitles(material, doc);
            setLanguage(material, doc);
            setDescriptions(material, doc);
            setSource(material, doc);
            setEmbedSource(material, doc);
            setTags(material, doc);
            setLearningResourceType(material, doc);
            setPeerReview(material, doc);
            setTaxon(material, doc);
            setCrossCurricularThemes(material, doc);
            setKeyCompetences(material, doc);
            setIsPaid(material, doc);
            setTargetGroups(material, doc);
            setPicture(material, doc);
            removeDuplicateTaxons(material);
            return material;
        } catch (RuntimeException e) {
            logFail(e);
            throw new ParseException(e);
        }

    }

    protected void setContributorsData(Material material, Document doc) {
        try {
            setAuthors(doc, material);
            setPublishersData(doc, material);
        } catch (Exception e) {
            log.debug("Error while setting authors or publishers.", e.getMessage());
        }
    }

    private void removeDuplicateTaxons(Material material) {
        List<Taxon> taxons = material.getTaxons();
        List<Taxon> uniqueTaxons = new ArrayList<>(taxons);

        for (int i = 0; i < taxons.size(); i++) {
            Taxon first = taxons.get(i);

            for (int j = 0; j < taxons.size(); j++) {
                Taxon second = taxons.get(j);

                if (second.containsTaxon(first) && j != i) {
                    uniqueTaxons.remove(first);
                } else if (first.containsTaxon(second) && j != i) {
                    uniqueTaxons.remove(second);
                }
            }
        }
        material.setTaxons(uniqueTaxons);
    }

    private void setIdentifier(Material material, Document doc) {
        Element header = (Element) doc.getElementsByTagName("header").item(0);
        Node identifier = getFirst(header, "identifier");
        material.setRepositoryIdentifier(value(identifier));
    }

    protected void setAuthorFromVCard(List<Author> authors, String data) {
        if (data.length() > 0) {
            VCard vcard = Ezvcard.parse(data).first();
            String name = vcard.getStructuredName().getGiven();
            String surname = vcard.getStructuredName().getFamily();

            if (name != null && surname != null) {
                Author author = authorService.getAuthorByFullName(name, surname);
                if (author == null) {
                    authors.add(authorService.createAuthor(name, surname));
                } else if (!authors.contains(author)) {
                    authors.add(author);
                }
            }
        }
    }

    private void setPublisherFromVCard(List<Publisher> publishers, String data) {
        if (StringUtils.isNotEmpty(data)) {
            VCard vcard = Ezvcard.parse(data).first();

            if (CollectionUtils.isNotEmpty(vcard.getUrls())) {
                String name = vcard.getFormattedName().getValue();
                String website = vcard.getUrls().get(0).getValue();

                if (name != null && website != null) {
                    Publisher publisher = publisherService.getPublisherByName(name);
                    if (publisher == null) {
                        publishers.add(publisherService.createPublisher(name, website));
                    } else if (!publishers.contains(publisher)) {
                        publishers.add(publisher);
                    }
                }
            }
        }
    }

    protected List<LanguageString> getLanguageStrings(Node node, LanguageService languageService) {
        List<LanguageString> languageStrings = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            String text = value(currentNode);

            if (StringUtils.isNotEmpty(text)) {
                languageStrings.add(new LanguageString(getLanguageCode(languageService, currentNode), text));
            }
        }

        return languageStrings;
    }

    private Language getLanguageCode(LanguageService languageService, Node currentNode) {
        if (!currentNode.hasAttributes()) {
            return null;
        }
        Node item = currentNode.getAttributes().item(0);
        String languageCode = value(item);
        String[] tokens = languageCode.split("-");

        Language language = languageService.getLanguage(tokens[0]);
        if (language != null) {
            return language;
        }
        log.warn(String.format(NO_SUCH_LANGUAGE_FOR_S_LANGUAGE_STRING_WILL_HAVE_NO_LANGUAGE, languageCode));
        return null;
    }

    protected List<Tag> getTagsFromKeywords(NodeList nl, TagService tagService) {
        return nodeStreamOf(nl)
                .map(MaterialParserUtil::value)
                .distinct()
                .map(s -> mapTag(tagService, s))
                .collect(Collectors.toList());
    }

    private Tag mapTag(TagService tagService, String s) {
        Tag tag = tagService.getTagByName(s);
        return tag != null ? tag : new Tag(s);
    }

    protected List<ResourceType> getResourceTypes(Document doc, String path) {
        List<String> results = getNodeStream(doc, path)
                .map(this::getElementValue)
                .distinct()
                .collect(Collectors.toList());
        return resourceTypeService.getResourceTypeByName(results);
    }

    private List<PeerReview> getPeerReviews(Document doc, String path) {
        List<String> results = getNodeStream(doc, path)
                .map(this::getElementValue)
                .distinct()
                .collect(Collectors.toList());
        return peerReviewService.getPeerReviewByURL(results);
    }

    private void setTaxon(Material material, Document doc) {
        Set<Taxon> taxons = new HashSet<>();

        try {
            for (Node taxonPath : getTaxonPathNodes(doc)) {
                Taxon parent;
                parent = setEducationalContext(taxonPath);
                parent = setDomain(taxonPath, parent);

                parent = setSubject(taxonPath, parent);
                parent = setSpecialization(taxonPath, parent);
                parent = setModule(taxonPath, parent);

                parent = setTopic(taxonPath, parent);
                parent = setSubTopic(taxonPath, parent);

                if (parent != null) {
                    taxons.add(parent);
                }
            }
        } catch (Exception ignored) {
        }

        //Set contexts that are specified separately, not inside the taxon
        taxons.addAll(getEducationalContexts(doc, getPathToContext()));
        setSpecialEducation(material, doc, getPathToContext());

        material.setTaxons(new ArrayList<>(taxons));
    }

    private List<EducationalContext> getEducationalContexts(Document doc, String path) {
        return getNodeStream(doc, path)
                .map(this::getElementValue)
                .map(context -> (EducationalContext) getTaxon(context, EducationalContext.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void setSpecialEducation(Material material, Document doc, String path) {
        if (getNodeStream(doc, path)
                .map(this::getElementValue)
                .anyMatch(MaterialParserUtil::isSpecialEducation)) {
            material.setSpecialEducation(true);
        }
    }

    private void setLearningResourceType(Material material, Document doc) {
        try {
            material.setResourceTypes(getResourceTypes(doc, getPathToResourceType()));
        } catch (Exception ignored) {
        }
    }

    private void setPeerReview(Material material, Document doc) {
        try {
            material.setPeerReviews(getPeerReviews(doc, getPathToPeerReview()));
        } catch (Exception ignored) {
        }
    }

    private void setSource(Material material, Document doc) throws ParseException {
        try {
            material.setSource(getSource(doc));
        } catch (Exception e) {
            throw new ParseException(ERROR_PARSING_DOCUMENT_SOURCE, e);
        }
    }

    private String getSource(Document doc) throws ParseException, URISyntaxException {
        NodeList nodeList = getNodeList(doc, getPathToLocation());
        if (nodeList.getLength() != 1) {
            log.error(MATERIAL_HAS_MORE_OR_LESS_THAN_ONE_SOURCE_CAN_T_BE_MAPPED);
            throw new ParseException(MATERIAL_HAS_MORE_OR_LESS_THAN_ONE_SOURCE_CAN_T_BE_MAPPED);
        }
        String source = getInitialSource(nodeList);

        if (!URL_VALIDATOR.isValid(source)) {
            log.error(String.format(ERROR_PARSING_DOCUMENT_INVALID_URL_S, source));
            throw new ParseException(String.format(ERROR_PARSING_DOCUMENT_INVALID_URL_S, source));
        }

        return source;
    }

    private String getInitialSource(NodeList nodeList) throws URISyntaxException {
        String source = value(nodeList.item(0));
        URI uri = new URI(source);
        return uri.getScheme() != null ? source : "http://" + source;
    }

    protected void setTargetGroups(Material material, Document doc) {
        Set<TargetGroup> targetGroups = new HashSet<>();
        NodeList ageRanges = getNodeList(doc, getPathToTargetGroups());

        for (int i = 0; i < ageRanges.getLength(); i++) {
            Node item = ageRanges.item(i);
            String[] ranges = value(item).split("-");

            if (ranges.length == 2) {
                int from = Integer.parseInt(ranges[0].trim());
                int to = Integer.parseInt(ranges[1].trim());
                targetGroups.addAll(targetGroupService.getTargetGroupsByAge(from, to));
            }
        }
        material.setTargetGroups(new ArrayList<>(targetGroups));
    }

    protected void setAuthors(Document doc, Material material) {
        List<Author> authors = new ArrayList<>();
        NodeList nodeList = getNodeList(doc, getPathToContribute());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node contributorNode = nodeList.item(i);
            String role = getRoleString(contributorNode);

            if (AUTHOR.equals(role)) {
                String vCard = getVCard(contributorNode);
                setAuthorFromVCard(authors, vCard);
            }
        }

        material.setAuthors(authors);
    }

    private void setPublishersData(Document doc, Material material) {
        List<Publisher> publishers = new ArrayList<>();
        IssueDate issueDate = null;
        NodeList nodeList = getNodeList(doc, getPathToContribute());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node contributorNode = nodeList.item(i);
            String role = getRoleString(contributorNode);

            if (PUBLISHER.equals(role)) {
                String vCard = getVCard(contributorNode);
                setPublisherFromVCard(publishers, vCard);

                Node issueDateNode = getNode(contributorNode, "./*[local-name()='date']/*[local-name()='dateTime']");
                if (issueDateNode != null) {
                    issueDate = new IssueDate(LocalDateTime.parse(value(issueDateNode)));
                }
            }
        }

        material.setPublishers(publishers);
        material.setIssueDate(issueDate);
    }

    private String getRoleString(Node contributorNode) {
        try {
            Node roleNode = getNode(contributorNode, "./*[local-name()='role']/*[local-name()='value']");
            return valueToUpper(roleNode);
        } catch (Exception ignored) {
            return null;
        }
    }

    private String getVCard(Node contributorNode) {
        Node node = getNode(contributorNode, "./*[local-name()='entity']");
        if (node == null) {
            return "";
        }
        return nodeStreamOf(node.getChildNodes())
                .filter(item -> StringUtils.isNotEmpty(value(item)))
                .map(item -> getVCardWithNewLines((CharacterData) item))
                .findFirst()
                .orElse("");
    }

    protected Stream<Node> getNodeStream(Document doc, String path) {
        return nodeStreamOf(getNodeList(doc, path));
    }

    protected NodeList getNodeList(Node node, String path) {
        try {
            XPathExpression expr = xpath.compile(path);
            return (NodeList) expr.evaluate(node, XPathConstants.NODESET);
        } catch (XPathExpressionException ignored) {
            return null;
        }
    }

    protected Node getNode(Node node, String path) {
        try {
            XPathExpression expr = xpath.compile(path);
            return (Node) expr.evaluate(node, XPathConstants.NODE);
        } catch (XPathExpressionException ignored) {
            return null;
        }
    }

    private List<Node> getTaxonPathNodes(Document doc) {
        List<Node> nodes = new ArrayList<>();
        NodeList classifications = getNodeList(doc, getPathToClassification());

        for (int i = 0; i < classifications.getLength(); i++) {
            NodeList nl = getNodeList(classifications.item(i), TAXON_PATH);

            if (notEmpty(nl)) {
                nodes.addAll(nodeStreamOf(nl).collect(Collectors.toList()));
            }
        }
        return nodes;
    }

    protected Taxon setEducationalContext(Node taxonPath) {
        return taxonMap.entrySet().stream()
                .filter(tag -> getNode(taxonPath, "./*[local-name()='" + tag.getKey() + "']") != null)
                .map(tag -> getTaxon(tag.getValue(), EducationalContext.class))
                .findFirst()
                .orElse(null);
    }

    protected Taxon setDomain(Node taxonPath, Taxon parent) {
        return setTaxon(taxonPath, parent, Domain.class, "domain", (t, key) -> t instanceof EducationalContext);
    }

    protected Taxon setSubject(Node taxonPath, Taxon parent) {
        return setTaxon(taxonPath, parent, Subject.class, "subject", (t, key) -> t instanceof Domain);
    }

    private Taxon setSpecialization(Node taxonPath, Taxon parent) {
        return setTaxon(taxonPath, parent, Specialization.class, "specialization", (t, key) -> t instanceof Domain);
    }

    private Taxon setModule(Node taxonPath, Taxon parent) {
        return setTaxon(taxonPath, parent, Module.class, "module", (t, key) -> t instanceof Specialization);
    }

    private Taxon setTopic(Node taxonPath, Taxon parent) {
        return setTaxon(taxonPath, parent, Topic.class, "topic", this::topicParentValidation);
    }

    private Taxon setSubTopic(Node taxonPath, Taxon parent) {
        return setTaxon(taxonPath, parent, Subtopic.class, "subtopic", (t, key) -> t instanceof Topic);
    }

    private Taxon setTaxon(Node taxonPath, Taxon parent, Class<? extends Taxon> level, String levelString, BiFunction<Taxon, String, Boolean> parentValidator) {
        return taxonMap.keySet().stream()
                .filter(tag -> parentValidator.apply(parent, tag))
                .map(tag -> getTaxonNode(taxonPath, tag, levelString))
                .filter(Objects::nonNull)
                .map(node -> getTaxon(node.getTextContent(), parent.getChildrenList(), level))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(parent);
    }

    private boolean topicParentValidation(Taxon parent, String tag) {
        return parent instanceof Domain && tag.equals(MaterialParserUtil.PRESCHOOL_TAXON) ||
                parent instanceof Module && tag.equals(MaterialParserUtil.VOCATIONAL_TAXON) ||
                parent instanceof Subject;
    }

    private Taxon getTaxon(String name, List<Taxon> list, Class<? extends Taxon> level) {
        List<String> systemNames = getTaxonNames(name, level);
        return list.stream()
                .filter(taxon -> systemNames.contains(taxon.getName()))
                .findAny()
                .orElse(null);
    }

    protected abstract void setTags(Material material, Document doc);

    protected abstract void setDescriptions(Material material, Document doc);

    protected abstract void setLanguage(Material material, Document doc);

    protected abstract void setTitles(Material material, Document doc) throws ParseException;

    protected abstract void setIsPaid(Material material, Document doc);

    protected abstract void setPicture(Material material, Document doc);

    protected abstract void setCrossCurricularThemes(Material material, Document doc);

    protected abstract void setKeyCompetences(Material material, Document doc);

    protected abstract void setEmbedSource(Material material, Document doc);

    protected abstract String getVCardWithNewLines(CharacterData characterData);

    protected abstract String getElementValue(Node node);

    protected abstract String getPathToContext();

    protected abstract String getPathToResourceType();

    protected abstract String getPathToPeerReview();

    protected abstract String getPathToLocation();

    protected abstract String getPathToContribute();

    protected abstract String getPathToTargetGroups();

    protected abstract String getPathToCurriculumLiterature();

    protected abstract String getPathToClassification();

    private Taxon getTaxon(String context, Class<? extends Taxon> level) {
        return taxonService.getTaxonByEstCoreName(context, level);
    }

    private List<String> getTaxonNames(String context, Class<? extends Taxon> level) {
        return taxonService.getTaxonsByEstCoreName(context, level).stream()
                .map(Taxon::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    private Node getTaxonNode(Node taxonPath, String tag, String domain) {
        return getNode(taxonPath, taxonPath(tag, domain));
    }

    private String taxonPath(String tag, String domain) {
        return "./*[local-name()='" + tag + "']/*[local-name()='" + domain + "']";
    }

    private void logFail(RuntimeException e) {
        log.error("Unexpected error while parsing document. Document may not"
                + " match mapping or XML structure - " + e.getMessage(), e);
    }
}
