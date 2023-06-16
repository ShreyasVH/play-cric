# --- !Ups

CREATE TABLE `series_types` (
    `id`                        tinyint unsigned AUTO_INCREMENT NOT NULL,
    `name`                      varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_st_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `series_types` (`name`) VALUES
    ('Bilateral'),
    ('Tri series'),
    ('Tournament');

CREATE TABLE `game_types` (
    `id`                        tinyint unsigned AUTO_INCREMENT NOT NULL,
    `name`                      varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_gt_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `game_types` (`name`) VALUES
    ('ODI'),
    ('Test'),
    ('T20');

CREATE TABLE `series` (
  `id`                            bigint unsigned AUTO_INCREMENT NOT NULL,
  `name`                          varchar(50) NOT NULL,
  `home_country_id`               bigint unsigned NOT NULL,
  `tour_id`                       bigint unsigned NOT NULL,
  `type_id`                       tinyint unsigned NOT NULL,
  `game_type_id`                  tinyint unsigned NOT NULL,
  `start_time`                    datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `home_country` (`home_country_id`),
  KEY `tour` (`tour_id`),
  KEY `type` (`type_id`),
  KEY `game_type` (`game_type_id`),
  UNIQUE KEY `uk_s_name_tour_game_type` (`name`, `tour_id`, `game_type_id`),
  CONSTRAINT `fk_series_home_country` FOREIGN KEY (`home_country_id`) REFERENCES `countries` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_series_tour` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_series_type` FOREIGN KEY (`type_id`) REFERENCES `series_types` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_series_game_type` FOREIGN KEY (`game_type_id`) REFERENCES `series_types` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `series_teams_map` (
    `id`                            bigint unsigned AUTO_INCREMENT NOT NULL,
    `series_id`                     bigint unsigned NOT NULL,
    `team_id`                       bigint unsigned NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk` (`series_id`,`team_id`),
    KEY `series` (`series_id`),
    KEY `teams` (`team_id`),
    CONSTRAINT `fk_series_teams_map_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_series_teams_map_team` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# --- !Downs

DROP TABLE IF EXISTS `series_teams_map`;
DROP TABLE IF EXISTS `series`;
DROP TABLE IF EXISTS `game_types`;
DROP TABLE IF EXISTS `series_types`;