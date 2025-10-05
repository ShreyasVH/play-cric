# --- !Ups

ALTER TABLE `series` CHANGE `id` `id` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT;

create table `tags` (
  `id`                            smallint unsigned auto_increment not null,
  `name`                          varchar(100) NOT NULL,
  UNIQUE KEY `uk_ta_name` (`name`),
  constraint pk_tags primary key (id)
);

INSERT INTO `tags` (`name`) VALUES
('WORLD_CUP'),
('IPL'),
('CHAMPIONS_TROPHY'),
('BBL'),
('ILT20'),
('CHAMPIONS_LEAGUE'),
('ASIA_CUP'),
('WTC');

create table `tags_map` (
    `id`                            bigint unsigned auto_increment not null,
    `entity_type`                   varchar(100) NOT NULL,
    `entity_id`                     mediumint unsigned NOT NULL,
    `tag_id`                        smallint unsigned NOT NULL,
    UNIQUE KEY `uk_tm_type_id_tag` (`entity_type`, `entity_id`, `tag_id`),
    CONSTRAINT `fk_tm_tag` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
    constraint pk_tags_map primary key (id)
);



# --- !Downs

DROP TABLE `tags_map`;

DROP TABLE `tags`;

ALTER TABLE `series` CHANGE `id` `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;