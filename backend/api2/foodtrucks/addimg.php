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
$username = $data->username;
$upload_path = "../../images/$username.jpg";
$img_path = "https://www.peopleorderourpatties.com/backend/images/$username.jpg";
$image = $data->image;


// create the food truck
if($ft->addimg($img_path, $username)){
  file_put_contents($upload_path, base64_decode($image));
    echo json_encode(
      
        array("message" => "Food Truck image was added.")
    );
}

// if unable to create the food truck, tell the front end
else{
    echo json_encode(
        array("message" => "Unable to add image Food Truck.")
    );
}
?>