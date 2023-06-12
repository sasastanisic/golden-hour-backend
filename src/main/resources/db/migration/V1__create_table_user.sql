CREATE TABLE IF NOT EXISTS `user` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  surname VARCHAR(100) NOT NULL,
  age INT NOT NULL,
  email VARCHAR(100) NOT NULL,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_email (email),
  UNIQUE KEY uk_username (username)
);