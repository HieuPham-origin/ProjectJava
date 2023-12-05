DROP DATABASE IF EXISTS Booking_Flight_Service;
CREATE DATABASE Booking_Flight_Service;
USE Booking_Flight_Service;

CREATE TABLE `Ticket_class`(
    `class_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `class_name` VARCHAR(255) NOT NULL,
    `rate` INT NOT NULL,
    `cabin_baggage` INT NOT NULL,
    `baggage` INT NOT NULL
);

CREATE TABLE `Airport`(
    `airport_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `airport_name` VARCHAR(255) NOT NULL,
    `city` VARCHAR(255) NOT NULL,
    `country` VARCHAR(255) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `airport_code` VARCHAR(255) NOT NULL
);

CREATE TABLE `Account`(
    `account_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(255) NOT NULL
);

-- No data
CREATE TABLE `Reservation`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `code` VARCHAR(255) NOT NULL,
    `time_created` VARCHAR(255) NOT NULL,
    `total` INT,
    `account_id` INT NOT NULL,
    FOREIGN KEY(`account_id`) REFERENCES `Account`(`account_id`)
);

CREATE TABLE `Plane`(
    `plane_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `plane_name` VARCHAR(255) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `capacity` INT NOT NULL
);

CREATE TABLE `Baggage`(
    `baggage_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `baggage_name` VARCHAR(255) NOT NULL,
    `weight` INT NOT NULL,
    `price` INT NOT NULL
);

CREATE TABLE `Service`(
    `service_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `service_name` VARCHAR(255) NOT NULL,
    `price` INT NOT NULL,
    `description` VARCHAR(255) NOT NULL
);

CREATE TABLE `Customer_type`(
    `type_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `type_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `Flight`(
    `flight_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `departure_airport_id` INT NOT NULL,
    `arrival_airport_id` INT NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `flight_airline` VARCHAR(50) NOT NULL,
    `flight_price` INT NOT NULL,
    FOREIGN KEY(`departure_airport_id`) REFERENCES `Airport`(`airport_id`),
    FOREIGN KEY(`arrival_airport_id`) REFERENCES `Airport`(`airport_id`)
);

CREATE TABLE `Flight_Plane`(
    `flight_id` INT NOT NULL,
    `plane_id` INT NOT NULL,
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `departure_time` TIME NOT NULL,
    `arrival_time` TIME NOT NULL,
    `departure_day` DATE NOT NULL,
    `arrival_day` DATE NOT NULL,
    FOREIGN KEY(`flight_id`) REFERENCES `Flight`(`flight_id`),
    FOREIGN KEY(`plane_id`) REFERENCES `Plane`(`plane_id`)
);

CREATE TABLE `Seat`(
    `seat_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `seat_number` VARCHAR(255) NOT NULL,
    `plane_id` INT NOT NULL,
    FOREIGN KEY(`plane_id`) REFERENCES `Plane`(`plane_id`)
);

CREATE TABLE `Seat_Detail`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `is_taken` BOOLEAN NOT NULL,
    `flight_plane_id` INT NOT NULL,
    `seat_id` INT NOT NULL,
    FOREIGN KEY(`flight_plane_id`) REFERENCES `Flight_Plane`(`id`),
    FOREIGN KEY(`seat_id`) REFERENCES `Seat`(`seat_id`)
);

