# --- !Ups

INSERT INTO `result_types` (`name`) VALUES ('Least Wickets'), ('Boundary Count');

# --- !Downs

DELETE FROM `result_types` WHERE `name` in ('Least Wickets', 'Boundary Count');