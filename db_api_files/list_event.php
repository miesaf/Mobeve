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
if(isset($input['evn_leader']) || isset($_GET['evn_leader'])){
	if(isset($input['evn_leader'])){
		$evn_leader = filter_var($input['evn_leader'], FILTER_SANITIZE_STRING);
	}else{
		$evn_leader = filter_var($_GET['evn_leader'], FILTER_SANITIZE_STRING);
	}

	$query    = "SELECT * FROM event WHERE evn_leader = \"$evn_leader\"";
	//$query    = "SELECT * FROM event";

	$result = mysqli_query($con, $query);
	//Check if there is any event
	if($result)
	{
		$response["status"] = 0;
		$response["message"] = "Fetch successful";
		
		$BIL = 0;
		
		$response["data"] = array();
		$response["data_total"] = $BIL;
		
		// iterate through all rows in result set
		while($row = mysqli_fetch_array($result))
		{
			$BIL++;
			
			$response["data_total"] = $BIL;
			
			// extract specific fields
			$evn_id		= $row['evn_id'];
			$evn_name	= $row['evn_name'];
			$evn_leader	= $row['evn_leader'];
			$evn_start	= $row['evn_start'];
			$evn_end	= $row['evn_end'];
			$evn_type	= $row["evn_type"];
			
			/*
			$response["data"][$evn_id]["evn_id"]		= $evn_id;
			$response["data"][$evn_id]["evn_name"]		= $evn_name;
			$response["data"][$evn_id]["evn_leader"]	= $evn_leader;
			$response["data"][$evn_id]["evn_start"]		= $evn_start;
			$response["data"][$evn_id]["evn_end"]		= $evn_end;
			$response["data"][$evn_id]["evn_type"]		= $evn_type;
			*/
			
			$event["evn_id"]		= $evn_id;
			$event["evn_name"]		= $evn_name;
			$event["evn_leader"]	= $evn_leader;
			$event["evn_start"]		= $evn_start;
			$event["evn_end"]		= $evn_end;
			$event["evn_type"]		= $evn_type;
			
			array_push($response["data"], $event);
			
		}
	}
	else{
		$response["status"] = 1;
		$response["message"] = "Fetch unsuccessfull";
	}
}
else{
	$response["status"] = 2;
	$response["message"] = "Missing mandatory parameters";
}

mysqli_close($con);

//Display the JSON response
echo json_encode($response, JSON_PRETTY_PRINT);
?>