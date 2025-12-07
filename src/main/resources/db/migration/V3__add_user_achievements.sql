DROP TABLE IF EXISTS user_achievements;

CREATE TABLE user_achievements (
  user_id             UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  achievement_key     VARCHAR(32) NOT NULL,
  progress_count      INT NOT NULL DEFAULT 0,

  PRIMARY KEY (user_id, achievement_key)
);