CREATE TABLE `Passenger` (
    `passenger_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `gender` VARCHAR(255),
    `date_of_birth` DATETIME,
    `phone_number` VARCHAR(255),
    `email` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255),
    `country` VARCHAR(255),
    `type` INT,
    FOREIGN KEY (`type`) REFERENCES `Customer_type` (`type_id`)
);

-- No data
CREATE TABLE `Ticket`(
    `ticket_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `seat_detail_id` INT NOT NULL,
    `class_id` INT NOT NULL,
    `total_price` INT NOT NULL,
    `tax` INT NOT NULL,
    `service_id` INT NOT NULL,
    `baggage_id` INT NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `passenger_id` INT NOT NULL,
    `reservation_id` INT NOT NULL,
    `day_order` DATETIME NOT NULL,
    `day_pay` DATETIME NOT NULL,
    FOREIGN KEY(`baggage_id`) REFERENCES `Baggage`(`baggage_id`),
    FOREIGN KEY(`service_id`) REFERENCES `Service`(`service_id`),
    FOREIGN KEY(`passenger_id`) REFERENCES `Passenger`(`passenger_id`),
    FOREIGN KEY(`reservation_id`) REFERENCES `Reservation`(`id`),
    FOREIGN KEY(`class_id`) REFERENCES `Ticket_class`(`class_id`),
    FOREIGN KEY(`seat_detail_id`) REFERENCES `Seat_Detail`(`id`)
);

-- No data
CREATE TABLE `Ticket_Detail`(
    `type_id` INT NOT NULL,
    `ticket_id` INT NOT NULL,
    `price` INT NOT NULL,
    PRIMARY KEY (`type_id`, `ticket_id`),
    FOREIGN KEY(`type_id`) REFERENCES `Customer_Type`(`type_id`),
    FOREIGN KEY(`ticket_id`) REFERENCES `Ticket`(`ticket_id`)
);

-- Tạo dữ liệu cho bảng Customer_type
INSERT INTO `Customer_type` (`type_name`) VALUES
('adult'),
('child'),
('infant');

-- Tạo dữ liệu cho bảng Ticket_Class
INSERT INTO Ticket_class (class_name, rate, cabin_baggage, baggage)
VALUES
    ('Economy', 1, 20, 7),
    ('Business', 2, 25, 9),
    ('First Class', 3, 30, 10);

-- Tạo dữ liệu cho bảng Airport
INSERT INTO `Airport` (`airport_name`, `city`, `country`, `status`, `airport_code`) VALUES
('Ha Noi Airport', 'Ha Noi', 'Vietnam', 'Active', 'HN'),
('Tan Son Nhat Airport', 'Ho Chi Minh', 'Vietnam', 'Active', 'TSN'),
('Da Nang Airport', 'Da Nang', 'Vietnam', 'Active', 'DN'),
('Hai Phong Airport', 'Hai Phong', 'Vietnam', 'Active', 'HP'),
('Vienna International Airport', 'Vienna', 'Austria', 'Active', 'VIE'),
('John F. Kennedy International Airport', 'New York', 'United States', 'Active', 'JFK'),
('Heathrow Airport', 'London', 'United Kingdom', 'Active', 'LHR'),
('Charles de Gaulle Airport', 'Paris', 'France', 'Active', 'CDG'),
('Los Angeles International Airport', 'Los Angeles', 'United States', 'Active', 'LAX'),
('Beijing Capital International Airport', 'Beijing', 'China', 'Active', 'PEK'),
('Dubai International Airport', 'Dubai', 'United Arab Emirates', 'Active', 'DXB'),
('Singapore Changi Airport', 'Singapore', 'Singapore', 'Active', 'SIN'),
('Incheon International Airport', 'Seoul', 'South Korea', 'Active', 'ICN'),
('Frankfurt Airport', 'Frankfurt', 'Germany', 'Active', 'FRA'),
('Sydney Airport', 'Sydney', 'Australia', 'Active', 'SYD'),
('Hamad International Airport', 'Doha', 'Qatar', 'Active', 'DOH'),
('Hong Kong International Airport', 'Hong Kong', 'China', 'Active', 'HKG'),
('Leonardo da Vinci–Fiumicino Airport', 'Rome', 'Italy', 'Active', 'FCO'),
('Tokyo Haneda Airport', 'Tokyo', 'Japan', 'Active', 'HND'),
('Amsterdam Airport Schiphol', 'Amsterdam', 'Netherlands', 'Active', 'AMS');

