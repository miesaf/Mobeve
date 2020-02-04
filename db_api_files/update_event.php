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
if(isset($input['evn_id']) && isset($input['evn_name']) && isset($input['evn_start']) && isset($input['evn_end']) && isset($input['evn_type'])){
	$EventID = $input['evn_id'];
	$EventName = $input['evn_name'];
	$StartDate = $input['evn_start'];
	$EndDate = $input['evn_end'];
	$EventType = $input['evn_type'];
	
	//Query to register new event
	$insertQuery  = "UPDATE event SET evn_name=?,evn_start=?,evn_end=?,evn_type=? WHERE evn_id=?";
	
	if($stmt = $con->prepare($insertQuery)){
		$stmt->bind_param("sssss", $EventName, $StartDate, $EndDate, $EventType, $EventID);
		$stmt->execute();
		$response["status"] = 0;
		$response["message"] = "Event updated";
		$stmt->close();
	}else{
		$response["status"] = 1;
		$response["message"] = "Update failed";
	}
}
else{
	$response["status"] = 2;
	$response["message"] = "Missing mandatory parameters";
}

echo json_encode($response, JSON_PRETTY_PRINT);
?>