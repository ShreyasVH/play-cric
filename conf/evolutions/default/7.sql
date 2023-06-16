# --- !Ups

CREATE TABLE `man_of_the_series` (
    `id`                            bigint unsigned AUTO_INCREMENT NOT NULL,
    `series_id`                     bigint unsigned NOT NULL,
    `player_id`                     bigint unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `series` (`series_id`),
    KEY `player` (`player_id`),
    UNIQUE KEY `uk_mots_series_player` (`series_id`, `player_id`),
    CONSTRAINT `fk_mots_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_mots_player` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# --- !Downs

DROP TABLE IF EXISTS `man_of_the_series`;