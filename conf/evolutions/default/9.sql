# --- !Ups

CREATE INDEX idx_mpm_player_team ON match_player_map (player_id, team_id);

CREATE INDEX idx_bs_match_player_dismissal ON batting_scores (match_player_id, dismissal_mode_id);

# --- !Downs

DROP INDEX idx_bs_match_player_dismissal;
DROP INDEX idx_mpm_player_team;