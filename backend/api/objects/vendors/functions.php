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
    public $ET_name;
    public $address;
    public $city;
    public $state;
    public $zip;
    public $phone;
    public $email;
    public $first;
    public $last;

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
  function edit($username, $ET_name, $address, $city, $state, $zip, $first, $last, $email, $phone){

      // query to insert vendor details
      $query = "INSERT INTO " . $this->table_name . " (EntertainerName, address, city, state, zip, ETID, First, Last, email, phone, imgURL, Description) VALUES (?,?,?,?,?, (SELECT ETID from loginET WHERE username = ?),?,?,?,?)";

      // prepare query
      $stmt = $this->conn->prepare($query);

      // sanitize
      $username=htmlspecialchars(strip_tags($username));
      $username = "{$username}";

      $ET_name=htmlspecialchars(strip_tags($ET_name));
      $ET_name = "{$ET_name}";

      $address=htmlspecialchars(strip_tags($address));
      $address = "{$address}";

      $city=htmlspecialchars(strip_tags($city));
      $city = "{$city}";

      $state=htmlspecialchars(strip_tags($state));
      $state = "{$state}";

      $zip=htmlspecialchars(strip_tags($zip));
      $zip = "{$zip}";

      $first=htmlspecialchars(strip_tags($first));
      $first = "{$first}";

      $last=htmlspecialchars(strip_tags($last));
      $last = "{$last}";

      $email =htmlspecialchars(strip_tags($email));
      $email = "{$email}";
      
      $phone=htmlspecialchars(strip_tags($phone));
      $phone = "{$phone}";


      // bind
      $stmt->bindParam(1, $ET_name);
      $stmt->bindParam(2, $address);
      $stmt->bindParam(3, $city);
      $stmt->bindParam(4, $state);
      $stmt->bindParam(5, $zip);
      $stmt->bindParam(6, $username);
      $stmt->bindParam(7, $first);
      $stmt->bindParam(8, $last);
      $stmt->bindParam(9, $email);
      $stmt->bindParam(10, $phone);


      // execute query
      if($stmt->execute()){
          return true;
      }

      return false;

  }

  function addimg($imgURL, $username)
  {
    //UPDATE ftinfo SET imgURL = "http://localhost/images/ft2.jpg" WHERE FTID = (SELECT FTID FROM loginFT WHERE username = "Phongloz")
    $query = "UPDATE " . $this->table_name . " SET imgURL = ? WHERE ETID = (SELECT ETID FROM loginET WHERE username = ?)"; 
    $stmt = $this->conn->prepare($query);

    $username=htmlspecialchars(strip_tags($username));
    $username = "{$username}";

    $imgURL = htmlspecialchars(strip_tags($imgURL));
    $imgURL = "{$imgURL}";

    $stmt->bindParam(1, $imgURL);
    $stmt->bindParam(2, $username);

    if($stmt->execute()){
      return true;
    }

    return false;

  }
  // delete current vendor
  function delete(){

      // delete query
      $query = "DELETE FROM " . $this->table_name . " WHERE (ETID IS NULL)";

      // prepare query
      $stmt = $this->conn->prepare($query);

      // // sanitize
      // $keywords=htmlspecialchars(strip_tags($keywords));
      // $keywords = "{$keywords}";

      // $username=htmlspecialchars(strip_tags($username));
      // $username = "{$username}";

      // // bind
      // $stmt->bindParam(1, $keywords);
      // $stmt->bindParam(1, $username);

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
      $query = "SELECT * FROM " . $this->table_name . " WHERE First LIKE ? OR Last LIKE ? OR EntertainerName LIKE ? OR address LIKE ? OR City LIKE ? OR State LIKE ? OR Zip LIKE ? OR email LIKE ? OR phone LIKE ? OR Description LIKE ?";

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
      $stmt->bindParam(6, $keywords);
      $stmt->bindParam(7, $keywords);
      $stmt->bindParam(8, $keywords);
      $stmt->bindParam(9, $keywords);
      $stmt->bindParam(10, $keywords);

      // execute query
      $stmt->execute();

      return $stmt;
  }
}
?>
