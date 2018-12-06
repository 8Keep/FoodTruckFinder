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
include_once '../objects/vendors/functions.php';

// get database connection
$database = new Database();
$db = $database->getConnection();

// prepare food truck object
$et = new Vendor($db);

// get keywords from url query string
//$keywords=isset($_GET["s"]) ? $_GET["s"] : "";
$data = json_decode(file_get_contents("php://input", true));

$et->usernameBefore = $data->usernameBefore;
$et->username = $data->username;
$et->truck_name = $data->truck_name;
$et->address = $data->address;
$et->city = $data->city;
$et->state = $data->state;
$et->zip = $data->zip;
$et->first = $data->first;
$et->last = $data->last;
$et->email = $data->email;
$et->phone = $data->phone;
$et->description = $data->description;



// function editProfile($usernameBefore, $username, $truck_name, $first, $last, $email, $phone, $address, $city, $state, $zip, $description)
if($et->editProfile($et->usernameBefore, $et->username, $et->truck_name, $et->first, $et->last, $et->email, $et->phone, $et->address, $et->city, $et->state, $et->zip, $et->description)){
    echo json_encode(
        array("message" => "Your profile was edited.")
    );
}

// if unable to create the food truck, tell the front end
else{
    echo json_encode(
        array("message" => "Unable to edit your profile. Please check your username/email")
    );
}
?>