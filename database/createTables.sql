CREATE USER 'yury'@'localhost' IDENTIFIED BY  'yury';
GRANT ALL PRIVILEGES ON *.* TO 'yury'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE `yury_savchuk`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

use `yury_savchuk`;
CREATE TABLE `address`(
	`id_address` int unsigned NOT NULL AUTO_INCREMENT,
    `country` nvarchar(30),
    `city` nvarchar(30),
    `street` nvarchar(30),
    `house` nvarchar(5),
    `flat` int ,
    `ind` int ,
     PRIMARY KEY(`id_address`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


use `yury_savchuk`;
CREATE TABLE `contacts`(
	`id_contact` int unsigned NOT NULL AUTO_INCREMENT,
	`name` nvarchar(30) NOT NULL,
    `surname` nvarchar(30) NOT NULL,
    `midleName` nvarchar(30) ,
    `birthday` date NOT NULL,
    `sex` nvarchar(6) NOT NULL,
    `nationality` nvarchar(20),
    `maritStat` nvarchar(20) ,
    `webSite` nvarchar(30) ,
    `email` nvarchar(30),
    `curWorkplace` nvarchar(30) ,
    `id_address` int unsigned NOT NULL,
    `imgPath` nvarchar(100),
    FOREIGN KEY (`id_address`)
    REFERENCES `yury_savchuk`.`address`(`id_address`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
	PRIMARY KEY(`id_contact`)
    
)ENGINE=InnoDB ;


use `yury_savchuk`;
CREATE TABLE `phoneNumbers`(
	`id_number` int unsigned NOT NULL AUTO_INCREMENT,
	`countryCode` int NOT NULL,
    `operCode` int NOT NULL,
    `number` int NOT NULL,
    `type` nvarchar(30) NOT NULL,
    `comment` nvarchar(50) NOT NULL,
    `id_contact` int unsigned NOT NULL,
    FOREIGN KEY(`id_contact`) 
    REFERENCES `yury_savchuk`.`contacts`(`id_contact`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
    PRIMARY KEY (`id_number`)
)ENGINE=InnoDB;


use `yury_savchuk`;
CREATE TABLE `attachFiles`(
	`id_file` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `fileName` nvarchar(30) NOT NULL,
    `path` nvarchar(200) NOT NULL,
    `dataLoad` date NOT NULL,
    `comment` nvarchar(50) NOT NULL,
    `id_contact` int unsigned NOT NULL,
    FOREIGN KEY(`id_contact`)
    REFERENCES `yury_savchuk`.`contacts`(`id_contact`)
    ON DELETE RESTRICT 
    ON UPDATE RESTRICT
)ENGINE=InnoDB;

