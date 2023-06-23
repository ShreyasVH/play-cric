# --- !Ups

CREATE TABLE `result_types` (
  `id`                            tinyint unsigned AUTO_INCREMENT NOT NULL,
  `name`                          varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rt_name` (`name`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `result_types` (`name`) VALUES
('Normal'),
('Tie'),
('Draw'),
('Super Over'),
('Washed Out'),
('Bowl Out'),
('Forfeit');

CREATE TABLE `win_margin_types` (
    `id`                            tinyint unsigned AUTO_INCREMENT NOT NULL,
    `name`                          varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_wmt_name` (`name`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `win_margin_types` (`name`) VALUES
('Run'),
('Wicket');

CREATE TABLE `matches` (
   `id`                            mediumint unsigned AUTO_INCREMENT NOT NULL,
   `series_id`                     bigint unsigned NOT NULL,
   `team_1_id`                     bigint unsigned NOT NULL,
   `team_2_id`                     bigint unsigned NOT NULL,
   `toss_winner_id`                bigint unsigned DEFAULT NULL,
   `bat_first_id`                  bigint unsigned DEFAULT NULL,
   `result_type_id`                tinyint unsigned NOT NULL,
   `winner_id`                     bigint unsigned DEFAULT NULL,
   `win_margin`                    smallint unsigned DEFAULT NULL,
   `win_margin_type_id`            tinyint unsigned DEFAULT NULL,
   `stadium_id`                    bigint unsigned NOT NULL,
   `start_time`                    datetime NOT NULL,
   `is_official`                   boolean NOT NULL default true,
   PRIMARY KEY (`id`),
   KEY `series` (`series_id`),
   KEY `team_1` (`team_1_id`),
   KEY `team_2` (`team_2_id`),
   KEY `toss_winner` (`toss_winner_id`),
   KEY `bat_first` (`bat_first_id`),
   KEY `result_type` (`result_type_id`),
   KEY `winner` (`winner_id`),
   KEY `win_margin_type` (`win_margin_type_id`),
   KEY `stadium` (`stadium_id`),
   UNIQUE KEY `uk_m_stadium_start` (`stadium_id`, `start_time`),
   CONSTRAINT `fk_m_series` FOREIGN KEY (`series_id`) REFERENCES `series` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
   CONSTRAINT `fk_m_team_1` FOREIGN KEY (`team_1_id`) REFERENCES `teams` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
   CONSTRAINT `fk_m_team_2` FOREIGN KEY (`team_2_id`) REFERENCES `teams` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
   CONSTRAINT `fk_m_toss_winner` FOREIGN KEY (`toss_winner_id`) REFERENCES `teams` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
   CONSTRAINT `fk_m_bat_first` FOREIGN KEY (`bat_first_id`) REFERENCES `teams` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
   CONSTRAINT `fk_m_result_type` FOREIGN KEY (`result_type_id`) REFERENCES `result_types` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
   CONSTRAINT `fk_m_winner` FOREIGN KEY (`winner_id`) REFERENCES `teams` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
   CONSTRAINT `fk_m_win_margin_type` FOREIGN KEY (`win_margin_type_id`) REFERENCES `win_margin_types` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT,
   CONSTRAINT `fk_m_stadium` FOREIGN KEY (`stadium_id`) REFERENCES `stadiums` (`id`) on DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `match_player_map` (
    `id`                            mediumint unsigned AUTO_INCREMENT NOT NULL,
    `match_id`                      mediumint unsigned NOT NULL,
    `player_id`                     bigint unsigned NOT NULL,
    `team_id`                       bigint unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `match` (`match_id`),
    KEY `team` (`team_id`),
    KEY `player` (`player_id`),
    UNIQUE KEY `uk_mpm_match_player_team` (`match_id`, `player_id`, `team_id`),
    CONSTRAINT `fk_mpm_match` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_mpm_player` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_mpm_team` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `dismissal_modes` (
   `id`                            tinyint unsigned AUTO_INCREMENT NOT NULL,
   `name`                          varchar(30),
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_dm_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `dismissal_modes` (`name`) VALUES
('Bowled'),
('Caught'),
('LBW'),
('Run Out'),
('Stumped'),
('Hit Twice'),
('Hit Wicket'),
('Obstructing the Field'),
('Timed Out'),
('Retired Hurt'),
('Handled the Ball');

CREATE TABLE `batting_scores` (
  `id`                            mediumint unsigned AUTO_INCREMENT NOT NULL,
  `match_player_id`               mediumint unsigned NOT NULL,
  `runs`                          smallint unsigned NOT NULL DEFAULT '0',
  `balls`                         smallint unsigned NOT NULL DEFAULT '0',
  `fours`                         tinyint unsigned NOT NULL DEFAULT '0',
  `sixes`                         tinyint unsigned NOT NULL DEFAULT '0',
  `dismissal_mode_id`             tinyint unsigned DEFAULT NULL,
  `bowler_id`                     mediumint unsigned DEFAULT NULL,
  `innings`                       tinyint unsigned NOT NULL,
  `number`                        tinyint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `match_player` (`match_player_id`),
  KEY `dismissal_mode` (`dismissal_mode_id`),
  KEY `bowler` (`bowler_id`),
  UNIQUE KEY `uk_bs_match_player_innings` (`match_player_id`, `innings`),
  CONSTRAINT `fk_bs_match_player` FOREIGN KEY (`match_player_id`) REFERENCES `match_player_map` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_bs_dismissal_mode` FOREIGN KEY (`dismissal_mode_id`) REFERENCES `dismissal_modes` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_bs_bowler` FOREIGN KEY (`bowler_id`) REFERENCES `match_player_map` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `bowling_figures` (
   `id`                            mediumint unsigned AUTO_INCREMENT NOT NULL,
   `match_player_id`               mediumint unsigned NOT NULL,
   `balls`                         smallint unsigned DEFAULT '0',
   `maidens`                       tinyint unsigned DEFAULT '0',
   `runs`                          smallint unsigned DEFAULT '0',
   `wickets`                       tinyint unsigned DEFAULT '0',
   `innings`                    tinyint unsigned NOT NULL,
   PRIMARY KEY (`id`),
   KEY `match_player` (`match_player_id`),
   UNIQUE KEY `uk_bf_match_player_innings` (`match_player_id`, `innings`),
   CONSTRAINT `fk_bf_match_player` FOREIGN KEY (`match_player_id`) REFERENCES `match_player_map` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create table fielder_dismissals (
    `id`                            mediumint unsigned AUTO_INCREMENT NOT NULL,
    `score_id`                      mediumint unsigned NOT NULL,
    `match_player_id`               mediumint unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `score` (`score_id`),
    KEY `match_player` (`match_player_id`),
    UNIQUE KEY `uk_fd_score_player_team` (`score_id`, `match_player_id`),
    CONSTRAINT `fk_fd_score` FOREIGN KEY (`score_id`) REFERENCES `batting_scores` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_fd_match_player` FOREIGN KEY (`match_player_id`) REFERENCES `match_player_map` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `extras_types` (
    `id`                            tinyint unsigned AUTO_INCREMENT NOT NULL,
    `name`                          varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_et_name` (`name`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `extras_types` (`name`) VALUES
('Bye'),
('Leg Bye'),
('Wide'),
('No Ball'),
('Penalty');

CREATE TABLE `extras` (
  `id`                            mediumint unsigned AUTO_INCREMENT NOT NULL,
  `match_id`                      mediumint unsigned NOT NULL,
  `type_id`                       tinyint unsigned NOT NULL,
  `runs`                          tinyint unsigned NOT NULL,
  `batting_team_id`               bigint unsigned NOT NULL,
  `bowling_team_id`               bigint unsigned NOT NULL,
  `innings`                    tinyint unsigned not null,
  PRIMARY KEY (`id`),
  KEY `match` (`match_id`),
  KEY `type` (`type_id`),
  KEY `batting_team` (`batting_team_id`),
  KEY `bowling_team` (`bowling_team_id`),
  UNIQUE KEY `uk_e_match_type_batting_innings` (`match_id`, `type_id`, `batting_team_id`, `innings`),
  CONSTRAINT `fk_e_match` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_e_type` FOREIGN KEY (`type_id`) REFERENCES `extras_types` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_e_batting_team` FOREIGN KEY (`batting_team_id`) REFERENCES `teams` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_e_bowling_team` FOREIGN KEY (`bowling_team_id`) REFERENCES `teams` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `captains` (
    `id` mediumint UNSIGNED NOT NULL AUTO_INCREMENT,
    `match_player_id` mediumint UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    KEY `match_player` (`match_player_id`),
    UNIQUE KEY `uk_c_match_player` (`match_player_id`),
    CONSTRAINT `fk_c_match_player` FOREIGN KEY (`match_player_id`) REFERENCES `match_player_map` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB;

CREATE TABLE `wicket_keepers` (
    `id` mediumint UNSIGNED NOT NULL AUTO_INCREMENT,
    `match_player_id` mediumint UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    KEY `match_player` (`match_player_id`),
    UNIQUE KEY `uk_wk_match_player` (`match_player_id`),
    CONSTRAINT `fk_wk_match_player` FOREIGN KEY (`match_player_id`) REFERENCES `match_player_map` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB;

CREATE TABLE `man_of_the_match` (
    `id` mediumint UNSIGNED NOT NULL AUTO_INCREMENT,
    `match_player_id` mediumint UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    KEY `match_player` (`match_player_id`),
    UNIQUE KEY `uk_motm_match_player` (`match_player_id`),
    CONSTRAINT `fk_motm_match_player` FOREIGN KEY (`match_player_id`) REFERENCES `match_player_map` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB;

# --- !Downs

DROP TABLE IF EXISTS `man_of_the_match`;
DROP TABLE IF EXISTS `wicket_keepers`;
DROP TABLE IF EXISTS `captains`;
DROP TABLE IF EXISTS `extras`;
DROP TABLE IF EXISTS `extras_types`;
DROP TABLE IF EXISTS `fielder_dismissals`;
DROP TABLE IF EXISTS `bowling_figures`;
DROP TABLE IF EXISTS `batting_scores`;
DROP TABLE IF EXISTS `dismissal_modes`;
DROP TABLE IF EXISTS `match_player_map`;
DROP TABLE IF EXISTS `matches`;
DROP TABLE IF EXISTS `win_margin_types`;
DROP TABLE IF EXISTS `result_types`;