-- IssueDate

insert into IssueDate(id, day, month, year) values(1, 2, 2, 1983);
insert into IssueDate(id, day, month, year) values(2, 27, 1, -983);
insert into IssueDate(id, year) values(3, -1500);
insert into IssueDate(id, day, month, year) values(4, 31, 3, 1923);
insert into IssueDate(id, day, month, year) values(5, 9, 12, 1978);
insert into IssueDate(id, day, month, year) values(6, 27, 1, 1986);
insert into IssueDate(id, month, year) values(7, 3, 1991);

-- LanguageTable

insert into LanguageTable(id, name, code) values (1, 'Estonian', 'est');
insert into LanguageTable(id, name, code) values (2, 'Russian', 'rus');
insert into LanguageTable(id, name, code) values (3, 'English', 'eng');
insert into LanguageTable(id, name, code) values (4, 'Arabic', 'ara');
insert into LanguageTable(id, name, code) values (5, 'Portuguese', 'por');
insert into LanguageTable(id, name, code) values (6, 'French', 'fre');

-- License Type

insert into LicenseType(id, name) values (1, 'CCBY');
insert into LicenseType(id, name) values (2, 'CCBYSA');
insert into LicenseType(id, name) values (3, 'CCBYND');

-- Repository. Do not use real URLs here

insert into Repository(id, baseURL, lastSynchronization, schemaName, contentIsEmbeddable, metadataPrefix, used) values (1, 'http://repo1.ee', null, 'waramu', false, 'oai_estcore', true);
insert into Repository(id, baseURL, lastSynchronization, schemaName, contentIsEmbeddable, metadataPrefix, used) values (2, 'http://estonianPublisher.ee/OAIHandler', null, 'estCore', true, 'oai_estcore', true);

-- Publishers

insert into Publisher(id, name, website) values (1, 'Koolibri', 'http://www.koolibri.ee');
insert into Publisher(id, name, website) values (2, 'Pegasus', 'http://www.pegasus.ee');
insert into Publisher(id, name, website) values (3, 'Varrak', 'http://www.varrak.ee');

-- User