-- Tạo dữ liệu cho bảng Plane
INSERT INTO `Plane` (`plane_name`, `status`, `capacity`) VALUES
('Boeing 737', 'Active', 150),
('Airbus A320', 'Active', 140),
('Boeing 747', 'Active', 250),
('Airbus A380', 'Active', 250),
('Embraer E190', 'Active', 100),
('Bombardier CRJ-900', 'Active', 100),
('Boeing 777', 'Active', 250),
('Airbus A330', 'Active', 250),
('Boeing 757', 'Active', 200),
('Airbus A350', 'Active', 250),
('Boeing 767', 'Active', 220),
('Embraer E145', 'Active', 150),
('Bombardier Q400', 'Active', 170),
('Boeing 787', 'Active', 250),
('Airbus A319', 'Active', 120),
('Boeing 717', 'Active', 100),
('Airbus A321', 'Active', 200),
('McDonnell Douglas MD-80', 'Active', 160),
('Embraer E170', 'Active', 180),
('Bombardier CRJ-200', 'Active', 160),
('Boeing 737 MAX', 'Active', 170),
('Airbus A330neo', 'Active', 250),
('Boeing 757-300', 'Active', 230),
('Airbus A321XLR', 'Active', 220),
('Boeing 767-400ER', 'Active', 240),
('Embraer E195', 'Active', 110),
('Bombardier CRJ-1000', 'Active', 120),
('Airbus A330-800neo', 'Active', 230),
('Boeing 737-800', 'Active', 160),
('Boeing 737-900ER', 'Active', 160);

-- Tạo dữ liệu cho bảng Seat
DELIMITER $$
CREATE PROCEDURE InsertSeats()
BEGIN
  DECLARE plane_id_var INT;
  DECLARE capacity_var INT;
  DECLARE row_number_var INT DEFAULT 1;
  DECLARE seat_number_var VARCHAR(255);
  DECLARE seat_count INT;

  -- Cursor to iterate over the planes
  DECLARE plane_cursor CURSOR FOR
    SELECT plane_id, capacity
    FROM Plane;

  -- Cursor to generate seat numbers
  DECLARE seat_cursor CURSOR FOR
    SELECT CONCAT(FLOOR((row_number_var - 1) / 6) + 1, CHAR(65 + ((row_number_var - 1) % 6))) AS seat_number;

  -- Declare handlers for exceptions
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET @done = TRUE;

  OPEN plane_cursor;

  -- Loop over planes
  plane_loop: LOOP
    FETCH plane_cursor INTO plane_id_var, capacity_var;

    IF (@done) THEN
      LEAVE plane_loop;
    END IF;

    SET seat_count = 0;
    SET row_number_var = 1;

    -- Loop to insert seats
    seat_loop: LOOP
      IF (seat_count = capacity_var) THEN
        LEAVE seat_loop;
      END IF;

      OPEN seat_cursor;
      FETCH seat_cursor INTO seat_number_var;

      -- Insert seat
      INSERT INTO Seat (seat_number, plane_id)
      VALUES (seat_number_var, plane_id_var);

      CLOSE seat_cursor;

      SET seat_count = seat_count + 1;
      SET row_number_var = row_number_var + 1;
    END LOOP seat_loop;
  END LOOP plane_loop;

  CLOSE plane_cursor;
END$$
DELIMITER ;

-- Call the stored procedure to insert seats
CALL InsertSeats();

