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
if(isset($input['evn_name']) && isset($input['evn_start']) && isset($input['evn_end']) && isset($input['evn_type'])){
	$EventName = $input['evn_name'];
	if(isset($input['evn_leader'])){
		$EventLeader = $input['evn_leader'];
	}else{
		$EventLeader = null;
	}
	$StartDate = $input['evn_start'];
	$EndDate = $input['evn_end'];
	$EventType = $input['evn_type'];
	
	//Check if user already exist
	if(!userExists($username)){
		
		$evnRule = array(
			'process_status' => 'incomplete',
			'process_level' => 1
		);

		$EventRule = json_encode($evnRule);
		
		//Query to register new event
		$insertQuery  = "INSERT INTO event(evn_id, evn_name, evn_leader, evn_start, evn_end, evn_type, evn_rule) VALUES (?,?,?,?,?,?,?)";
		if($stmt = $con->prepare($insertQuery)){
			$EVN_ID_GENERATION = generateRandomString();
			$stmt->bind_param("sssssss", $EVN_ID_GENERATION, $EventName, $EventLeader, $StartDate, $EndDate, $EventType, $EventRule);
			$stmt->execute();
			$response["status"] = 0;
			$response["message"] = "Event created";
			$response["data"]["evn_id"] = $EVN_ID_GENERATION;
			$response["data"]["evn_type"] = $EventType;
			$stmt->close();
		}
	}
	else{
		$response["status"] = 1;
		$response["message"] = "Event exists";
	}
}
else{
	$response["status"] = 2;
	$response["message"] = "Missing mandatory parameters";
}

function generateRandomString($length = 5) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

echo json_encode($response, JSON_PRETTY_PRINT);
?>