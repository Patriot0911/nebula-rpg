DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id UUID PRIMARY KEY,
  nickname VARCHAR(32) NOT NULL,
  level INT NOT NULL DEFAULT 1,
  exp INT NOT NULL DEFAULT 0
);

CREATE TABLE skills (
  id UUID PRIMARY KEY,
  name VARCHAR(64) NOT NULL
);

CREATE TABLE user_skills (
  user_id      UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  skill_id     UUID         NOT NULL REFERENCES skills(id) ON DELETE CASCADE,

  level        INT NOT NULL DEFAULT 1,
  data         JSONB,

  PRIMARY KEY (user_id, skill_id)
);

