CREATE TABLE IF NOT EXISTS `review` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  rating INT NOT NULL,
  comment VARCHAR(500),
  travel_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE `review`
ADD CONSTRAINT fk_travel2 FOREIGN KEY (travel_id) REFERENCES `travel`(id);

ALTER TABLE `review`
ADD CONSTRAINT fk_user2 FOREIGN KEY (user_id) REFERENCES `user`(id);