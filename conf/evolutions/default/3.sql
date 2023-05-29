# --- !Ups

CREATE TABLE `team_types` (
    `id`                            tinyint unsigned AUTO_INCREMENT NOT NULL,
    `name`                          varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tt_name` (`name`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `team_types` (`name`) VALUES
    ('International'),
    ('Domestic'),
    ('Franchise');

CREATE TABLE `teams` (
    `id`                            int unsigned AUTO_INCREMENT NOT NULL,
    `name`                          varchar(100) NOT NULL,
    `country_id`                    bigint unsigned NOT NULL,
    `type_id`                       tinyint unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `country` (`country_id`),
    UNIQUE KEY `uk_t_name_country_type` (`name`, `country_id`, `type_id`),
    CONSTRAINT `fk_teams_country_id` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_teams_type_id` FOREIGN KEY (`type_id`) REFERENCES `team_types` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# --- !Downs

DROP TABLE IF EXISTS teams;

DROP TABLE IF EXISTS team_types;