<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// include database and object files
include_once '../config/database.php';
include_once '../objects/vendors/functions.php';

// instantiate database
$database = new Database();
$db = $database->getConnection();

// initialize vendor object
$et = new Vendor($db);

// get keywords from JSON
$data = json_decode(file_get_contents("php://input", true));
$et->username = $data->username;

// query ETinfo in database
$stmt = $et->show($et->username);
$num = $stmt->rowCount();

// check if more than 0 record found
if($num>0){

    // attributes array
    $et_arr=array();
    $et_arr["records"]=array();

    // retrieve our table contents
    // fetch() is faster than fetchAll()
    // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($row);

        $et_item=array(
            "name" => $EntertainerName,
            "address" => $address,
            "city" => $City,
            "state" => $State,
            "zip" => $Zip,
        );

        array_push($et_arr["records"], $et_item);
    }

    echo json_encode($et_arr);
}

else{
    echo json_encode(
        array("message" => "No records found.")
    );
}
?>
