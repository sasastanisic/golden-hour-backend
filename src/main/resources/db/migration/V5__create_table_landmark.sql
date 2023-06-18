CREATE TABLE IF NOT EXISTS `landmark` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(2000) NOT NULL,
  price DOUBLE NOT NULL,
  category VARCHAR(50) NOT NULL,
  destination_id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE `landmark`
ADD CONSTRAINT fk_destination2 FOREIGN KEY (destination_id) REFERENCES `destination`(id);