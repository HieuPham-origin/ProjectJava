drop database IF exists Booking_Flight_Service;
CREATE DATABASE Booking_Flight_Service;
USE Booking_Flight_Service;
CREATE TABLE `Promotion`(
    `promotion_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `promotion_name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `discount` INT NOT NULL,
    `code` VARCHAR(255) NOT NULL,
    `url_image` VARCHAR(255) NOT NULL
);

CREATE TABLE `Ticket_class`(
    `class_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `class_name` VARCHAR(255) NOT NULL,
    `price` INT NOT NULL
);
CREATE TABLE `Airport`(
    `airport_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `airport_name` INT NOT NULL,
    `city` INT NOT NULL,
    `country` INT NOT NULL,
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
CREATE TABLE `Seat`(
    `seat_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `seat_status` VARCHAR(255) NOT NULL,
    `plane_id` INT NOT NULL,
    FOREIGN KEY(`plane_id`) REFERENCES `Plane`(`plane_id`)
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
CREATE TABLE `Ticket`(
    `ticket_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `seat_id` INT NOT NULL,
    `class_id` INT NOT NULL,
    `total_price` INT NOT NULL,
    `tax` INT NOT NULL,
    `service_id` INT NOT NULL,
    `baggage_id` INT NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `payment_id` INT NOT NULL,
    `day_order` DATETIME NOT NULL,
    `day_pay` DATETIME NOT NULL,
    FOREIGN KEY(`baggage_id`) REFERENCES `Baggage`(`baggage_id`),
    FOREIGN KEY(`service_id`) REFERENCES `Service`(`service_id`),
    FOREIGN KEY(`payment_id`) REFERENCES `Payment_Format`(`payment_id`),
    FOREIGN KEY(`class_id`) REFERENCES `Ticket_class`(`class_id`),
    FOREIGN KEY(`seat_id`) REFERENCES `Seat`(`seat_id`)
);
CREATE TABLE `Ticket_Detail`(
    `type_id` INT NOT NULL,
    `ticket_id` INT NOT NULL,
    `price` INT NOT NULL,
    PRIMARY KEY (`type_id`, `ticket_id`),
    
    FOREIGN KEY(`type_id`) REFERENCES `Customer_Type`(`type_id`),
    
    FOREIGN KEY(`ticket_id`) REFERENCES `Ticket`(`ticket_id`)

);
CREATE TABLE `Flight`(
    `flight_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `departure_airport_id` INT NOT NULL,
    `arrival_airport_id` INT NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `price` DOUBLE NOT NULL,
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
    `flight_type` INT NOT NULL,
    FOREIGN KEY(`flight_id`) REFERENCES `Flight`(`flight_id`),
    FOREIGN KEY(`plane_id`) REFERENCES `Plane`(`plane_id`)
);
INSERT INTO `Customer_type` (`type_name`) VALUES
('adult'),
('child'),
('infant');
