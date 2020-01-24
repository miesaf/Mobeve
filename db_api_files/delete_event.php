<?php
header('Cache-Control: no-cache, must-revalidate');
header("Content-Type: application/json");

$response = array();
include 'db/db_connect.php';
include 'functions.php';

//Get the input request parameters
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

//Check for Mandatory parameters
if(isset($input['evn_id']) || isset($_GET['evn_id'])){
	if(isset($input['evn_id'])){
		$evn_id = $input['evn_id'];
	}else{
		$evn_id = $_GET['evn_id'];
	}
	
	//Query to register new event
	$query  = "DELETE FROM event WHERE evn_id=\"$evn_id\"";
	
	if($stmt = $con->prepare($query)){
		$stmt->bind_param("s", $EventID);
		$stmt->execute();
		$response["status"] = 0;
		$response["message"] = "Event deleted";
		$stmt->close();
	}else{
		$response["status"] = 1;
		$response["message"] = "Delete failed";
	}
}
else{
	$response["status"] = 2;
	$response["message"] = "Missing mandatory parameters";
}

echo json_encode($response, JSON_PRETTY_PRINT);
?>