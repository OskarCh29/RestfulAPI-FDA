CREATE TABLE `drug_record`(
`application_number` VARCHAR(50) PRIMARY KEY NOT NULL,
`manufacturer_name` VARCHAR(50) NOT NULL,
`substance_name` VARCHAR(50) NOT NULL);

CREATE TABLE `product_number`(
`id` INT AUTO_INCREMENT PRIMARY KEY,
`product_number` VARCHAR(50) NOT NULL,
`drug_record_application_number` VARCHAR(50) NOT NULL,
FOREIGN KEY(`drug_record_application_number`) REFERENCES drug_record(`application_number``) ON DELETE CASCADE);