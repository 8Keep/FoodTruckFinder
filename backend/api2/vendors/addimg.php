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
$username = $data->username
$upload_path = "../../imagesET/$username.jpg";
$img_path = "https://www.peopleorderourpatties.com/backend/imagesET/$username.jpg";
$image = $data->image;

// edit the vendor's details
if($et->addimg($img_path, $username)){
    echo json_encode(
        array("message" => "Vendor image was added.")
    );
}

// if unable to create the vendor, tell the user
else{
    echo json_encode(
        array("message" => "Unable to add image vendor.")
    );
}
?>