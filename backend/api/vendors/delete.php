<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


// include database and object file
include_once '../config/database.php';
include_once '../objects/vendors/functions.php';

// get database connection
$database = new Database();
$db = $database->getConnection();

// prepare contact object
$et = new Vendor($db);

// get contact info to be deleted
$data = json_decode(file_get_contents("php://input"));
$keyword = $data->keyword;

// get keywords from url query string
//$contact->id=$_GET["id"];
//$contact->username=$_GET["username"];

// delete the contact
if($et->delete($keyword)){
    echo json_encode(
        array("message" => "Vendor was deleted.")
    );
}

// if unable to delete the contact
else{
    echo json_encode(
        array("message" => "Unable to delete vendor.")
    );
}
?>
