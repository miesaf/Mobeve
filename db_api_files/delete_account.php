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
	
	$query = "SELECT user_pass, user_salt FROM user WHERE user_uname = ?";

	if($stmt = $con->prepare($query))
	{
		$stmt->bind_param("s", $user_uname);
		$stmt->execute();
		$stmt->bind_result($db_user_pass, $db_user_salt);
		if($stmt->fetch())
		{
			//Validate the password
			if(password_verify(concatPasswordWithSalt($user_pass, $db_user_salt), $db_user_pass))
			{
				$stmt->close();
				
				//Query to register new event
				$query2  = "DELETE FROM user WHERE user_uname= ?";
				
				if($stmt2 = $con->prepare($query2))
				{
					$stmt2->bind_param("s", $user_uname);
					$stmt2->execute();
					$response["status"] = 0;
					$response["message"] = "Account deleted";
					$stmt2->close();
				}
				else
				{
					$response["status"] = 1;
					$response["message"] = "Delete failed";
				}
			}
			else
			{
				$response["status"] = 2;
				$response["message"] = "Invalid password entered";
			}
		}
		else
		{
			$response["status"] = 3;
			$response["message"] = "Delete failed";
		}
		
		$stmt->close();
	}
	else
	{
		$response["status"] = 4;
		$response["message"] = "Delete failed";
	}
}
else
{
	$response["status"] = 5;
	$response["message"] = "Missing mandatory parameters";
}

echo json_encode($response, JSON_PRETTY_PRINT);
?>