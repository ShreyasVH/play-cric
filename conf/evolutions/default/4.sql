# --- !Ups

CREATE TABLE `tours` (
   `id`                            bigint unsigned AUTO_INCREMENT NOT NULL,
   `name`                          varchar(100) NOT NULL,
   `start_time`                    datetime NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_t_name_start_time` (`name`, `start_time`)
);

# --- !Downs

DROP TABLE IF EXISTS tours;


