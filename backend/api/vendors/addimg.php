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

// prepare vendor object
$et = new Vendor($db);

// get keywords from url query string
//$keywords=isset($_GET["s"]) ? $_GET["s"] : "";
$data = json_decode(file_get_contents("php://input", true));
$et->username = $data->username;
$et->imgURL = $data->imgURL;

// edit the vendor's details
if($et->addimg($et->imgURL, $et->username)){
    echo json_encode(
        array("message" => "Vendor img was added.")
    );
}

// if unable to create the vendor, tell the user
else{
    echo json_encode(
        array("message" => "Unable to add img vendor.")
    );
}
?>