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
    public $usernameBefore;
    public $ET_name;
    public $address;
    public $city;
    public $state;
    public $zip;
    public $phone;
    public $email;
    public $first;
    public $last;
    public $description;
    public $keywords;

    // constructor with $db as database connection
    public function __construct($db){
        $this->conn = $db;
    }

    // show user profile details
    function show(){

        // SELECT * FROM FTinfo, loginFT WHERE FTinfo.FTID = loginFT.FTID ORDER BY FTinfo.FTID DESC
        $query = "SELECT * FROM " . $this->table_name . ", loginET WHERE ETinfo.ETID = loginET.ETID ORDER BY ETinfo.ETID DESC";

        // prepare query statement
        $stmt = $this->conn->prepare($query);

        // sanitize
        // $username=htmlspecialchars(strip_tags($username));
        // $username = "{$username}";

        // bind
        //$stmt->bindParam(1, $username);

        // execute query
        $stmt->execute();

        return $stmt;
    }
    function showProfile($username)
    {
      //SELECT ftinfo.First, ftinfo.Last, ftinfo.TruckName, ftinfo.email, ftinfo.phone, ftinfo.address, ftinfo.City, ftinfo.State, ftinfo.Zip, ftinfo.Description, loginft.username FROM ftinfo, loginft WHERE ftinfo.FTID = (SELECT FTID FROM loginft WHERE username = "baohong1") AND loginft.username = "baohong1"
      $query = "SELECT ETinfo.First, ETinfo.Last, ETinfo.EntertainerName, ETinfo.email, ETinfo.phone, ETinfo.address, ETinfo.City, ETinfo.State, ETinfo.Zip, ETinfo.Description, ETinfo.imgURL, loginET.username FROM ETinfo, loginET WHERE ETinfo.ETID = (SELECT ETID FROM loginET WHERE username = ?) AND loginET.username = ?";

      $stmt = $this->conn->prepare($query);

      $username=htmlspecialchars(strip_tags($username));
      $username = "{$username}";

      $stmt->bindParam(1, $username);
      $stmt->bindParam(2, $username);

      $stmt->execute();
      return $stmt; 
          
    }

  // edit vendor details
  function edit($username, $ET_name, $address, $city, $state, $zip, $first, $last, $email, $phone){

      // query to insert vendor details
      $query = "INSERT INTO " . $this->table_name . " (EntertainerName, address, City, State, Zip, ETID, First, Last, email, phone) VALUES (?,?,?,?,?, (SELECT ETID from loginET WHERE username = ?),?,?,?,?)";

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
  function editProfile($usernameBefore, $username, $ET_name, $first, $last, $email, $phone, $address, $city, $state, $zip, $description)
  {
    // UPDATE ftinfo SET First = "Bao", Last = "Hong", `EntertainerName` = "Bao dep trai", `email` = "bao_mu2012@yahoo.com.vn", `phone` = "3214408647", `address` = "911 Mesa Oak Ct" , `City`= "Kissimmee", `State` = "FL", `Zip` = "34744", Description = "I want to end this semester right fking now" WHERE `FTID` = (SELECT FTID FROM loginft WHERE username = "baohong98")
    $query = "UPDATE ETinfo SET First = ?, Last = ?, EntertainerName = ?, email = ?, phone = ?, address = ?, City = ?, State = ?, Zip = ?, Description = ? WHERE ETID = (SELECT ETID FROM loginET WHERE username = ?)";

    $stmt = $this->conn->prepare($query);

    $ET_name=htmlspecialchars(strip_tags($ET_name));
    $ET_name = "{$ET_name}";

    $address = htmlspecialchars(strip_tags($address));
    $address = "{$address}";

    $city=htmlspecialchars(strip_tags($city));
    $city = "{$city}";

    $state=htmlspecialchars(strip_tags($state));
    $state = "{$state}";

    $zip=htmlspecialchars(strip_tags($zip));
    $zip = "{$zip}";

    $usernameBefore= htmlspecialchars(strip_tags($usernameBefore));
    $usernameBefore = "{$usernameBefore}";

    $first=htmlspecialchars(strip_tags($first));
    $first = "{$first}";

    $last=htmlspecialchars(strip_tags($last));
    $last = "{$last}";

    $email =htmlspecialchars(strip_tags($email));
    $email = "{$email}";
    
    $phone=htmlspecialchars(strip_tags($phone));
    $phone = "{$phone}";

    $description = htmlspecialchars(strip_tags($description));
    $description = "{$description}";

    $stmt->bindParam(1, $first);
    $stmt->bindParam(2, $last);
    $stmt->bindParam(3, $ET_name);
    $stmt->bindParam(4, $email);
    $stmt->bindParam(5, $phone);
    $stmt->bindParam(6, $address);
    $stmt->bindParam(7, $city);
    $stmt->bindParam(8, $state);
    $stmt->bindParam(9, $zip);
    $stmt->bindParam(10, $description);
    $stmt->bindParam(11, $usernameBefore);

    if($stmt->execute() && $this->editLoginTab($usernameBefore, $username, $email)){
          return true;
      }

      return false;

  }
  function editLoginTab($usernameBefore,$username, $email)
  {
    $query = "UPDATE loginET SET username = ?, email = ? WHERE username = ?";
    $stmt = $this->conn->prepare($query);

    $usernameBefore= htmlspecialchars(strip_tags($usernameBefore));
    $usernameBefore = "{$usernameBefore}";

    $username= htmlspecialchars(strip_tags($username));
    $username = "{$username}";

    $email= htmlspecialchars(strip_tags($email));
    $email= "{$email}";

    $stmt->bindParam(1, $username);
    $stmt->bindParam(2, $email);
    $stmt->bindParam(3, $usernameBefore);

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
      $query = "SELECT * FROM ETinfo Left JOIN loginET ON ETinfo.ETID = loginET.ETID WHERE First LIKE ? OR Last LIKE ? OR EntertainerName LIKE ? OR address LIKE ? OR City LIKE ? OR State LIKE ? OR Zip LIKE ? OR ETinfo.email LIKE ? OR phone LIKE ? OR Description LIKE ?";

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