-- Tạo dữ liệu cho bảng Baggage
INSERT INTO `Baggage` (`baggage_name`, `weight`, `price`) VALUES
('Small Carry-On', 5, 20),
('Standard Carry-On', 8, 25),
('Large Carry-On', 10, 30),
('Personal Item', 3, 15),
('Checked Bag - Small', 15, 40),
('Checked Bag - Medium', 25, 50),
('Checked Bag - Large', 35, 60),
('Extra Large Checked Bag', 50, 80),
('Sports Equipment - Golf Bag', 15, 30),
('Sports Equipment - Ski Bag', 20, 35),
('Sports Equipment - Surfboard', 25, 40),
('Special Baggage - Musical Instrument', 10, 25),
('Special Baggage - Pet Carrier', 8, 20),
('Special Baggage - Bicycle', 30, 45),
('Special Baggage - Baby Stroller', 7, 15),
('Special Baggage - Wheelchair', 15, 25),
('Special Baggage - Fishing Equipment', 12, 30),
('Special Baggage - Diving Gear', 20, 35),
('Special Baggage - Snowboard Bag', 18, 40),
('Checked Bag - XL', 40, 70),
('Special Baggage - Portable Grill', 10, 25),
('Sports Equipment - Tennis Racket Bag', 5, 15),
('Special Baggage - Art Supplies', 8, 20),
('Checked Bag - XXL', 50, 90),
('Special Baggage - Camping Gear', 25, 40),
('Sports Equipment - Hockey Bag', 30, 45),
('Special Baggage - Telescope', 12, 30),
('Special Baggage - Wedding Dress', 8, 20),
('Special Baggage - Surfboard', 20, 35),
('Special Baggage - Skis and Poles', 15, 30);

-- Tạo dữ liệu cho bảng Service
INSERT INTO `Service` (`service_name`, `price`, `description`) VALUES
('Priority Boarding', 15, 'Get on the plane early and secure your seat'),
('In-Flight Wi-Fi', 10, 'Stay connected with high-speed internet during your flight'),
('Extra Legroom Seat', 20, 'Enjoy more space for a comfortable journey'),
('Onboard Meals', 25, 'Choose from a variety of delicious in-flight meal options'),
('Airport Lounge Access', 30, 'Relax in style before your flight with access to airport lounges'),
('Travel Insurance', 15, 'Ensure peace of mind with comprehensive travel insurance coverage'),
('Extra Baggage Allowance', 40, 'Bring more luggage without worrying about excess fees'),
('Seat Selection', 12, 'Choose your preferred seat for a personalized flying experience'),
('Car Rental Service', 35, 'Book a car for convenient transportation at your destination'),
('Airport Shuttle Service', 18, 'Enjoy hassle-free transportation between the airport and your accommodation'),
('Hotel Booking Assistance', 25, 'Get assistance in finding and booking the perfect hotel for your stay'),
('Flight Insurance', 20, 'Protect your journey with insurance specifically for flight-related incidents'),
('Pet Transportation Service', 50, 'Travel with your furry friend using our pet transportation service'),
('Child Care Service', 30, 'Ensure your child is comfort and safety during the flight'),
('Special Assistance', 15, 'Receive additional assistance for passengers with special needs'),
('Unaccompanied Minor Service', 25, 'Ensure the safe and supervised travel of unaccompanied minors'),
('Chauffeur Service', 40, 'Arrive in style with a chauffeur-driven car service'),
('Airport Fast Track', 22, 'Skip the lines at the airport with fast-track services'),
('Cabin Upgrade', 50, 'Experience a more luxurious journey with a cabin upgrade'),
('Entertainment Package', 12, 'Enjoy a variety of entertainment options during your flight'),
('VIP Lounge Access', 40, 'Indulge in luxury with access to exclusive VIP lounges'),
('Duty-Free Shopping', 18, 'Shop for a variety of products at duty-free prices'),
('Spa and Wellness Services', 35, 'Relax with spa and wellness services at the airport'),
('Gourmet Dining Experience', 45, 'Savor gourmet meals prepared by top chefs during your flight'),
('Language Translation Service', 15, 'Get assistance with language translation during your journey'),
('Baggage Delivery Service', 30, 'Have your luggage delivered directly to your destination'),
('Mobile Charging Service', 10, 'Stay powered up with mobile charging services at the airport'),
('City Tour Assistance', 25, 'Explore the destination with guided city tour assistance'),
('Concierge Service', 28, 'Experience personalized concierge services for a seamless journey'),
('Private Jet Charter', 200, 'Travel in ultimate luxury with a private jet charter service');

