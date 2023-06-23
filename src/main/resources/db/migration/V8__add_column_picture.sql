ALTER TABLE `destination`
ADD COLUMN picture_url VARCHAR(1000) NOT NULL AFTER description;

ALTER TABLE `hotel`
ADD COLUMN picture_url VARCHAR(1000) NOT NULL AFTER price_per_night;

ALTER TABLE `landmark`
ADD COLUMN picture_url VARCHAR(1000) NOT NULL AFTER category;