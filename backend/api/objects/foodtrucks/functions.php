<?php

class FoodTruck{

    // database connection and table name
    private $conn;
    private $table_name = "FTinfo";

    // object properties
    public $truck_name;
    public $city;
    public $state;
    public $zip;
    public $range;

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
  function edit($truck_name, $city, $state, $zip, $range){

      // query to insert record
      $query = "INSERT INTO " . $this->table_name . " (TruckName, City, State, Zip) VALUES (?,?,?,?)";

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

      // bind
      $stmt->bindParam(1, $truck_name);
      $stmt->bindParam(2, $city);
      $stmt->bindParam(3, $state);
      $stmt->bindParam(4, $zip);


      // execute query
      if($stmt->execute()){
          return true;
      }

      return false;

  }

  // delete contact
  function delete($keyword){

      // delete query
      $query = "DELETE FROM " . $this->table_name . " WHERE TruckName = ?";

      // prepare query
      $stmt = $this->conn->prepare($query);

      // sanitize
      $keyword=htmlspecialchars(strip_tags($keyword));
      $keyword = "{$keyword}";

      // bind
      $stmt->bindParam(1, $keyword);

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
