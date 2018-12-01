<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
/*
    THIS FILE IS TO TAKE A JSON AND THEN UPDATE THE DESCRIPTION OF THE PROFILE
*/
// include database and object file
include_once '../config/database.php';
include_once '../objects/foodtrucks/functions.php';
// get database connection
$database = new Database();
$db = $database->getConnection();

$conn = $db;
$table_name = "FTinfo";

$data = json_decode(file_get_contents("php://input", true));
$username = $data->username;
$description = $data->description;
// update FTinfo set Description = 'This project is very annoying' where FTID = (select FTID from loginFT where username = 'connor');

$query = "UPDATE " . $table_name . " set Description = ? WHERE FTID = (select FTID from loginFT where username = ?)";

$stmt = $conn->prepare($query);


$description=htmlspecialchars(strip_tags($description));
$description = "{$description}";

$username=htmlspecialchars(strip_tags($username));
$username = "{$username}";

$stmt->bindParam(1, $description);
$stmt->bindParam(2, $username);


// create the food truck
if($stmt->execute())){
    echo json_encode(
        array("message" => "Description was Updated.")
    );
}
// if unable to create the food truck, tell the front end
else{
    echo json_encode(
        array("message" => "Unable to update description.")
    );
}
?>
