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
	
	//Get a unique Salt
	$salt         = getSalt();
	
	//Generate a unique password Hash
	$passwordHash = password_hash(concatPasswordWithSalt($user_pass,$salt),PASSWORD_DEFAULT);
	
	//Query to register new event
	$insertQuery  = "UPDATE user SET user_pass=?, user_salt=? WHERE user_uname=?";
	
	if($stmt = $con->prepare($insertQuery)){
		$stmt->bind_param("sss", $passwordHash, $salt, $user_uname);
		$stmt->execute();
		$response["status"] = 0;
		$response["message"] = "Password updated";
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