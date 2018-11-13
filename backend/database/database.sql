create database largeproject;

  CREATE TABLE `largeproject`.`loginFT` (
    `FTID` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL DEFAULT '' UNIQUE,
    `email` VARCHAR(50) NOT NULL DEFAULT '' UNIQUE,
    `password` VARCHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY (`FTID`)
  );


  CREATE TABLE `largeproject`.`loginET` (
    `ETID` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL DEFAULT '' UNIQUE,
    `email` VARCHAR(50) NOT NULL DEFAULT '' UNIQUE,
    `password` VARCHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY (`ETID`)
  );


  CREATE TABLE `largeproject`.`FTinfo` (
    `FTinfoID` INT NOT NULL AUTO_INCREMENT,
    `TruckName` VARCHAR(100) NOT NULL DEFAULT '',
    `City` VARCHAR(50) NOT NULL DEFAULT '',
    `State` VARCHAR(25) NOT NULL DEFAULT '',
    `Zip` VARCHAR(5) NOT NULL DEFAULT '',
    `Range` INT NOT NULL DEFAULT 10,
    `FTID` INT,
    FOREIGN KEY (`FTID`)
    REFERENCES `largeproject`.`loginFT`(`FTID`),
    PRIMARY KEY (`FTinfoID`)
  ); 


  CREATE TABLE `largeproject`.`ETinfo` (
    `ETinfoID` INT NOT NULL AUTO_INCREMENT,
    `EntertainerName` VARCHAR(100) NOT NULL DEFAULT '',
    `address` VARCHAR(100) NOT NULL DEFAULT '',
    `City` VARCHAR(50) NOT NULL DEFAULT '',
    `State` VARCHAR(25) NOT NULL DEFAULT '',
    `Zip` VARCHAR(5) NOT NULL DEFAULT '',
    `ETID` INT,
    FOREIGN KEY (`ETID`)
    REFERENCES `largeproject`.`loginET`(`ETID`),
    PRIMARY KEY (`ETinfoID`)
  );


  CREATE TABLE `largeproject`.`Menu` (
    `FoodID` INT NOT NULL AUTO_INCREMENT,
    `DishName` VARCHAR(100) NOT NULL DEFAULT '',
    `Description` VARCHAR(1000) NOT NULL DEFAULT '',
    `Price` VARCHAR(10) NOT NULL DEFAULT '',
    `FTID` INT,
    FOREIGN KEY (`FTID`)
    REFERENCES `largeproject`.`loginFT`(`FTID`),
    PRIMARY KEY (`FoodID`)
  );
