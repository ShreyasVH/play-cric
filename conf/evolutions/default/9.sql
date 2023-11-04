# --- !Ups
ALTER TABLE `series` CHANGE `name` `name` VARCHAR(100) NOT NULL;

# --- !Downs
ALTER TABLE `series` CHANGE `name` `name` VARCHAR(50) NOT NULL;