-- Tạo dữ liệu cho bảng Passenger
INSERT INTO `Passenger` (`first_name`, `last_name`, `gender`, `date_of_birth`, `phone_number`, `email`, `address`, `country`, `type`)
VALUES
('David', 'Luis', 'Male', '2003-11-20', '357-456-0798', 'passenger@gmail.com', '19 TDTU', 'VN', 1),
('John', 'Doe', 'Male', '2000-01-01', '123-456-7890', 'john.doe@email.com', '123 Main St', 'USA', 1),
('Jane', 'Smith', 'Female', '2002-11-01', '987-654-3210', 'jane.smith@email.com', '456 Oak St', 'Canada', 2),
('Mike', 'Johnson', 'Male', '2003-10-11', '555-123-4567', 'mike.johnson@email.com', '789 Elm St', 'UK', 3),
('Emily', 'Davis', 'Female', '2002-03-14', '111-222-3333', 'emily.davis@email.com', '101 Pine St', 'Australia', 1),
('Robert', 'Brown', 'Male', '2000-02-01', '999-888-7777', 'robert.brown@email.com', '202 Cedar St', 'Germany', 2),
('Linda', 'Miller', 'Female', '2000-11-05', '444-555-6666', 'linda.miller@email.com', '303 Birch St', 'France', 3),
('David', 'Wilson', 'Male', '2003-10-12', '777-666-5555', 'david.wilson@email.com', '404 Maple St', 'Italy', 1),
('Sophia', 'Moore', 'Female', '1999-12-11', '333-444-5555', 'sophia.moore@email.com', '505 Pine St', 'Spain', 2),
('Daniel', 'Taylor', 'Male', '2000-09-08', '222-333-4444', 'daniel.taylor@email.com', '606 Oak St', 'Japan', 3),
('Olivia', 'Anderson', 'Female', '2003-12-11', '666-777-8888', 'olivia.anderson@email.com', '707 Elm St', 'China', 1),
('William', 'Clark', 'Male', '2003-11-27', '444-333-2222', 'william.clark@email.com', '808 Cedar St', 'Brazil', 2),
('Ava', 'Hill', 'Female', '2000-08-23', '888-999-0000', 'ava.hill@email.com', '909 Maple St', 'Mexico', 3),
('James', 'Wright', 'Male', '2002-12-11', '123-987-4560', 'james.wright@email.com', '111 Pine St', 'India', 1),
('Emma', 'Turner', 'Female', '2003-11-22', '321-654-0987', 'emma.turner@email.com', '222 Oak St', 'South Africa', 2),
('Benjamin', 'Garcia', 'Male', '2004-06-15', '555-777-9999', 'benjamin.garcia@email.com', '333 Elm St', 'Russia', 3),
('Mia', 'Fisher', 'Female', '2000-07-11', '111-222-3333', 'mia.fisher@email.com', '444 Cedar St', 'Argentina', 1),
('Alexander', 'Hernandez', 'Male', '2003-08-21', '999-888-7777', 'alexander.hernandez@email.com', '555 Maple St', 'Chile', 2),
('Sofia', 'Martinez', 'Female', '2004-09-21', '777-666-5555', 'sofia.martinez@email.com', '666 Pine St', 'Colombia', 3),
('Ethan', 'Lopez', 'Male', '2002-11-30', '333-444-5555', 'ethan.lopez@email.com', '777 Oak St', 'Peru', 1),
('Amelia', 'Rodriguez', 'Female', '2002-12-16', '222-333-4444', 'amelia.rodriguez@email.com', '888 Elm St', 'Venezuela', 2);

