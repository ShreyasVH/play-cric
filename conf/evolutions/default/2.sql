# --- !Ups

ALTER TABLE `countries` CHANGE `id` `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

CREATE TABLE `stadiums` (
    `id`                            bigint unsigned AUTO_INCREMENT NOT NULL,
    `name`                          varchar(200) NOT NULL,
    `city`                          varchar(100) NOT NULL,
    `state`                         varchar(100) DEFAULT NULL,
    `country_id`                    bigint unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `country` (`country_id`),
    UNIQUE KEY `uk_s_name_country` (`name`, `country_id`),
    CONSTRAINT `fk_stadiums_country_id` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# --- !Downs

drop table if exists stadiums;

ALTER TABLE `countries` CHANGE `id` `id` BIGINT NOT NULL AUTO_INCREMENT;

