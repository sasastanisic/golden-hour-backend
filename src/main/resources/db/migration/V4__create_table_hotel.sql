CREATE TABLE IF NOT EXISTS `hotel` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  stars INT NOT NULL,
  hotel_type VARCHAR(50) NOT NULL,
  available_rooms INT NOT NULL,
  price_per_night DOUBLE NOT NULL,
  destination_id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE `hotel`
ADD CONSTRAINT fk_destination FOREIGN KEY (destination_id) REFERENCES `destination`(id);