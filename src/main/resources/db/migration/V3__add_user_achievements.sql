CREATE TABLE IF NOT EXISTS user_achievements (
  user_id CHAR(36) NOT NULL,
  achievement_key VARCHAR(32) NOT NULL,
  progress_count INT NOT NULL DEFAULT 0,

  PRIMARY KEY (user_id, achievement_key),

  CONSTRAINT fk_achievements_user FOREIGN KEY (user_id)
      REFERENCES users(id) ON DELETE CASCADE
);
