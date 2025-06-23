# --- !Ups

ALTER TABLE `cric_play`.`stadiums` DROP INDEX `uk_s_name_country`, ADD UNIQUE `uk_s_name_country_city` (`name`, `country_id`, `city`);

# --- !Downs

ALTER TABLE `cric_play`.`stadiums` DROP INDEX `uk_s_name_country_city`, ADD UNIQUE `uk_s_name_country` (`name`, `country_id`);