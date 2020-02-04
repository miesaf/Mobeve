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
if(isset($input['user_uname']) && isset($input['user_pass'])){
	$user_uname = $input['user_uname'];
	$user_pass = $input['user_pass'];
	$query    = "SELECT user_fname, user_pass, user_salt FROM user WHERE user_uname = ?";

	if($stmt = $con->prepare($query)){
		$stmt->bind_param("s",$user_uname);
		$stmt->execute();
		$stmt->bind_result($db_user_fname, $db_user_pass, $db_user_salt);
		if($stmt->fetch()){
			//Validate the password
			if(password_verify(concatPasswordWithSalt($user_pass,$db_user_salt),$db_user_pass)){
				$response["status"] = 0;
				$response["message"] = "Login successful";
				$response["user_fname"] = $db_user_fname;
				$response["user_uname"] = $user_uname;
				//$response["hash"] = concatPasswordWithSalt($user_pass,$db_user_salt);
			}
			else{
				$response["status"] = 1;
				$response["message"] = "Invalid username and password combination";
			}
		}else{
			$response["status"] = 1;
			$response["message"] = "Invalid username and password combination";
		}
		
		$stmt->close();
	}
}
else{
	$response["status"] = 2;
	$response["message"] = "Missing mandatory parameters";
}
//Display the JSON response
echo json_encode($response, JSON_PRETTY_PRINT);
?>