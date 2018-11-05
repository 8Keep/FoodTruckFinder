<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// include database and object file
include_once '../../config/database.php';
include_once '../../objects/vendors/authenticate.php';

// get database connection
$database = new Database();
$db = $database->getConnection();

// instantiate user object
$user = new User($db);

// get username/password from url query string
//$name=isset($_GET["n"]) ? $_GET["n"] : "";
//$password=isset($_GET["p"]) ? $_GET["p"] : "";
// get user-entered username and password in json format
$data = json_decode(file_get_contents("php://input", true));
$user->username = $data->username;
$user->password = $data->password;

// create the contact
if($user->create($user->username, $user->password)){
    echo json_encode(
        array("message" => "Vendor account was created.")
    );
}

// if unable to create the contact, tell the user
else{
    echo json_encode(
        array("message" => "Unable to create user.")
    );
}
?>
