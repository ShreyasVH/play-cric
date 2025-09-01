# --- !Ups

create table `totals` (
   id                            bigint unsigned auto_increment not null,
   match_id                      mediumint unsigned not null,
   team_id                      bigint unsigned not null,
   runs                         smallint not null,
   wickets                      tinyint not null,
   balls                        smallint not null,
   innings                      tinyint not null,
   UNIQUE KEY `uk_t_match_team_innings` (`match_id`, `team_id`, `innings`),
   CONSTRAINT `fk_totals_match_id` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`),
   CONSTRAINT `fk_totals_team_id` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`),
   constraint pk_totals primary key (id)
);



# --- !Downs

DROP TABLE `totals`;