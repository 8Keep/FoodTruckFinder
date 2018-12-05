<?php

/*
  THIS FILE IS NEEDED FOR ALL FUNCTIONALITIES
  THAT ARE DEPENDANT OF THE "loginET" TABLE.
  WE WILL USE THIS FILE TO CREATE NEW VENDOR
  USERNAME/PASSWORD COMBOS AND AUTHENTICATE
  EXISTING VENDORS FOR SUCCESSFUL LOGIN.
*/

class User{

    // database connection and table name
    private $conn;
    private $table_name = "loginET";

    // object properties
    public $id;
    public $username;
    public $password;
    public $email;

    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }

    function create($username, $email, $password){

        // query to insert record
        $query = "INSERT INTO " . $this->table_name . " (username, email, password) VALUES (?, ?, ?)";

        // prepare query
        $stmt = $this->conn->prepare($query);

        // sanitize
        $username=htmlspecialchars(strip_tags($username));
        $username = "{$username}";
        $email=htmlspecialchars(strip_tags($email));
        $email = "{$email}";

        $password=htmlspecialchars(strip_tags($password));
        $password=md5($password);
        $password = "{$password}";

        // bind
        $stmt->bindParam(1, $username);
        $stmt->bindParam(2, $email);
        $stmt->bindParam(3, $password);

        // execute query
        if($stmt->execute()){
            return true;
        }

        return false;

    }

    function authenticate($username, $password){

      // select all query
      $query = "SELECT * FROM " . $this->table_name . " WHERE username LIKE ? AND password LIKE ?";

      // prepare query statement
      $stmt = $this->conn->prepare($query);

      // sanitize
      $username=htmlspecialchars(strip_tags($username));
      $username = "{$username}";

      $password=htmlspecialchars(strip_tags($password));
      $password= md5($password);
      $password = "{$password}";

      // bind
      $stmt->bindParam(1, $username);
      $stmt->bindParam(2, $password);

      // execute query
      $stmt->execute();

      return $stmt;

    }
}
?>