-- Tạo dữ liệu cho bảng Account
INSERT INTO `Account` (`username`, `password`, `role`) VALUES 
('admin@gmail.com', '$2a$10$b4fPVlmra8fqY6VqMnsL6OWsIZtt/i5XGYIMwkS2Fn0gB4gYSqr8G', 'ADMIN');
INSERT INTO `Account` (`username`, `password`, `role`)
SELECT `email`, '$2a$10$b4fPVlmra8fqY6VqMnsL6OWsIZtt/i5XGYIMwkS2Fn0gB4gYSqr8G', 'USER' FROM `Passenger`;
-- password: 123456

-- Tạo dữ liệu cho bảng Flight
DELIMITER //
CREATE PROCEDURE GenerateFlightData()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE random_value INT;

  SELECT COUNT(*) INTO @num_airports FROM Airport;

  WHILE i < @num_airports DO
    -- Sinh số ngẫu nhiên từ 1 đến 10
    SET random_value = FLOOR(1 + RAND() * 10);

    INSERT INTO `Flight` (`departure_airport_id`, `arrival_airport_id`, `status`, `flight_airline`, `flight_price`) VALUES
    (i, i + 1, 'Active', 'Vietjet Air', 150 + random_value * 10),
    (i + 1, i, 'Active', 'Vietnam Airlines', 150 + random_value * 10);

    SET i = i + 1;
  END WHILE;
END //
DELIMITER ;

-- Gọi thủ tục để thực hiện lặp
CALL GenerateFlightData();

DELIMITER //
CREATE PROCEDURE GenerateFlightDataRepeat()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE random_value INT;

  SELECT COUNT(*) INTO @num_airports FROM Airport;

  WHILE i < @num_airports DO
    -- Sinh số ngẫu nhiên từ 1 đến 10
    SET random_value = FLOOR(1 + RAND() * 10);

    INSERT INTO `Flight` (`departure_airport_id`, `arrival_airport_id`, `status`, `flight_airline`, `flight_price`) VALUES
    (i, i + 1, 'Active', 'Vietnam Airlines', 150 + random_value * 10),
    (i + 1, i, 'Active', 'Vietjet Air', 150 + random_value * 10);

    SET i = i + 1;
  END WHILE;
END //
DELIMITER ;

-- Gọi thủ tục để thực hiện lặp
CALL GenerateFlightDataRepeat();

-- Tạo dữ liệu cho bảng Flight_Plane
-- DELIMITER //

-- CREATE PROCEDURE InsertFlightPlaneCombination()
-- BEGIN
--   DECLARE flight_id_var INT DEFAULT 1;
--   DECLARE plane_id_var INT;

--   SELECT COUNT(*) INTO @num_flights FROM Flight;

--   -- Bắt đầu vòng lặp cho mỗi Flight
--   WHILE flight_id_var <= @num_flights DO
  
--     SELECT COUNT(*) INTO @num_planes FROM Plane;
--     SET plane_id_var = 1;

--     -- Bắt đầu vòng lặp cho mỗi Plane
--     WHILE plane_id_var <= @num_planes DO
--       SET @current_date = CURRENT_DATE;
      
--       -- Random departure day between current date and current date + 3 days
--       SET @departure_day = @current_date + INTERVAL FLOOR(RAND() * 3) DAY;
--       -- Random arrival day between departure day and departure day + 2 days
--       SET @arrival_day = @departure_day + INTERVAL FLOOR(RAND() * 2) DAY;

--       SET @departure_time_sec = FLOOR(RAND() * 86400);
--       SET @arrival_time_sec = FLOOR(RAND() * 86400);

--       -- Kiểm tra điều kiện departure_time phải nhỏ hơn arrival_time
--       WHILE @departure_time_sec >= @arrival_time_sec DO
--         SET @departure_time_sec = FLOOR(RAND() * 86400);
--         SET @arrival_time_sec = FLOOR(RAND() * 86400);
--       END WHILE;

