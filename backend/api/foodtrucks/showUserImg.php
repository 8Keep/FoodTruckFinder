<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");

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

// update FTinfo set Description = 'This project is very annoying' where FTID = (select FTID from loginFT where username = 'connor');
// SELECT `imgURL` FROM `ftinfo` WHERE FTID = (SELECT FTID from loginft WHERE username = "baohong1")
$query = "SELECT imgURL FROM " . $table_name . " WHERE FTID = (select FTID from loginFT where username = ?)";

$stmt = $conn->prepare($query);


$username=htmlspecialchars(strip_tags($username));
$username = "{$username}";

$stmt->bindParam(1, $username);


$img_arr = array();
$img_arr["records"] = array();
// create the food truck
if($stmt->execute()){
	if($stmt->rowCount() > 0)
	{
		
		while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
			extract($row);

			$img_item=array(
            "imgURL" => $imgURL
        );
			array_push($img_arr["records"], $img_item);
		}
		echo json_encode($img_arr);
	}
	else
	{
	
		array_push($img_arr["records"], array("imgURL" => "No records found."));
    	echo json_encode($img_arr);
	}
    
}
// if unable to create the food truck, tell the front end
else{
    echo json_encode(
        array("message" => "Unable to load image.")
    );
}
?>
