<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// include database and object files
include_once '../config/database.php';
include_once '../objects/foodtrucks/functions.php';

// instantiate database
$database = new Database();
$db = $database->getConnection();

// initialize food truck object
$ft = new FoodTruck($db);

// get keywords from url query string
//$keywords=isset($_GET["s"]) ? $_GET["s"] : "";

//get data from front end json
$data = json_decode(file_get_contents("php://input", true));
$keywords = $data->keywords;

// query FTinfo table in database for search results
$stmt = $ft->search($keywords);
$num = $stmt->rowCount();

// check if more than 0 record found
if($num>0){

    // create a foodtruck arrate to store search results
    $ft_arr=array();
    $ft_arr["results"]=array();

    // retrieve our table contents
    // fetch() is faster than fetchAll()
    // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['TruckName'] to
        // just $TruckName only
        extract($row);

        $ft_item=array(
            "TruckName" => $TruckName,
            "City" => $City,
            "State" => $State,
            "Zip" => $Zip,
        );

        array_push($ft_arr["results"], $ft_item);
    }

    echo json_encode($ft_arr);
}

else{
    echo json_encode(
        array("message" => "No food trucks found.")
    );
}
?>
