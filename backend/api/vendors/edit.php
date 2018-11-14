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
$et->name = $data->name;
$et->address = $data->address;
$et->city = $data->city;
$et->state = $data->state;
$et->zip = $data->zip;

// edit the vendor's details
if($et->edit($et->username, $et->name, $et->address, $et->city, $et->state, $et->zip)){
    echo json_encode(
        array("message" => "Vendor was edited.")
    );
}

// if unable to create the vendor, tell the user
else{
    echo json_encode(
        array("message" => "Unable to edit vendor.")
    );
}
?>
