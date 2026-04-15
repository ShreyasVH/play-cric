# --- !Ups

INSERT INTO tags (name) VALUES
    ('CPL'),
    ('CHALLENGER'),
    ('QUALIFIER_1'),
    ('QUALIFIER_2');


# --- !Downs

DELETE FROM tags WHERE name in ('CPL', 'CHALLENGER', 'QUALIFIER_1', 'QUALIFIER_2');