insert into User(id, userName, name, surName, idCode, role, publisher) values (1, 'mati.maasikas', 'Mati', 'Maasikas', '39011220011', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (2, 'peeter.paan', 'Peeter', 'Paan', '38011550077', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (3, 'voldemar.vapustav', 'Voldemar', 'Vapustav', '37066990099', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (4, 'voldemar.vapustav2', 'Voldemar', 'Vapustav', '15066990099', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (5, 'mati.maasikas2', 'Mäti', 'Maasikas', '39011220012', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (6, 'mati.maasikas-vaarikas', 'Mati', 'Maasikas-Vaarikas', '39011220013', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (7, 'my.testuser', 'My', 'Testuser', '78912378912', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (8, 'admin.admin', 'Admin', 'Admin', '89898989898', 'ADMIN', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (9, 'second.testuser', 'Second', 'Testuser', '89012378912', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (10, 'super.publisher', 'Super', 'Publisher', '77007700770', 'USER', 1);
insert into User(id, userName, name, surName, idCode, role, publisher) values (11, 'restricted.user', 'Restricted', 'User', '89898989890', 'RESTRICTED', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (12, 'biffy.clyro', 'Biffy', 'Clyro', '38211120031', 'MODERATOR', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (13, 'user.to.be.banned1', 'Whiskey', 'Tango', '38256133107', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (14, 'user.to.be.banned2', 'November', 'Juliet', '38256133108', 'USER', null);
insert into User(id, userName, name, surName, idCode, role, publisher) values (15, 'restricted.user2', 'Restricted', 'User', '89898989892', 'RESTRICTED', null);

-- UserTourData

INSERT INTO UserTourData(id, user, generalTour, editTour) VALUES (1, 1, 1, 0);

-- AuthenticatedUser

insert into AuthenticatedUser(id, user_id, token, firstLogin, person) values (1, 1, 'token', false, null);

-- AuthenticationState

insert into AuthenticationState(id, token) values (1, 'testTOKEN');
insert into AuthenticationState(id, token) values (2, 'taatAuthenticateTestToken');

-- EducationalContext

insert into Taxon(id, name, nameLowerCase, level, used) values (1, 'PRESCHOOLEDUCATION', 'preschooleducation', 'EDUCATIONAL_CONTEXT', 1);
insert into EducationalContext(id, used) values (1, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (2, 'BASICEDUCATION', 'basiceducation', 'EDUCATIONAL_CONTEXT', 1);
insert into EducationalContext(id, used) values (2, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (3, 'SECONDARYEDUCATION', 'secondaryeducation', 'EDUCATIONAL_CONTEXT', 1);
insert into EducationalContext(id, used) values (3, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (4, 'HIGHEREDUCATION', 'highereducation', 'EDUCATIONAL_CONTEXT', 1);
insert into EducationalContext(id, used) values (4, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (5, 'VOCATIONALEDUCATION', 'vocationaleducation', 'EDUCATIONAL_CONTEXT', 1);
insert into EducationalContext(id, used) values (5, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (6, 'CONTINUINGEDUCATION', 'continuingeducation', 'EDUCATIONAL_CONTEXT', 1);
insert into EducationalContext(id, used) values (6, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (7, 'TEACHEREDUCATION', 'teachereducation', 'EDUCATIONAL_CONTEXT', 1);
insert into EducationalContext(id, used) values (7, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (8, 'SPECIALEDUCATION', 'specialeducation', 'EDUCATIONAL_CONTEXT', 1);
insert into EducationalContext(id, used) values (8, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (9, 'OTHER', 'other', 'EDUCATIONAL_CONTEXT', 1);
insert into EducationalContext(id, used) values (9, 1);

-- Domain

insert into Taxon(id, name, nameLowerCase, level, used) values (10, 'Mathematics', 'mathematics', 'DOMAIN', 1);
insert into Domain(id, educationalContext, used) values (10, 1, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (11, 'ForeignLanguage', 'foreignlanguage', 'DOMAIN', 1);
insert into Domain(id, educationalContext, used) values (11, 1, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (12, 'DomainWithTopics', 'domainwithtopics', 'DOMAIN', 1);
insert into Domain(id, educationalContext, used) values (12, 6, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (13, 'SecondaryDomain', 'secondarydomain', 'DOMAIN', 1);
insert into Domain(id, educationalContext, used) values (13, 3, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (14, 'Computer_science', 'computer_science', 'DOMAIN', 1);
insert into Domain(id, educationalContext, used) values (14, 5, 1);

-- Subject

insert into Taxon(id, name, nameLowerCase, level, used) values (20, 'Biology', 'biology', 'SUBJECT', 1);
insert into Subject(id, domain, used) values (20, 10, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (21, 'Mathematics', 'mathematics', 'SUBJECT', 1);
insert into Subject(id, domain, used) values (21, 10, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (22, 'SecondarySubject', 'secondarysubject', 'SUBJECT', 1);
insert into Subject(id, domain, used) values (22, 13, 1);

-- Specialization

insert into Taxon(id, name, nameLowerCase, level, used) values (40, 'Computers_and_Networks', 'computers_and_networks', 'SPECIALIZATION', 1);
insert into Specialization(id, domain, used) values (40, 14, 1);

-- Module

insert into Taxon(id, name, nameLowerCase, level, used) values (50, 'IT_õigus', 'it_õigus', 'MODULE', 1);
insert into Module(id, specialization, used) values (50, 40, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (51, 'Kommunikatsioon', 'kommunikatsioon', 'MODULE', 1);
insert into Module(id, specialization, used) values (51, 40, 1);

-- Topics from Subjects

insert into Taxon(id, name, nameLowerCase, level, used) values (30, 'Algebra', 'algebra', 'TOPIC', 1);
insert into Topic(id, subject, used) values (30, 21, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (31, 'Trigonometria', 'trigonometria', 'TOPIC', 1);
insert into Topic(id, subject, used) values (31, 21, 1);

-- Topics from Domain

insert into Taxon(id, name, nameLowerCase, level, used) values (32, 'EstoniaAndTheWould', 'estoniaandthewould', 'TOPIC', 1);
insert into Topic(id, domain, used) values (32, 12, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (33, 'VogaisTonicas', 'vogaistonicas', 'TOPIC', 1);
insert into Topic(id, domain, used) values (33, 12, 1);

-- Topics from Module

insert into Taxon(id, name, nameLowerCase, level, used) values (34, 'Infoühiskonna_tehnoloogiad', 'infoühiskonna_tehnoloogiad', 'TOPIC', 1);
insert into Topic(id, module, used) values (34, 50, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (35, 'Arvuti_töövahendina', 'arvuti_töövahendina', 'TOPIC', 1);
insert into Topic(id, module, used) values (35, 51, 1);

-- Subtopic

insert into Taxon(id, name, nameLowerCase, level, used) values (60, 'arvsõna', 'arvsõna', 'SUBTOPIC', 1);
insert into Subtopic(id, topic, used) values (60, 30, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (61, 'konkurents', 'konkurents', 'SUBTOPIC', 1);
insert into Subtopic(id, topic, used) values (61, 32, 1);
insert into Taxon(id, name, nameLowerCase, level, used) values (62, 'tehnoloogia_ja_ühiskond', 'tehnoloogia_ja_ühiskond', 'SUBTOPIC', 1);
insert into Subtopic(id, topic, used) values (62, 34, 1);

-- UserTaxon

INSERT INTO User_Taxon(user, taxon) VALUES (12, 1);
INSERT INTO User_Taxon(user, taxon) VALUES (12, 10);
INSERT INTO User_Taxon(user, taxon) VALUES (12, 21);
INSERT INTO User_Taxon(user, taxon) VALUES (12, 31);

-- EstCore taxon mapping

insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (1, 1, 'preschoolEducation', 'preschooleducation');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (2, 2, 'basicEducation', 'basiceducation');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (3, 3, 'secondaryEducation', 'secondaryeducation');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (4, 4, 'higherEducation', 'highereducation');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (5, 5, 'vocationalEducation', 'vocationaleducation');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (6, 6, 'continuingEducation', 'continuingeducation');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (7, 7, 'teacherEducation', 'teachereducation');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (8, 8, 'specialEducation', 'specialeducation');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (9, 9, 'other', 'other');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (10, 10, 'Mathematics', 'mathematics');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (11, 11, 'Foreign language', 'foreign language');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (12, 12, 'DomainWithTopics', 'domainwithtopics');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (20, 20, 'Biology', 'biology');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (21, 21, 'Mathematics', 'mathematics');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (30, 30, 'Algebra', 'algebra');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (31, 31, 'Trigonometria', 'trigonometria');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (32, 32, 'EstoniaAndTheWould', 'estoniaandthewould');
insert into EstCoreTaxonMapping(id, taxon, name, nameLowercase) values (33, 33, 'VogaisTonicas', 'vogaistonicas');

-- Recommendations

insert into Recommendation(id, creator, added) values (1, 8, '2015-12-12 13:14:15');
insert into Recommendation(id, creator, added) values (2, 8, '2015-12-12 13:14:16');
insert into Recommendation(id, creator, added) values (3, 8, '2015-12-12 13:14:17');
insert into Recommendation(id, creator, added) values (4, 8, '2015-12-12 13:14:17'); -- deleted portfolio
insert into Recommendation(id, creator, added) values (5, 8, '2015-12-12 13:14:17'); -- private portfolio
insert into Recommendation(id, creator, added) values (6, 8, '2015-12-12 13:14:17'); -- not listed portfolio
insert into Recommendation(id, creator, added) values (7, 8, '2015-12-12 13:14:17'); -- deleted material


-- Picture

insert into Picture(id, name, data) values(1, 'picture1', '656b6f6f6c696b6f7474');
insert into Picture(id, name, data) values(2, 'picture2', '556b6f6f6c696b6f7474');
insert into Picture(id, name, data) values(3, 'picture3', '77b6f6f6c696b65f7474');
insert into Picture(id, name, data) values(4, 'picture4', '65425284561965bf7474');
insert into Picture(id, name, data) values(5, 'picture5', '656b6f6f6c6967896215');

-- Thumbnail

insert into Thumbnail(id, name, data, size) values(1, 'thumbnail1', '65425284561965bf7474', 'SM');

-- Materials

insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(1, '1999-01-01 00:00:01', '2000-03-01 07:00:01', 100, 1, 1, false, 1, 'PUBLIC', 1);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(1, 1, 1, 'https://www.youtube.com/watch?v=gSWbx3CvVUk', 1, 'isssiiaawej', true, true, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(2, '1970-01-01 00:00:01', '1995-07-12 09:00:01', 200, null, 2, false, 2, 'PUBLIC', 2);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(2, 2, 2, 'https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes', 1, 'isssiidosa00dsa', true, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(3, '2009-01-01 00:00:01', '2011-01-10 19:00:01', 300, null, null, false, null, 'PUBLIC', 3);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(3, 4, 3,  'http://eloquentjavascript.net/Eloquent_JavaScript.pdf', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(4, '2012-01-01 00:00:01', '2012-08-28 22:40:01', 400, null, 1, false, null, 'PUBLIC', 1);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(4, 3, 4,  'https://en.wikipedia.org/wiki/Power_Architecture', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(5, '2011-09-01 00:00:01', '2012-11-04 09:30:01', 500, null, 2, false, null, 'PUBLIC', 2);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(5, 3, 5,  'https://en.wikipedia.org/wiki/Power_Architecture', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(6, '1911-09-01 00:00:01', null, 600, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(6, null, null, 'http://www.planalto.gov.br/ccivil_03/Constituicao/Constituicao.htm', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(7, '2001-07-01 00:00:01', null, 700, null, null, false, null, 'PUBLIC', 3);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(7, 4, 6, 'https://president.ee/en/republic-of-estonia/the-constitution/index.html', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(8, '2014-06-01 00:00:01', null, 800, null, 1, false, null, 'PUBLIC', 1);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(8, 5, 7, 'http://www.palmeiras.com.br/historia/titulos', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(9, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(9, null, null, 'http://www.chaging.it.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(10, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(10, null, null, 'http://www.boo.com', null, null, false, false, false); -- Do not use this material, it is deleted by tests
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(11, '2015-09-02 00:00:01', '2015-09-03 07:00:01', 100, 2, 1, true, 7, 'PUBLIC', 1);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(11, 1, null, 'https://www.deleted.com/', 1, 'isssiiaawejdsada4564', false, false, false); -- This material should be amoung the 8 latest materials
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(12, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(12, null, null, 'http://estRepo.com', 2, null, false, false, true);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(13, '2005-09-02 00:00:31', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(13, null, null, 'http://example.com/123', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(14, '2005-09-02 04:00:31', null, 0, null, null, true, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(14, null, null, 'http://example.com/456', null, 1, true, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(15, '2005-09-02 00:00:31', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(15, null, null, 'http://example.com/123', null, null, false, false, false);

-- ReviewableChange data
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(16, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(16, null, null, 'http://www.bieber16.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(17, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(17, null, null, 'http://www.bieber17.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(18, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(18, null, null, 'http://www.bieber18.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(19, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(19, null, null, 'http://www.bieber19.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(20, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(20, null, null, 'http://www.bieber20.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(21, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(21, null, null, 'http://www.bieber21.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(22, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(22, null, null, 'http://www.bieber22.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(23, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(23, null, null, 'http://www.bieber23.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(24, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(24, null, null, 'http://www.bieber24.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(25, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(25, null, null, 'http://www.bieber25.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(26, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(26, null, null, 'http://www.bieber26.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(27, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(27, null, null, 'http://www.bieber27.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(28, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(28, null, null, 'http://www.bieber28.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(29, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(29, null, null, 'http://www.bieber29.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(30, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(30, null, null, 'http://www.bieber30.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(31, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(31, null, null, 'http://www.bieber31.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(32, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(32, null, null, 'http://www.bieber32.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(33, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(33, null, null, 'http://www.bieber33.com', null, null, false, false, false);
-- Review test
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(34, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(34, null, null, 'http://www.bieber34.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(35, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(35, null, null, 'http://www.bieber35.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(36, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(36, null, null, 'http://www.bieber36.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(37, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(37, null, null, 'http://www.bieber37.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(38, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(38, null, null, 'http://www.bieber38.com', null, null, false, false, false);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType) values(39, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(39, null, null, 'http://www.bieber39.com', null, null, false, false, false);

insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility, licenseType, promoted) values(40, '1911-09-01 00:00:01', null, 0, null, null, false, null, 'PUBLIC', null, 1);
insert into Material(id, lang, issueDate, source, repository, repositoryIdentifier, paid, isSpecialEducation, embeddable) values(40, null, null, 'http://www.bieber39.com', null, null, false, false, false);


-- Authors

insert into Author(id, name, surname) values(1, 'Isaac', 'John Newton');
insert into Author(id, name, surname) values(2, 'Karl Simon Ben', 'Tom Oliver Marx');
insert into Author(id, name, surname) values(3, 'Leonardo', 'Fibonacci');

-- Material_Authors

insert into Material_Author(material, author) values(1, 1);
insert into Material_Author(material, author) values(2, 1);
insert into Material_Author(material, author) values(2, 3);
insert into Material_Author(material, author) values(3, 1);
insert into Material_Author(material, author) values(4, 1);
insert into Material_Author(material, author) values(5, 3);
insert into Material_Author(material, author) values(6, 3);
insert into Material_Author(material, author) values(7, 3);
insert into Material_Author(material, author) values(8, 2);

-- LanguageKeyCodes

insert into LanguageKeyCodes(lang, code) values (1, 'et');
insert into LanguageKeyCodes(lang, code) values (2, 'ru');
insert into LanguageKeyCodes(lang, code) values (3, 'en');
insert into LanguageKeyCodes(lang, code) values (5, 'pt');
insert into LanguageKeyCodes(lang, code) values (5, 'pt-br');
insert into LanguageKeyCodes(lang, code) values (6, 'fr');

-- Material Descriptions

insert into LanguageString(id, lang, textValue) values (1, 1, 'Test description in estonian. (Russian available)');
insert into LanguageString(id, lang, textValue) values (2, 2, 'Test description in russian, which is the only language available.');
insert into LanguageString(id, lang, textValue) values (3, 2, 'Test description in russian. (Estonian available)');
insert into LanguageString(id, lang, textValue) values (4, 5, 'Test description in portugese, as the material language (english) not available.');
insert into LanguageString(id, lang, textValue) values (5, 4, 'Test description in arabic (material language). No estonian or russian available.');
insert into LanguageString(id, lang, textValue) values (6, 3, 'Test description in english, which is the material language.');
insert into LanguageString(id, lang, textValue) values (7, 3, 'Test description in english, which is not the material language. Others are also available, but arent estonian or russian.');
insert into LanguageString(id, lang, textValue) values (8, 5, 'Test description in portugese, english also available.');

insert into Material_Description(description, material) values(1, 1);
insert into Material_Description(description, material) values(2, 2);
insert into Material_Description(description, material) values(3, 1);
insert into Material_Description(description, material) values(4, 4);
insert into Material_Description(description, material) values(5, 3);
insert into Material_Description(description, material) values(6, 5);
insert into Material_Description(description, material) values(7, 7);
insert into Material_Description(description, material) values(8, 7);

-- Material Titles

insert into LanguageString(id, lang, textValue) values (9, 1, 'Matemaatika õpik üheksandale klassile');
insert into LanguageString(id, lang, textValue) values (10, 2, 'Математика учебник для 9-го класса');
insert into LanguageString(id, lang, textValue) values (11, 2, 'Математика учебник для 8-го класса');
insert into LanguageString(id, lang, textValue) values (12, 5, 'Test title in portugese: manual de instruções, as the material language (english) not available.');
insert into LanguageString(id, lang, textValue) values (13, 4, 'الرياضيات الكتب المدرسية للصف 7');
insert into LanguageString(id, lang, textValue) values (14, 3, 'The Capital.');
insert into LanguageString(id, lang, textValue) values (15, 3, 'Test title in english, which is not the material language. Others are also available, but arent estonian or russian.');
insert into LanguageString(id, lang, textValue) values (16, 5, 'Test title in portugese, english also available.');
insert into LanguageString(id, lang, textValue) values (17, 1, 'Eesti keele õpik üheksandale klassile');
insert into LanguageString(id, lang, textValue) values (18, 1, 'Aabits 123');


insert into Material_Title(title, material) values(9, 1);
insert into Material_Title(title, material) values(10, 1);
insert into Material_Title(title, material) values(11, 2);
insert into Material_Title(title, material) values(12, 4);
insert into Material_Title(title, material) values(13, 3);
insert into Material_Title(title, material) values(14, 5);
insert into Material_Title(title, material) values(15, 7);
insert into Material_Title(title, material) values(16, 7);
insert into Material_Title(title, material) values(17, 6);
insert into Material_Title(title, material) values(18, 8);


-- Material_Taxon

insert into LearningObject_Taxon(learningObject, taxon) values(1,20); -- PRESCHOOLEDUCATION/Mathematics/Biology
insert into LearningObject_Taxon(learningObject, taxon) values(2,21); -- PRESCHOOLEDUCATION/Mathematics/Mathematics
insert into LearningObject_Taxon(learningObject, taxon) values(3,20); -- PRESCHOOLEDUCATION/Mathematics/Biology
insert into LearningObject_Taxon(learningObject, taxon) values(4,20); -- PRESCHOOLEDUCATION/Mathematics/Biology
insert into LearningObject_Taxon(learningObject, taxon) values(5,21); -- PRESCHOOLEDUCATION/Mathematics/Mathematics
insert into LearningObject_Taxon(learningObject, taxon) values(6,20); -- PRESCHOOLEDUCATION/Mathematics/Biology
insert into LearningObject_Taxon(learningObject, taxon) values(6,21); -- PRESCHOOLEDUCATION/Mathematics/Mathematics

insert into LearningObject_Taxon(learningObject, taxon) values(7,11); -- PRESCHOOLEDUCATION/ForeignLanguage

insert into LearningObject_Taxon(learningObject, taxon) values(1,2); -- BASICEDUCATION
insert into LearningObject_Taxon(learningObject, taxon) values(2,4); -- HIGHEREDUCATION
insert into LearningObject_Taxon(learningObject, taxon) values(3,5); -- VOCATIONALEDUCATION
insert into LearningObject_Taxon(learningObject, taxon) values(4,6); -- CONTINUINGEDUCATION
insert into LearningObject_Taxon(learningObject, taxon) values(5,4); -- HIGHEREDUCATION
insert into LearningObject_Taxon(learningObject, taxon) values(7,5); -- VOCATIONALEDUCATION

-- ResourceType

insert into ResourceType(id, name) values (1001, 'TEXTBOOK1');
insert into ResourceType(id, name) values (1002, 'EXPERIMENT1');
insert into ResourceType(id, name) values (1003, 'SIMULATION1');
insert into ResourceType(id, name) values (1004, 'GLOSSARY1');
insert into ResourceType(id, name) values (1005, 'ROLEPLAY1');
insert into ResourceType(id, name) values (1006, 'WEBSITE');
insert into ResourceType(id, name) values (1007, 'COURSE');

-- Material_ResourceType

insert into Material_ResourceType(material, resourceType) values (1, 1001);
insert into Material_ResourceType(material, resourceType) values (1, 1002);
insert into Material_ResourceType(material, resourceType) values (2, 1003);
insert into Material_ResourceType(material, resourceType) values (3, 1004);
insert into Material_ResourceType(material, resourceType) values (4, 1005);
insert into Material_ResourceType(material, resourceType) values (5, 1003);
insert into Material_ResourceType(material, resourceType) values (6, 1002);
insert into Material_ResourceType(material, resourceType) values (7, 1004);

-- Cross-curricular themes
insert into CrossCurricularTheme(id, name) values (1, 'Lifelong_learning_and_career_planning');
insert into CrossCurricularTheme(id, name) values (2, 'Environment_and_sustainable_development');

-- Key competences
insert into KeyCompetence(id, name) values (1, 'Cultural_and_value_competence');
insert into KeyCompetence(id, name) values (2, 'Social_and_citizenship_competence');

-- LearningObject Cross-curricular themes
insert into LearningObject_CrossCurricularTheme(learningObject, crossCurricularTheme) values (1, 1);

-- LearningObject Key competences
insert into LearningObject_KeyCompetence(learningObject, keyCompetence) values (1, 1);

-- MaterialPublisher

insert into Material_Publisher(material, publisher) values (1, 1);
insert into Material_Publisher(material, publisher) values (1, 2);
insert into Material_Publisher(material, publisher) values (2, 2);
insert into Material_Publisher(material, publisher) values (3, 3);

-- Tags

insert into Tag(id, name) values (1, 'matemaatika');
insert into Tag(id, name) values (2, 'põhikool');
insert into Tag(id, name) values (3, 'õpik');
insert into Tag(id, name) values (4, 'mathematics');
insert into Tag(id, name) values (5, 'book');
insert into Tag(id, name) values (6, 'Математика');
insert into Tag(id, name) values (7, 'учебник');

insert into LearningObject_Tag(tag, learningObject) values(1, 1);
insert into LearningObject_Tag(tag, learningObject) values(1, 2);
insert into LearningObject_Tag(tag, learningObject) values(2, 1);
insert into LearningObject_Tag(tag, learningObject) values(3, 1);
insert into LearningObject_Tag(tag, learningObject) values(4, 1);
insert into LearningObject_Tag(tag, learningObject) values(4, 2);
insert into LearningObject_Tag(tag, learningObject) values(5, 1);
insert into LearningObject_Tag(tag, learningObject) values(6, 2);
insert into LearningObject_Tag(tag, learningObject) values(7, 2);

-- TargetGroups
insert into TargetGroup(id, name, label) values (1, 'ZERO_FIVE', 'PRESCHOOL');
insert into TargetGroup(id, name, label) values (2, 'SIX_SEVEN', 'PRESCHOOL');

insert into TargetGroup(id, name, label) values (3, 'GRADE1', 'LEVEL1');
insert into TargetGroup(id, name, label) values (4, 'GRADE2', 'LEVEL1');
insert into TargetGroup(id, name, label) values (5, 'GRADE3', 'LEVEL1');

insert into TargetGroup(id, name, label) values (6, 'GRADE4', 'LEVEL2');
insert into TargetGroup(id, name, label) values (7, 'GRADE5', 'LEVEL2');
insert into TargetGroup(id, name, label) values (8, 'GRADE6', 'LEVEL2');

insert into TargetGroup(id, name, label) values (9, 'GRADE7', 'LEVEL3');
insert into TargetGroup(id, name, label) values (10, 'GRADE8', 'LEVEL3');
insert into TargetGroup(id, name, label) values (11, 'GRADE9', 'LEVEL3');

insert into TargetGroup(id, name, label) values (12, 'GYMNASIUM', 'LEVEL_GYMNASIUM');

-- LearningObject TargetGroups

insert into LearningObject_TargetGroup(learningObject, targetGroup) values (1, 1);
insert into LearningObject_TargetGroup(learningObject, targetGroup) values (1, 2);

-- TranslationGroup

insert into TranslationGroup(id, lang) values (1, 1);
insert into TranslationGroup(id, lang) values (2, 2);
insert into TranslationGroup(id, lang) values (3, 3);

-- Translation

-- Estonian
insert into Translation(translationGroup, translationKey, translation) values (1, 'FOO', 'FOO sõnum');
insert into Translation(translationGroup, translationKey, translation) values (1, 'Estonian', 'Eesti keeles');
insert into Translation(translationGroup, translationKey, translation) values (1, 'Russian', 'Vene keel');
insert into Translation(translationGroup, translationKey, translation) values (1, 'TOPIC_MATHEMATICS', 'Matemaatika');
insert into Translation(translationGroup, translationKey, translation) values (1, 'FEED_ID', 'e-Koolikott:et');
insert into Translation(translationGroup, translationKey, translation) values (1, 'FEED_TITLE', 'e-Koolikott - Uudised');
insert into Translation(translationGroup, translationKey, translation) values (1, 'FEED_VERSION_TITLE', 'Uus versioon "%s"');
insert into Translation(translationGroup, translationKey, translation) values (1, 'FEED_PORTFOLIO_TITLE', 'Uus portfoolio "%s"');
insert into Translation(translationGroup, translationKey, translation) values (1, 'FEED_MATERIAL_TITLE', 'Uus material "%s"');
insert into Translation(translationGroup, translationKey, translation) values (1, 'DOMAIN_FOREIGNLANGUAGE', 'ForeignLanguage');

-- Russian
insert into Translation(translationGroup, translationKey, translation) values (2, 'FOO', 'FOO сообщение');
insert into Translation(translationGroup, translationKey, translation) values (2, 'Estonian', 'Эстонский язык');
insert into Translation(translationGroup, translationKey, translation) values (2, 'Russian', 'русский язык');
insert into Translation(translationGroup, translationKey, translation) values (2, 'TOPIC_MATHEMATICS', 'Mатематика');
insert into Translation(translationGroup, translationKey, translation) values (2, 'FEED_ID', 'e-Koolikott:ru');
insert into Translation(translationGroup, translationKey, translation) values (2, 'FEED_TITLE', 'Новая версия "%s"');
insert into Translation(translationGroup, translationKey, translation) values (2, 'FEED_VERSION_TITLE', 'Feed version title rus');
insert into Translation(translationGroup, translationKey, translation) values (2, 'FEED_PORTFOLIO_TITLE', 'Новый портфель "%s"');
insert into Translation(translationGroup, translationKey, translation) values (2, 'FEED_MATERIAL_TITLE', 'Новый материал "%s"');
insert into Translation(translationGroup, translationKey, translation) values (2, 'DOMAIN_FOREIGNLANGUAGE', 'ForeignLanguage');

-- English
insert into Translation(translationGroup, translationKey, translation) values (3, 'FOO', 'FOO message');
insert into Translation(translationGroup, translationKey, translation) values (3, 'Estonian', 'Estonian');
insert into Translation(translationGroup, translationKey, translation) values (3, 'Russian', 'Russian');
insert into Translation(translationGroup, translationKey, translation) values (3, 'TOPIC_MATHEMATICS', 'Mathematics');
insert into Translation(translationGroup, translationKey, translation) values (3, 'FEED_ID', 'e-Koolikott:en');
insert into Translation(translationGroup, translationKey, translation) values (3, 'FEED_TITLE', 'e-Koolikott - News');
insert into Translation(translationGroup, translationKey, translation) values (3, 'FEED_VERSION_TITLE', 'New version "%s"');
insert into Translation(translationGroup, translationKey, translation) values (3, 'FEED_PORTFOLIO_TITLE', 'New portfolio "%s"');
insert into Translation(translationGroup, translationKey, translation) values (3, 'FEED_MATERIAL_TITLE', 'New material "%s"');
insert into Translation(translationGroup, translationKey, translation) values (3, 'DOMAIN_FOREIGNLANGUAGE', 'ForeignLanguage');

-- Page

-- Estonian
insert into Page(id, name, content, language) VALUES (1, 'About', '<h1>Meist</h1><p>Tekst siin</p>', 1);
insert into Page(id, name, content, language) VALUES (2, 'Help', '<h1>Abi</h1><p>ekst siine</p>', 1);

-- Russian
insert into Page(id, name, content, language) VALUES (3, 'About', '<h1>О нас</h1><p>Текст здесь.</p>', 2);
insert into Page(id, name, content, language) VALUES (4, 'Help', '<h1>Помощь</h1><p>Текст здесь.</p>', 2);

-- English
insert into Page(id, name, content, language) VALUES (5, 'About', '<h1>About us</h1><p>Text here</p>', 3);
insert into Page(id, name, content, language) VALUES (6, 'Help', '<h1>Help</h1><p>Text here</p>', 3);

-- Portfolio

insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (101, '2000-12-29 06:00:01', '2004-12-29 06:00:01', 95455215, 3, 6, false, 3, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (101, 'The new stock market', 5, 'The changes after 2008.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (102, '2012-12-29 06:00:01', null, 14, null, 4, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (102, 'New ways how to do it', 4, null);
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (103, '2002-12-29 06:00:01', '2006-12-29 06:00:01', 14, 4, 6, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (103, 'The newer stock market', 6, 'A marvellous summary.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (104, '2003-10-10 07:00:11', null, 100, null, 1, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (104, 'The even newer stock market', 1, 'Cool summary.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (105, '2003-10-10 07:00:11', null, 100, null, 1, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (105, 'Adding comment to a portfolio', 1, 'Cool summary.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (106, '2003-10-10 07:00:11', null, 100, null, 2, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (106, 'This portfolio will be changed to not listed in the tests. ', 2, 'Summary.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (107, '2003-10-10 07:00:11', null, 100, 5, 2, false, null, 'PRIVATE');
insert into Portfolio(id, title, originalCreator, summary) VALUES (107, 'This portfolio is private. ', 2, 'Summary. Sum sum sum.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (108, '2003-10-10 07:00:11', null, 100, null, 2, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (108, 'This portfolio is public. ', 2, 'Summary. Wow.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (109, '2011-10-10 07:00:11', null, 100, null, 7, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (109, 'This portfolio2 is public. ', 7, 'Alpha.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (110, '2003-10-10 07:00:11', null, 95455216, null, 7, false, 5, 'PRIVATE');
insert into Portfolio(id, title, originalCreator, summary) VALUES (110, 'This portfolio2 is private. ', 7, 'Alpha.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (111, '2003-10-10 07:00:11', null, 100, null, 7, false, 6, 'NOT_LISTED');
insert into Portfolio(id, title, originalCreator, summary) VALUES (111, 'This portfolio2 is not listed. ', 7, 'Alpha.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (112, '2003-10-10 07:00:11', null, 100, null, 9, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (112, 'This portfolio will be DELETED in tests.', 9, 'Alpha.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (113, '2003-12-10 06:21:10', null, 100, null, 9, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (113, 'This portfolio will be DELETED in tests by admin.', 9, 'Beta.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (114, '2003-10-10 07:00:09', null, 100, null, 6, false, 4, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (114, 'Uploading picture to it', 6, 'Beta.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (115, '2003-10-10 07:00:09', null, 0, null, null, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (115, 'Broken portfolio', 6, 'Beta.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (116, '2003-10-10 07:00:11', null, 100, null, 2, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (116, 'This portfolio is public. ', 2, 'Summary. Wow.');
insert into LearningObject(id, added, updated, views, picture, creator, deleted, recommendation, visibility) VALUES (117, '2003-10-10 07:00:11', null, 100, null, 2, false, null, 'PUBLIC');
insert into Portfolio(id, title, originalCreator, summary) VALUES (117, 'This portfolio is public. ', 2, 'Summary. Wow.');

-- Chapter

insert into Chapter(id, title, portfolio, textValue, parentChapter, chapterOrder) values (1, 'The crisis', 101, null, null, 0);
insert into Chapter(id, title, portfolio, textValue, parentChapter, chapterOrder) values (2, 'Chapter 3', 101, 'This is some text that explains what is the Chapter 3 about.' || char(10) || 'It can have many lines' || char(10) || char(10) || char(10) || 'And can also have    spaces   betwenn    the words on it', null, 2);
insert into Chapter(id, title, portfolio, textValue, parentChapter, chapterOrder) values (3, 'Chapter 2', 101, 'Paragraph 1' || char(10) || char(10) || 'Paragraph 2' || char(10) || char(10) || 'Paragraph 3' || char(10) || char(10) || 'Paragraph 4', null, 1);
insert into Chapter(id, title, portfolio, textValue, parentChapter, chapterOrder) values (4, 'Subprime', null, null, 1, 0); -- Subchapter of #1
insert into Chapter(id, title, portfolio, textValue, parentChapter, chapterOrder) values (5, 'The big crash', null, 'Bla bla bla' || char(10) || 'Bla bla bla bla bla bla bla', 1, 1); -- Subchpater of #1

-- Content_Row

insert into ContentRow(id) values(1);
insert into ContentRow(id) values(2);
insert into ContentRow(id) values(3);
insert into ContentRow(id) values(4);
insert into ContentRow(id) values(5);

-- Chapter_Row

insert into Chapter_Row(row, chapter, rowOrder) values(1, 1, 0);
insert into Chapter_Row(row, chapter, rowOrder) values(2, 2, 0);
insert into Chapter_Row(row, chapter, rowOrder) values(3, 3, 0);
insert into Chapter_Row(row, chapter, rowOrder) values(4, 4, 0);
insert into Chapter_Row(row, chapter, rowOrder) values(5, 5, 0);


-- Row_Material

insert into Row_Material(row, material, materialOrder) values(1, 1, 0);
insert into Row_Material(row, material, materialOrder) values(2, 5, 0);
insert into Row_Material(row, material, materialOrder) values(3, 1, 0);
insert into Row_Material(row, material, materialOrder) values(4, 8, 0);
insert into Row_Material(row, material, materialOrder) values(5, 3, 0);

-- Portfolio-Tags

insert into LearningObject_Tag(tag, learningObject) values(1, 101);
insert into LearningObject_Tag(tag, learningObject) values(1, 103);
insert into LearningObject_Tag(tag, learningObject) values(2, 101);
insert into LearningObject_Tag(tag, learningObject) values(3, 101);
insert into LearningObject_Tag(tag, learningObject) values(4, 101);
insert into LearningObject_Tag(tag, learningObject) values(4, 103);
insert into LearningObject_Tag(tag, learningObject) values(5, 101);

-- Portfolio TargetGroups
insert into LearningObject_TargetGroup(learningObject, targetGroup) values (101, 1);
insert into LearningObject_TargetGroup(learningObject, targetGroup) values (101, 2);

-- Portfolio Cross-curricular themes
insert into LearningObject_CrossCurricularTheme(learningObject, crossCurricularTheme) values (101, 1);

-- Portfolio Key competences
insert into LearningObject_KeyCompetence(learningObject, keyCompetence) values (101, 1);

-- Portfolio taxons
insert into LearningObject_Taxon(learningObject, taxon) values(101, 21);
insert into LearningObject_Taxon(learningObject, taxon) values(103, 21);

-- Improper content

insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (1, 1, 102,'2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (2, 1,  2, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (3, 9, 2, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (4, 9, 3, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (5, 9, 103, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (6, 9, 104, '2014-06-01 00:00:01', true);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (7, 9, 4, '2014-06-01 00:00:01', true);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (15, 9, 15, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (34, 9, 34, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (35, 9, 35, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (36, 9, 36, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (37, 9, 37, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (38, 9, 38, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (39, 9, 39, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (115, 9, 115, '2014-06-01 00:00:01', false);
insert into ImproperContent(id, createdBy, learningObject, createdAt, reviewed) values (117, 9, 117, '2014-06-01 00:00:01', false);

-- Reviewable Change

insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource) values (15, 9, 15, '2014-06-01 00:00:01', false, 'www.bieber.ee');
insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource) values (34, 9, 34, '2014-06-01 00:00:01', false, 'www.bieber.ee');
insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource) values (35, 9, 35, '2014-06-01 00:00:01', false, 'www.bieber.ee');
insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource) values (36, 9, 36, '2014-06-01 00:00:01', false, 'www.bieber.ee');
insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource) values (37, 9, 37, '2014-06-01 00:00:01', false, 'www.bieber.ee');
insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource) values (38, 9, 38, '2014-06-01 00:00:01', false, 'www.bieber.ee');
insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource) values (39, 9, 39, '2014-06-01 00:00:01', false, 'www.bieber.ee');
insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource) values (8, 9, 8, '2014-06-01 00:00:01', false, 'www.bieber.ee');
insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource, taxon) values (115, 9, 115, '2014-06-01 00:00:01', false, NULL , 1);
insert into ReviewableChange(id, createdBy, learningObject, createdAt, reviewed, materialSource, taxon) values (117, 9, 117, '2014-06-01 00:00:01', false, NULL , 1);

-- TagUpVotes

insert into TagUpVote(id, user, learningObject, tag, deleted) values (1, 1, 101, 1, false);
insert into TagUpVote(id, user, learningObject, tag, deleted) values (2, 1, 1, 1, false);

-- UserLike

insert into UserLike(id, creator, learningObject, isLiked, added) values (1, 1, 1, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (2, 2, 1, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (3, 3, 1, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (4, 4, 1, 0, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (5, 5, 1, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (6, 1, 2, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (7, 2, 2, 1, null);

insert into UserLike(id, creator, learningObject, isLiked, added) values (8, 1, 103, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (9, 2, 103, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (10, 3, 103, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (11, 5, 103, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (12, 1, 104, 0, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (13, 2, 104, 0, null);

-- deleted material
insert into UserLike(id, creator, learningObject, isLiked, added) values (14, 4, 11, 0, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (15, 5, 11, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (16, 1, 11, 1, null);
insert into UserLike(id, creator, learningObject, isLiked, added) values (17, 2, 11, 1, null);


-- In the far past, should not count when looking for the most liked ones
insert into UserLike(id, creator, learningObject, isLiked, added) values (18, 2, 3, 0, '2014-06-01 00:00:01');
insert into UserLike(id, creator, learningObject, isLiked, added) values (19, 4, 103, 0, '2014-06-01 00:00:01');
insert into UserLike(id, creator, learningObject, isLiked, added) values (20, 6, 103, 0, '2015-01-11 00:00:01');

-- Uploaded files

insert into UploadedFile(id, name, path) values(1, 'bookCover.jpg', 'src/test/resources/bookCover.jpg');

-- Repository material URL-s

insert into RepositoryURL(id, baseURL, repository) values(1, 'koolielu.ee', 1);
insert into RepositoryURL(id, baseURL, repository) values(2, 'koolitaja.eenet.ee', 1);

-- PeerReviews

insert into PeerReview(url) values ('http://postimees.ee');
insert into PeerReview(url) values ('http://google.com');

-- PeerReview - Material

insert into Material_PeerReview(material, peerReview) values (2, 1);
insert into Material_PeerReview(material, peerReview) values (3, 2);

-- FirstReview

INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (1, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (2, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (3, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (4, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (5, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (6, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (7, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (8, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (9, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (10, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (11, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (12, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (13, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (14, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (15, 0, CURRENT_TIMESTAMP);

-- ReviewableChange data
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (16, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (17, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (18, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (19, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (20, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (21, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (22, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (23, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (24, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (25, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (26, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (27, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (28, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (29, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (30, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (31, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (32, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (33, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (34, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (35, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (36, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (37, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (38, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (39, 0, CURRENT_TIMESTAMP);

INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (101, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (102, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (103, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (104, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (105, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (106, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (107, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (108, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (109, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (110, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (111, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (112, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (113, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (114, 1, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (115, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (116, 0, CURRENT_TIMESTAMP);
INSERT into FirstReview(learningObject, reviewed, createdAt) VALUES (117, 0, CURRENT_TIMESTAMP);

-- Version

INSERT into Version(id, version, released) VALUES (1, 1.0, '2017-01-18 14:31:47');
INSERT into Version(id, version, released) VALUES (2, 2.0, '2017-02-18 14:31:47');

-- Media

INSERT into Media(id, url, createdBy, createdAt) VALUES (1, 'source123.com', 2, '2017-02-18 14:31:47');
