<?php

/*
  THIS FILE IS NEEDED FOR ALL FUNCTIONALITIES
  THAT ARE DEPENDANT OF THE "ETinfo" TABLE.
  WE WILL USE THIS FILE TO CREATE NEW VENDORS
  AND AUTHENTICATE EXISTING VENDORS.
*/

class Vendor{

    // database connection and table name
    private $conn;
    private $table_name = "ETinfo";

    // object properties
    public $name;
    public $address;
    public $city;
    public $state;
    public $zip;

    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }

    // read contacts
    function read($username){

        // select all query
        $query = "SELECT * FROM " . $this->table_name . " WHERE UserID = (SELECT UserID FROM login WHERE username = ?)";

        // prepare query statement
        $stmt = $this->conn->prepare($query);

        // sanitize
        $username=htmlspecialchars(strip_tags($username));
        $username = "{$username}";

        // bind
        $stmt->bindParam(1, $username);

        // execute query
        $stmt->execute();

        return $stmt;
    }

  // create contact
  function edit($name, $address, $city, $state, $zip){

      // query to insert record
      $query = "INSERT INTO " . $this->table_name . " (EntertainerName, address, city, state, zip) VALUES (?,?,?,?,?)";

      // prepare query
      $stmt = $this->conn->prepare($query);

      // sanitize
      $name=htmlspecialchars(strip_tags($name));
      $name = "{$name}";

      $address=htmlspecialchars(strip_tags($address));
      $address = "{$address}";

      $city=htmlspecialchars(strip_tags($city));
      $city = "{$city}";

      $state=htmlspecialchars(strip_tags($state));
      $state = "{$state}";

      $zip=htmlspecialchars(strip_tags($zip));
      $zip = "{$zip}";


      // bind
      $stmt->bindParam(1, $name);
      $stmt->bindParam(2, $address);
      $stmt->bindParam(3, $city);
      $stmt->bindParam(4, $state);
      $stmt->bindParam(5, $zip);


      // execute query
      if($stmt->execute()){
          return true;
      }

      return false;

  }

  // delete contact
  function delete($keywords){

      // delete query
      $query = "DELETE FROM " . $this->table_name . " WHERE EntertainerName = ?";

      // prepare query
      $stmt = $this->conn->prepare($query);

      // sanitize
      $keywords=htmlspecialchars(strip_tags($keywords));
      $keywords = "{$keywords}";

      // bind
      $stmt->bindParam(1, $keywords);

      // execute query
      if($stmt->execute() && $stmt->rowCount() > 0){
          return true;
      }

      return false;

  }

  // search contacts
  function search($keywords){

    // $regex = "/[\s]/";
    // if (preg_match($regex, $keywords)) {
    //   read($username);
    //   return;
    // }
      // select all query
      $query = "SELECT * FROM " . $this->table_name . " WHERE EntertainerName LIKE ? OR address LIKE ? OR City LIKE ? or State LIKE ? or Zip LIKE ?";

      // prepare query statement
      $stmt = $this->conn->prepare($query);

      // sanitize
      $keywords=htmlspecialchars(strip_tags($keywords));
      $keywords = "%{$keywords}%";

      // bind
      $stmt->bindParam(1, $keywords);
      $stmt->bindParam(2, $keywords);
      $stmt->bindParam(3, $keywords);
      $stmt->bindParam(4, $keywords);
      $stmt->bindParam(5, $keywords);

      // execute query
      $stmt->execute();

      return $stmt;
  }

  //A SMALL DISPLAY FUNCTION ADDED BY BAO (OF COURSE COPY AND PASTE FROM HUGH CODES)
  function display($keywords){
        $query = "SELECT * FROM " . $this->table_name . " WHERE id = ? ";
        $stmt = $this->conn->prepare($query);

        $keywords=htmlspecialchars(strip_tags($keywords));
        $keywords = "{$keywords}";
        $stmt->bindParam(1, $keywords);
        $stmt->execute();
        return $stmt;
  }
}
?>
