CREATE TABLE IF NOT EXISTS `travel` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  departure_day DATE NOT NULL,
  travel_duration ENUM('DAY', 'WEEKEND', 'WEEK', 'HOLIDAYS') NOT NULL,
  transport_type ENUM('BUS', 'PLANE', 'TRAIN', 'SHIP') NOT NULL,
  number_of_nights INT(10) NOT NULL,
  destination_id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE `travel`
ADD CONSTRAINT fk_destination3 FOREIGN KEY (destination_id) REFERENCES `destination`(id);