CREATE TABLE IF NOT EXISTS users (
  id CHAR(36) PRIMARY KEY,
  nickname VARCHAR(32) NOT NULL,
  level INT NOT NULL DEFAULT 1,
  exp INT NOT NULL DEFAULT 0
);
CREATE UNIQUE INDEX idx_users_nickname ON users(nickname);
