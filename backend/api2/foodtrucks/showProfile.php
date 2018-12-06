<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// include database and object files
include_once '../config/database.php';
include_once '../objects/foodtrucks/functions.php';

// instantiate database
$database = new Database();
$db = $database->getConnection();

// initialize FoodTruck object
$ft = new FoodTruck($db);

// get keywords from JSON
$data = json_decode(file_get_contents("php://input", true));
$ft->ftid = $data->ftid;

// query FTinfo in database
$stmt = $ft->showProfile($ft->ftid);
$num = $stmt->rowCount();

// check if more than 0 record found
if($num>0){

    // attributes array
    $ft_arr=array();
    $ft_arr["records"]=array();

    // retrieve our table contents
    // fetch() is faster than fetchAll()
    // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($row);

        $ft_item=array(
            "imgURL" => $imgURL,
            "TruckName" => $TruckName,
            "city" => $City,
            "state" => $State,
            "zip" => $Zip,
            "first" => $First,
            "last" => $Last,
            "email" => $email,
            "phone" => $phone,
            "address" => $address,
            "description" => $Description,
            "username" => $username
        );

        array_push($ft_arr["records"], $ft_item);
    }

    echo json_encode($ft_arr);
}

else{
    echo json_encode(
        array("message" => "No records found.")
    );
}
?>
