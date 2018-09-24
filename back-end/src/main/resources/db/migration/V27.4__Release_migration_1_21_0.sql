SET foreign_key_checks = 0;

ALTER TABLE LearningObject ADD promoted boolean not null default true;

SET foreign_key_checks = 1;