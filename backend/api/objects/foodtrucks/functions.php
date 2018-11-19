<?php

class FoodTruck{

    // database connection and table name
    private $conn;
    private $table_name = "FTinfo";

    // object properties
    public $username;
    public $truck_name;
    public $city;
    public $state;
    public $zip;
    public $range;
    public $address;
    public $phonenumber;

    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }

    // show user profile details
    function show($username){

        // select all query
        $query = "SELECT * FROM " . $this->table_name . " WHERE FTID = (SELECT FTID from loginFT WHERE username = ?)";

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

  // edit user details
  function edit($username, $truck_name, $city, $state, $zip){

      // query to insert user details
      $query = "INSERT INTO " . $this->table_name . " (TruckName, City, State, Zip, FTID) VALUES (?,?,?,?, (SELECT FTID FROM loginFT WHERE username = ?))";

      // prepare query
      $stmt = $this->conn->prepare($query);

      // sanitize
      $truck_name=htmlspecialchars(strip_tags($truck_name));
      $truck_name = "{$truck_name}";

      $city=htmlspecialchars(strip_tags($city));
      $city = "{$city}";

      $state=htmlspecialchars(strip_tags($state));
      $state = "{$state}";

      $zip=htmlspecialchars(strip_tags($zip));
      $zip = "{$zip}";

      $username=htmlspecialchars(strip_tags($username));
      $username = "{$username}";

      // bind
      $stmt->bindParam(1, $truck_name);
      $stmt->bindParam(2, $city);
      $stmt->bindParam(3, $state);
      $stmt->bindParam(4, $zip);
      $stmt->bindParam(5, $username);

      // execute query
      if($stmt->execute()){
          return true;
      }

      return false;

  }

  // delete current food truck
  function delete($keyword, $username){

      // delete query
      $query = "DELETE FROM " . $this->table_name . " WHERE TruckName = ? AND FTID = (SELECT FTID FROM loginFT WHERE username = ?)";

      // prepare query
      $stmt = $this->conn->prepare($query);

      // sanitize
      $keyword=htmlspecialchars(strip_tags($keyword));
      $keyword = "{$keyword}";

      $username=htmlspecialchars(strip_tags($username));
      $username = "{$username}";

      // bind
      $stmt->bindParam(1, $keyword);
      $stmt->bindParam(1, $username);

      // execute query
      if($stmt->execute() && $stmt->rowCount() > 0){
          return true;
      }

      return false;

  }

  // search food trucks
  function search($keywords){

    // $regex = "/[\s]/";
    // if (preg_match($regex, $keywords)) {
    //   read($username);
    //   return;
    // }
      // select all query
      $query = "SELECT * FROM " . $this->table_name . " WHERE TruckName LIKE ? OR City LIKE ? OR State LIKE ? or Zip LIKE ?";

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


      // execute query
      $stmt->execute();

      return $stmt;
  }
}
?>
