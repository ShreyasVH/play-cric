# --- !Ups

CREATE TABLE `players` (
   `id`                            bigint unsigned AUTO_INCREMENT NOT NULL,
   `name`                          varchar(50) NOT NULL,
   `country_id`                    bigint unsigned NOT NULL,
   `date_of_birth`                 DATE NULL,
   `image`                         varchar(255) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `country` (`country_id`),
   UNIQUE KEY `uk_p_name_country_dob` (`country_id`, `name`, `date_of_birth`),
   CONSTRAINT `fk_players_country_id` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# --- !Downs

DROP TABLE IF EXISTS `players`;