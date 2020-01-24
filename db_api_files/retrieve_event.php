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
	
	$query    = "SELECT * FROM event WHERE evn_id = \"$evn_id\"";

	$result = mysqli_query($con, $query);
	//Check if there is any event
	if($result)
	{
		// iterate through all rows in result set
		if($row = mysqli_fetch_array($result))
		{
			$response["status"] = 0;
			$response["message"] = "Fetch successful";
			
			//$events["events"] = array();
			$response["data"] = array();
			
			// extract specific fields
			$event["evn_id"]		= $row['evn_id'];
			$event["evn_name"]		= $row['evn_name'];
			$event["evn_leader"]	= $row['evn_leader'];
			$event["evn_start"]		= $row['evn_start'];
			$event["evn_end"]		= $row['evn_end'];
			$event["evn_type"]		= $row["evn_type"];
			$event["evn_rule"]		= $row["evn_rule"];
			
			array_push($response["data"], $event);
		}
		else
		{
			$response["status"] = 1;
			$response["message"] = "No data existed";
		}
	}
	else{
		$response["status"] = 2;
		$response["message"] = "Fetch unsuccessfull";
	}
}
else{
	$response["status"] = 3;
	$response["message"] = "Missing mandatory parameters";
}

mysqli_close($con);

//Display the JSON response
echo json_encode($response, JSON_PRETTY_PRINT);
?>