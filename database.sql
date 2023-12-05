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

CREATE TABLE `Payment_Format`(
    `payment_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `payment_name` VARCHAR(255) NOT NULL
);


CREATE TABLE `Account`(
    `account_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(255) NOT NULL
);

CREATE TABLE `Reservation`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `code` VARCHAR(255) NOT NULL,
    `time_created` VARCHAR(255) NOT NULL,
    `account_id` INT NOT NULL,
    FOREIGN KEY(`account_id`) REFERENCES `Account`(`account_id`)
);

CREATE TABLE `Plane`(
    `plane_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `plane_name` VARCHAR(255) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `capacity` INT NOT NULL
);

-- **
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

-- ***
CREATE TABLE `Seat`(
    `seat_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `seat_number` VARCHAR(255) NOT NULL,
    `plane_id` INT NOT NULL,
    FOREIGN KEY(`plane_id`) REFERENCES `Plane`(`plane_id`)
);

CREATE TABLE `SeatDetail`(
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
    `phone_number` VARCHAR(255),
    `email` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255),
    `country` VARCHAR(255),
    `type` INT,
    FOREIGN KEY (`type`) REFERENCES `Customer_type` (`type_id`)
);

--  **
CREATE TABLE `Ticket`(
    `ticket_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `seat_detail_id` INT NOT NULL,
    `class_id` INT NOT NULL,
    `total_price` INT NOT NULL,
    `tax` INT NOT NULL,
    `service_id` INT NOT NULL,
    `baggage_id` INT NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `payment_id` INT NOT NULL,
    `reservation_id` INT NOT NULL,
    `day_order` DATETIME NOT NULL,
    `day_pay` DATETIME NOT NULL,
    FOREIGN KEY(`baggage_id`) REFERENCES `Baggage`(`baggage_id`),
    FOREIGN KEY(`service_id`) REFERENCES `Service`(`service_id`),
    FOREIGN KEY(`payment_id`) REFERENCES `Payment_Format`(`payment_id`),
    FOREIGN KEY(`reservation_id`) REFERENCES `Reservation`(`id`),
    FOREIGN KEY(`class_id`) REFERENCES `Ticket_class`(`class_id`),
    FOREIGN KEY(`seat_detail_id`) REFERENCES `SeatDetail`(`id`)
);

CREATE TABLE `Ticket_Detail`(
    `type_id` INT NOT NULL,
    `ticket_id` INT NOT NULL,
    `price` INT NOT NULL,
    PRIMARY KEY (`type_id`, `ticket_id`),
    FOREIGN KEY(`type_id`) REFERENCES `Customer_Type`(`type_id`),
    FOREIGN KEY(`ticket_id`) REFERENCES `Ticket`(`ticket_id`)
);

INSERT INTO `Customer_type` (`type_name`) VALUES
('adult'),
('child'),
('infant');


INSERT INTO Ticket_class (class_name, rate, cabin_baggage, baggage)
VALUES
    ('Economy', 1, 20, 7),
    ('Business', 2, 25, 9),
    ('First Class', 3, 30, 10);


INSERT INTO `Airport` (`airport_name`, `city`, `country`, `status`, `airport_code`) VALUES
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
('O''Hare International Airport', 'Chicago', 'United States', 'Active', 'ORD'),
('Adolfo Suárez Madrid–Barajas Airport', 'Madrid', 'Spain', 'Active', 'MAD'),
('Hong Kong International Airport', 'Hong Kong', 'China', 'Active', 'HKG'),
('Leonardo da Vinci–Fiumicino Airport', 'Rome', 'Italy', 'Active', 'FCO'),
('Denver International Airport', 'Denver', 'United States', 'Active', 'DEN'),
('Tokyo Haneda Airport', 'Tokyo', 'Japan', 'Active', 'HND'),
('Amsterdam Airport Schiphol', 'Amsterdam', 'Netherlands', 'Active', 'AMS'),
('Toronto Pearson International Airport', 'Toronto', 'Canada', 'Active', 'YYZ'),
('San Francisco International Airport', 'San Francisco', 'United States', 'Active', 'SFO'),
('Munich Airport', 'Munich', 'Germany', 'Active', 'MUC'),
('Indira Gandhi International Airport', 'Delhi', 'India', 'Active', 'DEL'),
('Dallas/Fort Worth International Airport', 'Dallas', 'United States', 'Active', 'DFW'),
('Guangzhou Baiyun International Airport', 'Guangzhou', 'China', 'Active', 'CAN'),
('Seattle-Tacoma International Airport', 'Seattle', 'United States', 'Active', 'SEA'),
('Kuala Lumpur International Airport', 'Kuala Lumpur', 'Malaysia', 'Active', 'KUL'),
('Hartsfield-Jackson Atlanta International Airport', 'Atlanta', 'United States', 'Active', 'ATL'),
('Zurich Airport', 'Zurich', 'Switzerland', 'Active', 'ZRH'),
('Barcelona–El Prat Airport', 'Barcelona', 'Spain', 'Active', 'BCN'),
('San Diego International Airport', 'San Diego', 'United States', 'Active', 'SAN'),
('Chhatrapati Shivaji Maharaj International Airport', 'Mumbai', 'India', 'Active', 'BOM'),
('Minneapolis-Saint Paul International Airport', 'Minneapolis', 'United States', 'Active', 'MSP'),
('Dublin Airport', 'Dublin', 'Ireland', 'Active', 'DUB'),
('Ben Gurion Airport', 'Tel Aviv', 'Israel', 'Active', 'TLV'),
('Charlotte Douglas International Airport', 'Charlotte', 'United States', 'Active', 'CLT'),
('Vienna International Airport', 'Vienna', 'Austria', 'Active', 'VIE'),
('Istanbul Airport', 'Istanbul', 'Turkey', 'Active', 'IST'),
('Phoenix Sky Harbor International Airport', 'Phoenix', 'United States', 'Active', 'PHX'),
('Brussels Airport', 'Brussels', 'Belgium', 'Active', 'BRU'),
('McCarran International Airport', 'Las Vegas', 'United States', 'Active', 'LAS'),
('Miami International Airport', 'Miami', 'United States', 'Active', 'MIA'),
('Rajiv Gandhi International Airport', 'Hyderabad', 'India', 'Active', 'HYD'),
('Detroit Metropolitan Airport', 'Detroit', 'United States', 'Active', 'DTW'),
('Auckland Airport', 'Auckland', 'New Zealand', 'Active', 'AKL'),
('Fiumicino Airport', 'Rome', 'Italy', 'Active', 'FCO'),
('Osaka Kansai International Airport', 'Osaka', 'Japan', 'Active', 'KIX'),
('Perth Airport', 'Perth', 'Australia', 'Active', 'PER'),
('Düsseldorf Airport', 'Düsseldorf', 'Germany', 'Active', 'DUS'),
('Helsinki-Vantaa Airport', 'Helsinki', 'Finland', 'Active', 'HEL'),
('Cairo International Airport', 'Cairo', 'Egypt', 'Active', 'CAI'),
('Jorge Chávez International Airport', 'Lima', 'Peru', 'Active', 'LIM'),
('Tocumen International Airport', 'Panama City', 'Panama', 'Active', 'PTY'),
('Eleftherios Venizelos International Airport', 'Athens', 'Greece', 'Active', 'ATH'),
('Abu Dhabi International Airport', 'Abu Dhabi', 'United Arab Emirates', 'Active', 'AUH'),
('Sheremetyevo International Airport', 'Moscow', 'Russia', 'Active', 'SVO'),
('Suvarnabhumi Airport', 'Bangkok', 'Thailand', 'Active', 'BKK'),
('Kempegowda International Airport', 'Bangalore', 'India', 'Active', 'BLR'),
('Copenhagen Airport', 'Copenhagen', 'Denmark', 'Active', 'CPH'),
('Ninoy Aquino International Airport', 'Manila', 'Philippines', 'Active', 'MNL'),
('Jomo Kenyatta International Airport', 'Nairobi', 'Kenya', 'Active', 'NBO'),
('Kingsford Smith Airport', 'Sydney', 'Australia', 'Active', 'SYD'),
('Marrakesh Menara Airport', 'Marrakesh', 'Morocco', 'Active', 'RAK'),
('Brisbane Airport', 'Brisbane', 'Australia', 'Active', 'BNE'),
('Changi Airport', 'Singapore', 'Singapore', 'Active', 'SIN'),
('Malpensa Airport', 'Milan', 'Italy', 'Active', 'MXP'),
('Vienna International Airport', 'Vienna', 'Austria', 'Active', 'VIE'),
('Sheremetyevo International Airport', 'Moscow', 'Russia', 'Active', 'SVO'),
('O. R. Tambo International Airport', 'Johannesburg', 'South Africa', 'Active', 'JNB'),
('Doha Hamad International Airport', 'Doha', 'Qatar', 'Active', 'DHIA');


INSERT INTO `Payment_Format` (`payment_name`) VALUES
('Credit Card'),
('PayPal'),
('Collect on Delivery');


INSERT INTO `Plane` (`plane_name`, `status`, `capacity`) VALUES
('Boeing 737', 'Active', 150),
('Airbus A320', 'Active', 140),
('Boeing 747', 'Active', 300),
('Airbus A380', 'Active', 500),
('Embraer E190', 'Active', 100),
('Bombardier CRJ-900', 'Active', 100),
('Boeing 777', 'Active', 350),
('Airbus A330', 'Active', 250),
('Boeing 757', 'Active', 200),
('Airbus A350', 'Active', 300),
('Boeing 767', 'Active', 220),
('Embraer E145', 'Active', 150),
('Bombardier Q400', 'Active', 170),
('Boeing 787', 'Active', 270),
('Airbus A319', 'Active', 120),
('Boeing 717', 'Active', 100),
('Airbus A321', 'Active', 200),
('McDonnell Douglas MD-80', 'Active', 160),
('Embraer E170', 'Active', 180),
('Bombardier CRJ-200', 'Active', 160),
('Boeing 737 MAX', 'Active', 170),
('Airbus A330neo', 'Active', 280),
('Boeing 757-300', 'Active', 230),
('Airbus A321XLR', 'Active', 220),
('Boeing 767-400ER', 'Active', 240),
('Embraer E195', 'Active', 110),
('Bombardier CRJ-1000', 'Active', 120),
('Boeing 777X', 'Active', 400),
('Airbus A330-800neo', 'Active', 230),
('Boeing 737-800', 'Active', 160),
('Airbus A220', 'Active', 130),
('Boeing 737-900ER', 'Active', 180),
('Airbus A321neo', 'Active', 240),
('Boeing 757-200', 'Active', 210),
('Embraer E175', 'Active', 170),
('Bombardier CRJ-700', 'Active', 180),
('Boeing 737-700', 'Active', 140),
('Airbus A318', 'Active', 100),
('Boeing 767-300ER', 'Active', 250),
('Airbus A340', 'Active', 300),
('Boeing 737-600', 'Active', 120),
('Airbus A310', 'Active', 180),
('Boeing 757-100', 'Active', 150),
('Embraer E135', 'Active', 140),
('Bombardier CRJ-500', 'Active', 150),
('Boeing 747-8', 'Active', 250),
('Airbus A330-200', 'Active', 220),
('Boeing 737-700ER', 'Active', 150),
('Airbus A321LR', 'Active', 200);


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
('Special Baggage - Skis and Poles', 15, 30),
('Special Baggage - Kayak', 40, 60),
('Special Baggage - Archery Equipment', 10, 25),
('Special Baggage - Bike Box', 35, 50),
('Special Baggage - Scuba Diving Tank', 18, 35),
('Special Baggage - Artwork', 10, 25),
('Special Baggage - Golf Clubs', 15, 30),
('Special Baggage - Motorcycle Helmet', 3, 15),
('Special Baggage - Paraglider', 25, 40),
('Special Baggage - Kiteboard Bag', 20, 35),
('Special Baggage - Electric Scooter', 15, 30),
('Special Baggage - Antiques', 10, 25),
('Special Baggage - Game Console', 5, 15),
('Special Baggage - Telescope', 12, 30),
('Special Baggage - Skateboard', 7, 20),
('Special Baggage - Drone', 4, 15),
('Special Baggage - Picnic Set', 10, 20),
('Special Baggage - Fishing Rod', 5, 15),
('Special Baggage - Beach Gear', 15, 30);


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
('Private Jet Charter', 200, 'Travel in ultimate luxury with a private jet charter service'),
('Celebratory Package', 60, 'Celebrate special occasions with a customized in-flight package'),
('Catering Upgrade', 35, 'Upgrade your in-flight dining experience with premium catering'),
('Meeting Room Reservation', 20, 'Book a meeting room for business discussions at the airport'),
('Airport Yoga Studio Access', 15, 'Relax and rejuvenate with access to an airport yoga studio'),
('Personal Shopper Service', 30, 'Receive assistance from a personal shopper for duty-free shopping'),
('Bag Wrapping Service', 8, 'Secure your luggage with professional bag wrapping services'),
('Customs Assistance', 12, 'Receive guidance and assistance through the customs process'),
('Currency Exchange Assistance', 15, 'Get assistance with currency exchange at the airport'),
('Mobile Boarding Pass', 5, 'Go paperless with a mobile boarding pass on your smartphone'),
('Carry-On Essentials Kit', 10, 'Stay refreshed and comfortable with a carry-on essentials kit');


INSERT INTO `Passenger` (`first_name`, `last_name`, `gender`, `phone_number`, `email`, `address`, `country`, `type`)
VALUES
('John', 'Doe', 'Male', '123-456-7890', 'john.doe@email.com', '123 Main St', 'USA', 1),
('Jane', 'Smith', 'Female', '987-654-3210', 'jane.smith@email.com', '456 Oak St', 'Canada', 2),
('Mike', 'Johnson', 'Male', '555-123-4567', 'mike.johnson@email.com', '789 Elm St', 'UK', 3),
('Emily', 'Davis', 'Female', '111-222-3333', 'emily.davis@email.com', '101 Pine St', 'Australia', 1),
('Robert', 'Brown', 'Male', '999-888-7777', 'robert.brown@email.com', '202 Cedar St', 'Germany', 2),
('Linda', 'Miller', 'Female', '444-555-6666', 'linda.miller@email.com', '303 Birch St', 'France', 3),
('David', 'Wilson', 'Male', '777-666-5555', 'david.wilson@email.com', '404 Maple St', 'Italy', 1),
('Sophia', 'Moore', 'Female', '333-444-5555', 'sophia.moore@email.com', '505 Pine St', 'Spain', 2),
('Daniel', 'Taylor', 'Male', '222-333-4444', 'daniel.taylor@email.com', '606 Oak St', 'Japan', 3),
('Olivia', 'Anderson', 'Female', '666-777-8888', 'olivia.anderson@email.com', '707 Elm St', 'China', 1),
('William', 'Clark', 'Male', '444-333-2222', 'william.clark@email.com', '808 Cedar St', 'Brazil', 2),
('Ava', 'Hill', 'Female', '888-999-0000', 'ava.hill@email.com', '909 Maple St', 'Mexico', 3),
('James', 'Wright', 'Male', '123-987-4560', 'james.wright@email.com', '111 Pine St', 'India', 1),
('Emma', 'Turner', 'Female', '321-654-0987', 'emma.turner@email.com', '222 Oak St', 'South Africa', 2),
('Benjamin', 'Garcia', 'Male', '555-777-9999', 'benjamin.garcia@email.com', '333 Elm St', 'Russia', 3),
('Mia', 'Fisher', 'Female', '111-222-3333', 'mia.fisher@email.com', '444 Cedar St', 'Argentina', 1),
('Alexander', 'Hernandez', 'Male', '999-888-7777', 'alexander.hernandez@email.com', '555 Maple St', 'Chile', 2),
('Sofia', 'Martinez', 'Female', '777-666-5555', 'sofia.martinez@email.com', '666 Pine St', 'Colombia', 3),
('Ethan', 'Lopez', 'Male', '333-444-5555', 'ethan.lopez@email.com', '777 Oak St', 'Peru', 1),
('Amelia', 'Rodriguez', 'Female', '222-333-4444', 'amelia.rodriguez@email.com', '888 Elm St', 'Venezuela', 2);


INSERT INTO `Account` (`username`, `password`, `role`) VALUES ('admin@gmail.com', '$2a$10$b4fPVlmra8fqY6VqMnsL6OWsIZtt/i5XGYIMwkS2Fn0gB4gYSqr8G', 'ADMIN');
-- Tạo dữ liệu cho bảng Account từ bảng Passenger
INSERT INTO `Account` (`username`, `password`, `role`)
SELECT `email`, '$2a$10$b4fPVlmra8fqY6VqMnsL6OWsIZtt/i5XGYIMwkS2Fn0gB4gYSqr8G', 'USER' FROM `Passenger`;


INSERT INTO `Flight` (`departure_airport_id`, `arrival_airport_id`, `status`, `flight_airline`, `flight_price`) VALUES
(1, 2, 'Active', 'Vietjet Air', 150),
(2, 3, 'Active', 'Vietnam AirLines', 150),
(3, 4, 'Active', 'Vietjet Air', 150),
(4, 5, 'Active', 'Vietjet Air', 150),
(5, 1, 'Active', 'Vietnam AirLines', 150),
(1, 3, 'Active', 'Vietjet Air', 150),
(2, 4, 'Active', 'Vietnam AirLines', 150),
(3, 5, 'Active', 'Vietjet Air', 150),
(4, 1, 'Active', 'Vietnam AirLines', 150),
(5, 2, 'Active', 'Vietjet Air', 150),
(1, 4, 'Active', 'Vietnam AirLines', 150),
(2, 5, 'Active', 'Vietjet Air', 150),
(3, 1, 'Active', 'Vietnam AirLines', 150),
(4, 2, 'Active', 'Vietjet Air', 150),
(5, 3, 'Active', 'Vietnam AirLines', 150),
(1, 5, 'Active', 'Vietjet Air', 150),
(2, 1, 'Active', 'Vietnam AirLines', 150),
(3, 2, 'Active', 'Vietjet Air', 150),
(4, 3, 'Active', 'Vietnam AirLines', 150),
(5, 4, 'Active', 'Vietjet Air', 150),
(1, 3, 'Active', 'Vietnam AirLines', 150),
(2, 4, 'Active', 'Vietjet Air', 150),
(3, 5, 'Active', 'Vietnam AirLines', 150),
(4, 1, 'Active', 'Vietjet Air', 150),
(5, 2, 'Active', 'Vietnam AirLines', 150),
(1, 4, 'Active', 'Vietjet Air', 150);


-- Đặt biến cho số lượng dòng dữ liệu muốn thêm
SET @num_rows = 50;

-- Sử dụng vòng lặp để thêm dữ liệu ngẫu nhiên
DELIMITER //
CREATE PROCEDURE InsertRandomFlightPlane()
BEGIN
  DECLARE i INT DEFAULT 1;

  WHILE i <= @num_rows DO
    SET @departure_day = '2023-01-01' + INTERVAL FLOOR(RAND() * 200) DAY;
    SET @arrival_day = '2023-01-01' + INTERVAL FLOOR(RAND() * 200) DAY;

    -- Kiểm tra điều kiện departure_day phải nhỏ hơn arrival_day
    WHILE @departure_day >= @arrival_day DO
      SET @departure_day = '2023-01-01' + INTERVAL FLOOR(RAND() * 20) DAY;
      SET @arrival_day = '2023-01-01' + INTERVAL FLOOR(RAND() * 20) DAY;
    END WHILE;

    SET @departure_time_sec = FLOOR(RAND() * 86400);
    SET @arrival_time_sec = FLOOR(RAND() * 86400);

    -- Kiểm tra điều kiện departure_time phải nhỏ hơn arrival_time
    WHILE @departure_time_sec >= @arrival_time_sec DO
      SET @departure_time_sec = FLOOR(RAND() * 86400);
      SET @arrival_time_sec = FLOOR(RAND() * 86400);
    END WHILE;

    INSERT INTO `Flight_Plane` (`flight_id`, `plane_id`, `departure_time`, `arrival_time`, `departure_day`, `arrival_day`)
    SELECT
      FLOOR(1 + RAND() * 20),
      FLOOR(1 + RAND() * 40),
      SEC_TO_TIME(@departure_time_sec),
      SEC_TO_TIME(@arrival_time_sec),
      @departure_day,
      @arrival_day;

    SET i = i + 1;
  END WHILE;
END //
DELIMITER ;

-- Gọi procedure để thực hiện thêm dữ liệu
CALL InsertRandomFlightPlane();

