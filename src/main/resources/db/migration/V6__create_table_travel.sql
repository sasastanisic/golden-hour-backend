CREATE TABLE IF NOT EXISTS `travel` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  departure_day DATE NOT NULL,
  travel_duration VARCHAR(50) NOT NULL,
  transport_type VARCHAR(50) NOT NULL,
  number_of_nights INT NOT NULL,
  destination_id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE `travel`
ADD CONSTRAINT fk_destination3 FOREIGN KEY (destination_id) REFERENCES `destination`(id);