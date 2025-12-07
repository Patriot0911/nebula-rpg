DROP TABLE IF EXISTS user_achievements;

CREATE TABLE user_achievements (
  user_id             UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  achievement_key     String,

  PRIMARY KEY (user_id, achievement_id)
);

