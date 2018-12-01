create database largeproject;

  CREATE TABLE `largeproject`.`loginFT` (
    `FTID` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL DEFAULT '' UNIQUE,
    `email` VARCHAR(100) NOT NULL DEFAULT '' UNIQUE,
    `password` VARCHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY (`FTID`)
  );


  CREATE TABLE `largeproject`.`loginET` (
    `ETID` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL DEFAULT '' UNIQUE,
    `email` VARCHAR(100) NOT NULL DEFAULT '' UNIQUE,
    `password` VARCHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY (`ETID`)
  );


  CREATE TABLE `largeproject`.`FTinfo` (
    `FTinfoID` INT NOT NULL AUTO_INCREMENT,
    `First` VARCHAR(50) NOT NULL DEFAULT '',
    `Last` VARCHAR(50) NOT NULL DEFAULT '',
    `TruckName` VARCHAR(100) NOT NULL DEFAULT '',
    `City` VARCHAR(50) NOT NULL DEFAULT '',
    `State` VARCHAR(25) NOT NULL DEFAULT '',
    `Zip` VARCHAR(5) NOT NULL DEFAULT '',
    `email` VARCHAR(50) NOT NULL DEFAULT '',
    `phone` VARCHAR(50) NOT NULL DEFAULT '',
    `Description` TEXT,
    `FTID` INT UNIQUE NOT NULL,
    `imgURL` VARCHAR(2000) NOT NULL DEFAULT '',
    `menuURL` VARCHAR(2000) NOT NULL DEFAULT '',
    FOREIGN KEY (`FTID`)
    REFERENCES `largeproject`.`loginFT`(`FTID`),
    PRIMARY KEY (`FTinfoID`)
  ); 


  CREATE TABLE `largeproject`.`ETinfo` (
    `ETinfoID` INT NOT NULL AUTO_INCREMENT,
    `First` VARCHAR(50) NOT NULL DEFAULT '',
    `Last` VARCHAR(50) NOT NULL DEFAULT '',
    `EntertainerName` VARCHAR(100) NOT NULL DEFAULT '',
    `address` VARCHAR(100) NOT NULL DEFAULT '',
    `City` VARCHAR(50) NOT NULL DEFAULT '',
    `State` VARCHAR(25) NOT NULL DEFAULT '',
    `Zip` VARCHAR(5) NOT NULL DEFAULT '',
    `email` VARCHAR(50) NOT NULL DEFAULT '',
    `phone` VARCHAR(50) NOT NULL DEFAULT '',
    `Description` TEXT,
    `ETID` INT UNIQUE NOT NULL,
    `imgURL` VARCHAR(2000) NOT NULL DEFAULT '',
    FOREIGN KEY (`ETID`)
    REFERENCES `largeproject`.`loginET`(`ETID`),
    PRIMARY KEY (`ETinfoID`)
  );
