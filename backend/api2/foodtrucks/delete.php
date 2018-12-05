<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//  I'M NOT SURE IF WE'LL FIND USE FOR THIS FILE.

/*
  THIS FILE'S MAIN PURPOSE IS TO GATHER JSON FROM THE
  FRONT END AND USE THE GATHERED JSON IN ORDER TO CALL
  THE DELETE METHOD IN THE FOODTRUCK CLASS. THIS FILE
  WILL NOTIFY THE FRONT END WHETHER THE FOOD TRUCK WAS
  SUCCESSFULLY DELETED THROUGH JSON THAT IT SENDS BACK
  TO THE FRONT END.
*/


// include database and object file
include_once '../config/database.php';
include_once '../objects/foodtrucks/functions.php';

// get database connection
$database = new Database();
$db = $database->getConnection();

// prepare food truck object
$ft = new FoodTruck($db);

// get food truck info to be deleted
// $data = json_decode(file_get_contents("php://input"));
// $keyword = $data->keyword;
// $username = $data->username;


// get keywords from url query string
//$ft->id=$_GET["id"];
//$ft->username=$_GET["username"];

// delete the food truck
if($ft->delete()){
    echo json_encode(
        array("message" => "Food Truck was deleted.")
    );
}

// if unable to delete the food truck
else{
    echo json_encode(
        array("message" => "Unable to delete Food Truck.")
    );
}
?>
