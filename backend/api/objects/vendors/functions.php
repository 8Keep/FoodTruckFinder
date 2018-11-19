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
    public $username;
    public $name;
    public $address;
    public $city;
    public $state;
    public $zip;
    public $phonenumber;
    public $email;

    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }

    // show user profile details
    function show($username){

        // select all query
        $query = "SELECT * FROM " . $this->table_name . " WHERE ETID = (SELECT ETID from loginET WHERE username = ?)";

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

  // edit vendor details
  function edit($username, $name, $address, $city, $state, $zip, $phonenumber, $email){

      // query to insert vendor details
      $query = "INSERT INTO " . $this->table_name . " (EntertainerName, address, city, state, zip, ETID) VALUES (?,?,?,?,?,?,?, (SELECT ETID from loginET WHERE username = ?))";

      // prepare query
      $stmt = $this->conn->prepare($query);

      // sanitize
      $username=htmlspecialchars(strip_tags($username));
      $username = "{$username}";

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
    
      $phonenumber=htmlspecialchars(strip_tags($phonenumber));
      $phonenumber = "{$phonenumber}";
    
      $email=htmlspecialchars(strip_tags($email));
      $email = "{$email}";


      // bind
      $stmt->bindParam(1, $name);
      $stmt->bindParam(2, $address);
      $stmt->bindParam(3, $city);
      $stmt->bindParam(4, $state);
      $stmt->bindParam(5, $zip);
      $stmt->bindParam(6, $phonenumber);
      $stmt->bindParam(7, $email);
      $stmt->bindParam(8, $username);


      // execute query
      if($stmt->execute()){
          return true;
      }

      return false;

  }

  // delete current vendor
  function delete($keywords, $username){

      // delete query
      $query = "DELETE FROM " . $this->table_name . " WHERE EntertainerName = ? AND FTID = (SELECT FTID FROM loginFT WHERE username = ?)";

      // prepare query
      $stmt = $this->conn->prepare($query);

      // sanitize
      $keywords=htmlspecialchars(strip_tags($keywords));
      $keywords = "{$keywords}";

      $username=htmlspecialchars(strip_tags($username));
      $username = "{$username}";

      // bind
      $stmt->bindParam(1, $keywords);
      $stmt->bindParam(1, $username);

      // execute query
      if($stmt->execute() && $stmt->rowCount() > 0){
          return true;
      }

      return false;

  }

  // search vendors
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
}
?>
