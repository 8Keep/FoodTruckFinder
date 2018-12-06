<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// include database and object files
include_once '../config/database.php';
include_once '../objects/vendors/functions.php';

// instantiate database
$database = new Database();
$db = $database->getConnection();

// initialize vendor object
$et = new Vendor($db);

// get keywords from url query string
//$keywords=isset($_GET["s"]) ? $_GET["s"] : "";

//get data from front end json
$data = json_decode(file_get_contents("php://input", true));
$keywords = $data->keywords;

// query ETinfo table in database for search results
$stmt = $et->search($keywords);
$num = $stmt->rowCount();

// check if more than 0 record found
if($num>0){

    // create a vendor array to store search results
    $et_arr=array();
    $et_arr["results"]=array();

    // retrieve our table contents
    // fetch() is faster than fetchAll()
    // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['EntertainerName'] to
        // just $EntertainerName only
        extract($row);

        $et_item=array(
            "username" => $username,
            "imgURL" => $imgURL,
            "ETID" => $ETID, // using ETID from ETinfo table so that frontend can use this unique ID to query this Entertainer's info
            "EntertainerName" => $EntertainerName,
            "address" => $address,
            "City" => $City,
            "State" => $State,
            "Zip" => $Zip,
        );

        array_push($et_arr["results"], $et_item);
    }

    echo json_encode($et_arr);
}

else{
    echo json_encode(
        array("message" => "No vendors found.")
    );
}
?>