--       -- Insert dữ liệu vào bảng Flight_Plane
--       INSERT INTO `Flight_Plane` (`flight_id`, `plane_id`, `departure_time`, `arrival_time`, `departure_day`, `arrival_day`)
--       VALUES (flight_id_var, plane_id_var, SEC_TO_TIME(@departure_time_sec), SEC_TO_TIME(@arrival_time_sec), @departure_day, @arrival_day);

--       SET plane_id_var = plane_id_var + 1;
--     END WHILE;

--     SET flight_id_var = flight_id_var + 1;
--   END WHILE;
-- END //

-- DELIMITER ;

-- DELIMITER //

-- CREATE PROCEDURE InsertFlightPlaneCombination()
-- BEGIN
--   DECLARE flight_id_var INT DEFAULT 1;
--   DECLARE plane_id_var INT;

--   SELECT COUNT(*) INTO @num_flights FROM Flight;
--   SELECT COUNT(*) INTO @num_planes FROM Plane;

--   -- Bắt đầu vòng lặp cho mỗi Flight
--   WHILE flight_id_var <= @num_flights DO

--     SET plane_id_var = ((flight_id_var - 1) * 10 % @num_planes) + 1;

--     -- Bắt đầu vòng lặp cho mỗi Plane (chỉ lấy 10 plane_id)
--     WHILE plane_id_var <= @num_planes AND plane_id_var <= (flight_id_var * 10) % @num_planes + 10 DO
--       SET @current_date = CURRENT_DATE;

--       -- Random departure day between current date and current date + 3 days
--       SET @departure_day = @current_date + INTERVAL FLOOR(RAND() * 3) DAY;
--       -- Random arrival day between departure day and departure day + 2 days
--       SET @arrival_day = @departure_day + INTERVAL FLOOR(RAND() * 2) DAY;

--       SET @departure_time_sec = FLOOR(RAND() * 86400);
--       SET @arrival_time_sec = FLOOR(RAND() * 86400);

--       -- Kiểm tra điều kiện departure_time phải nhỏ hơn arrival_time
--       WHILE @departure_time_sec >= @arrival_time_sec DO
--         SET @departure_time_sec = FLOOR(RAND() * 86400);
--         SET @arrival_time_sec = FLOOR(RAND() * 86400);
--       END WHILE;

--       -- Insert dữ liệu vào bảng Flight_Plane
--       INSERT INTO `Flight_Plane` (`flight_id`, `plane_id`, `departure_time`, `arrival_time`, `departure_day`, `arrival_day`)
--       VALUES (flight_id_var, plane_id_var, SEC_TO_TIME(@departure_time_sec), SEC_TO_TIME(@arrival_time_sec), @departure_day, @arrival_day);

--       SET plane_id_var = plane_id_var + 1;
--     END WHILE;

--     SET flight_id_var = flight_id_var + 1;
--   END WHILE;
-- END //

-- DELIMITER ;

DELIMITER //

CREATE PROCEDURE InsertFlightPlaneCombination()
BEGIN
  DECLARE flight_id_var INT DEFAULT 1;
  DECLARE plane_id_var INT;

  SELECT COUNT(*) INTO @num_flights FROM Flight;
  SELECT COUNT(*) INTO @num_planes FROM Plane;

  -- Bắt đầu vòng lặp cho mỗi Flight
  WHILE flight_id_var <= @num_flights DO

    SET plane_id_var = ((flight_id_var - 1) * 10 % @num_planes) + 1;

    -- Bắt đầu vòng lặp cho mỗi Plane (chỉ lấy 10 plane_id)
    WHILE plane_id_var <= @num_planes AND plane_id_var <= ((flight_id_var - 1) * 10 % @num_planes) + 10 DO
      SET @current_date = CURRENT_DATE;

      -- Random departure day between current date and current date + 2 days
      SET @departure_day = @current_date + INTERVAL FLOOR(RAND() * 2) DAY;
      -- Random arrival day between departure day and departure day + 2 days
      SET @arrival_day = @departure_day + INTERVAL FLOOR(RAND() * 2) DAY;

      SET @departure_time_sec = FLOOR(RAND() * 86400);
      SET @arrival_time_sec = FLOOR(RAND() * 86400);

      -- Kiểm tra điều kiện departure_time phải nhỏ hơn arrival_time
      WHILE @departure_time_sec >= @arrival_time_sec DO
        SET @departure_time_sec = FLOOR(RAND() * 86400);
        SET @arrival_time_sec = FLOOR(RAND() * 86400);
      END WHILE;

      -- Insert dữ liệu vào bảng Flight_Plane
      INSERT INTO `Flight_Plane` (`flight_id`, `plane_id`, `departure_time`, `arrival_time`, `departure_day`, `arrival_day`)
      VALUES (flight_id_var, plane_id_var, SEC_TO_TIME(@departure_time_sec), SEC_TO_TIME(@arrival_time_sec), @departure_day, @arrival_day);

      SET plane_id_var = plane_id_var + 1;
    END WHILE;

    SET flight_id_var = flight_id_var + 1;
  END WHILE;
