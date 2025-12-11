CREATE TABLE IF NOT EXISTS skills (
  id CHAR(36) PRIMARY KEY,
  name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_skills (
  user_id  CHAR(36) NOT NULL,
  skill_id CHAR(36) NOT NULL,

  level INT NOT NULL DEFAULT 1,
  data JSON,

  PRIMARY KEY (user_id, skill_id),

  CONSTRAINT fk_user_skills_user FOREIGN KEY (user_id)
      REFERENCES users(id) ON DELETE CASCADE,

  CONSTRAINT fk_user_skills_skill FOREIGN KEY (skill_id)
      REFERENCES skills(id) ON DELETE CASCADE
);

