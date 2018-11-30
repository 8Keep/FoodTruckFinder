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
    public $phone;
    public $email;
    public $first;
    public $last;
    public $imgURL;
    public $description;
   

    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }

    // show user profile details
    function show(){

        // select all query
        $query = "SELECT * FROM " . $this->table_name  ;

        // prepare query statement
        $stmt = $this->conn->prepare($query);

        // sanitize
        // $username=htmlspecialchars(strip_tags($username));
        // $username = "{$username}";

        // // bind
        // $stmt->bindParam(1, $username);

        // execute query
        $stmt->execute();

        return $stmt;
    }

  // edit user details
  function edit($username, $truck_name, $city, $state, $zip, $first, $last, $email, $phone, $imgURL, $description){

      // query to insert user details
      $query = "INSERT INTO " . $this->table_name . " (TruckName, City, State, Zip, FTID, First, Last, email, phone, imgURL, Description) VALUES (?,?,?,?, (SELECT FTID FROM loginFT WHERE username = ?),?,?,?,?,?,?)";

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

      $first=htmlspecialchars(strip_tags($first));
      $first = "{$first}";

      $last=htmlspecialchars(strip_tags($last));
      $last = "{$last}";

      $email =htmlspecialchars(strip_tags($email));
      $email = "{$email}";
      
      $phone=htmlspecialchars(strip_tags($phone));
      $phone = "{$phone}";
      
      $imgURL=htmlspecialchars(strip_tags($imgURL));
      $imgURL = "{$imgURL}";
      
      $description=htmlspecialchars(strip_tags($description));
      $description = "{$description}";

      // bind
      $stmt->bindParam(1, $truck_name);
      $stmt->bindParam(2, $city);
      $stmt->bindParam(3, $state);
      $stmt->bindParam(4, $zip);
      $stmt->bindParam(5, $username);
      $stmt->bindParam(6, $first);
      $stmt->bindParam(7, $last);
      $stmt->bindParam(8, $email);
      $stmt->bindParam(9, $phone);
      $stmt->bindParam(10, $imgURL);
      $stmt->bindParam(11, $description);

      // execute query
      if($stmt->execute()){
          return true;
      }

      return false;

  }
  function addimg($imgURL, $username)
  {
    //UPDATE ftinfo SET imgURL = "http://localhost/images/ft2.jpg" WHERE FTID = (SELECT FTID FROM loginFT WHERE username = "Phongloz")
    $query = "UPDATE " . $this->table_name . " SET imgURL = ? WHERE FTID = (SELECT FTID FROM loginFT WHERE username = ?)";
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


  // delete current food truck
  function delete(){

      // delete query
      $query = "DELETE FROM " . $this->table_name . " WHERE (FTID IS NULL) ";

      //prepare query
      $stmt = $this->conn->prepare($query);

      // sanitize
      //$keyword=htmlspecialchars(strip_tags($keyword));
      //$keyword = "{$keyword}";

      //$username=htmlspecialchars(strip_tags($username));
      //$username = "{$username}";

      // bind
      //$stmt->bindParam(1, $keyword);
      //$stmt->bindParam(2, $username);

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
      $query = "SELECT * FROM " . $this->table_name . " WHERE First LIKE ? OR Last LIKE ? OR TruckName LIKE ? OR City LIKE ? OR State LIKE ? OR Zip LIKE ? OR email LIKE ? OR phone LIKE ? OR Description LIKE ?";

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


      // execute query
      $stmt->execute();

      return $stmt;
  }
}
?>
