CREATE TABLE IF NOT EXISTS `booking` (
  id BIGINT NOT NULL AUTO_INCREMENT,
  number_of_people INT(10) NOT NULL,
  own_transport BOOLEAN NOT NULL,
  total_price DOUBLE NOT NULL,
  user_id BIGINT NOT NULL,
  travel_id BIGINT NOT NULL,
  hotel_id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE `booking`
ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES `user`(id);

ALTER TABLE `booking`
ADD CONSTRAINT fk_travel FOREIGN KEY (travel_id) REFERENCES `travel`(id);

ALTER TABLE `booking`
ADD CONSTRAINT fk_hotel FOREIGN KEY (hotel_id) REFERENCES `hotel`(id);