END //

DELIMITER ;

CALL InsertFlightPlaneCombination();

-- Tạo dữ liệu cho bảng Seat_Detail
-- DELIMITER //

-- CREATE PROCEDURE InsertSeatDetailData()
-- BEGIN
--   DECLARE flight_plane_id_var INT;
--   DECLARE seat_id_var INT;
--   DECLARE done INT DEFAULT FALSE;

--   -- Lấy danh sách các flight_plane
--   DECLARE flight_plane_cursor CURSOR FOR SELECT `id` FROM `Flight_Plane`;
--   DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

--   OPEN flight_plane_cursor;

--   read_loop: LOOP
--     FETCH flight_plane_cursor INTO flight_plane_id_var;
--     IF done THEN
--       LEAVE read_loop;
--     END IF;

--     -- Lấy số lượng seat hiện có
--     SET @num_seats = (SELECT COUNT(*) FROM `Seat`);

--     -- Thêm dữ liệu vào bảng Seat_Detail
--     INSERT INTO `SeatDetail` (`is_taken`, `flight_plane_id`, `seat_id`)
--     VALUES (0, flight_plane_id_var, FLOOR(1 + RAND() * @num_seats));
--   END LOOP;

--   CLOSE flight_plane_cursor;
-- END //

-- DELIMITER ;

-- DELIMITER //

-- CREATE PROCEDURE InsertSeatDetailData()
-- BEGIN
--   DECLARE flight_plane_id_var INT;
--   DECLARE plane_id_var INT;
--   DECLARE capacity_var INT;
--   DECLARE i INT;

--   -- Lấy danh sách các flight_plane
--   DECLARE flight_plane_cursor CURSOR FOR SELECT `id`, `plane_id` FROM `Flight_Plane`;

--   OPEN flight_plane_cursor;

--   read_loop: LOOP
--     FETCH flight_plane_cursor INTO flight_plane_id_var, plane_id_var;
--     IF flight_plane_id_var IS NULL THEN
--       LEAVE read_loop;
--     END IF;

--     -- Lấy số lượng seat của plane tương ứng
--     SET capacity_var = (SELECT `capacity` FROM `Plane` WHERE `plane_id` = plane_id_var);

--     -- Thêm dữ liệu vào bảng Seat_Detail
--     SET i = 1;
--     WHILE i <= capacity_var DO
--       INSERT INTO `Seat_Detail` (`is_taken`, `flight_plane_id`, `seat_id`)
--       VALUES (0, flight_plane_id_var, i);
--       SET i = i + 1;
--     END WHILE;

--   END LOOP;

--   CLOSE flight_plane_cursor;
-- END //

-- DELIMITER ;
INSERT INTO Seat_Detail (is_taken, flight_plane_id, seat_id)
SELECT 0, fp.id, s.seat_id
FROM Flight_Plane fp
JOIN Seat s ON s.plane_id = fp.plane_id;

-- CALL InsertSeatDetailData();