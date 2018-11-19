<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


/*
  THIS FILE'S USE IS TO GATHER JSON FROM FRONT-END
  AND USE THAT JSON IN ORDER TO CALL THE CREATE FUNCTION
  IN THE FOODTRUCK CLASS. THIS FILE WILL RETURN JSON TO
  FRONT END TO INDICATE WHETHER THE FOOD TRUCK WAS CREATED
  SUCCESSFULLY OR IF IT WAS UNSUCCESSFUL
*/


// include database and object file
include_once '../config/database.php';
include_once '../objects/foodtrucks/functions.php';

// get database connection
$database = new Database();
$db = $database->getConnection();

// prepare food truck object
$ft = new FoodTruck($db);

// get keywords from url query string
//$keywords=isset($_GET["s"]) ? $_GET["s"] : "";
$data = json_decode(file_get_contents("php://input", true));
$ft->username = $data->username;
$ft->truck_name = $data->truck_name;
$ft->city = $data->city;
$ft->state = $data->state;
$ft->zip = $data->zip;
$ft->phonenumber = $data->phonenumber;
$ft->email = $data->email;


// create the food truck
if($ft->edit($ft->username, $ft->truck_name, $ft->city, $ft->state, $ft->zip, $ft->phonenumber, $ft->email)){
    echo json_encode(
        array("message" => "Food Truck was edited.")
    );
}

// if unable to create the food truck, tell the front end
else{
    echo json_encode(
        array("message" => "Unable to edit Food Truck.")
    );
}
?>
