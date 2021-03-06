<?php
	header('Cache-Control: no-cache, must-revalidate');
	header("Content-Type: application/json");

	$response = array();
	include 'db/db_connect.php';
	require_once 'functions.php';
	
	//ini_set('display_errors', 1);
	//ini_set('display_startup_errors', 1);
	//error_reporting(E_ALL);

	//Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array

	//Check for Mandatory parameters
	if(isset($input['evn_id']) && isset($input['rule_level']))
	{
		$RuleLevel = $input['rule_level'];
		$EventID = $input['evn_id'];
		$Ans_1 = $input['ans_1'];
		$Ans_2 = $input['ans_2'];
		$Ans_3 = $input['ans_3'];
		$Ans_4 = $input['ans_4'];
		$Ans_5 = $input['ans_5'];
		$Ans_6 = $input['ans_6'];
		$Ans_7 = $input['ans_7'];
		$Ans_8 = $input['ans_8'];
		$Ans_9 = $input['ans_9'];
		$Ans_10 = $input['ans_10'];
		$Ans_11 = $input['ans_11'];
		$Ans_12 = $input['ans_12'];
		$Ans_13 = $input['ans_13'];
		$Ans_14 = $input['ans_14'];
		
		$query    = "SELECT evn_rule, evn_name FROM event WHERE evn_id = \"$EventID\"";

		$result = mysqli_query($con, $query);
		//Check if there is any event
		if($result)
		{
			// iterate through all rows in result set
			if($row = mysqli_fetch_array($result))
			{
				// extract specific fields
				$T_EVN_NAME	= $row['evn_name'];
				$TEMP		= $row['evn_rule'];
				$T_Rule = json_decode($TEMP, TRUE);
				unset($TEMP);
				
				//Check rule level
				if($RuleLevel == "1")
				{
					$checkEmpty = false;
					
					if(empty($input['ans_1'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_2'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_3'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_4'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_5'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_6'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_7'])){
						$checkEmpty = true;
					}
					
					if(!$checkEmpty)
					{
						//Build rule JSON
						$ans = array(
							'ans_1' => $Ans_1,
							'ans_2' => $Ans_2,
							'ans_3' => $Ans_3,
							'ans_4' => $Ans_4,
							'ans_5' => $Ans_5,
							'ans_6' => $Ans_6,
							'ans_7' => $Ans_7
						);
						
						$T_Rule["process_level"] = 2;
						$T_Rule["known_facts"][1] = $ans;
						
						$U_EventRule = json_encode($T_Rule, JSON_PRETTY_PRINT);
						
						//Query to register new event
						$insertQuery  = "UPDATE event SET evn_rule=? WHERE evn_id=?";
						if($stmt = $con->prepare($insertQuery)){
							$stmt->bind_param("ss", $U_EventRule, $EventID);
							$stmt->execute();
							$response["status"] = 0;
							$response["message"] = "Inference saved";
							$response["data"]["evn_id"] = $EventID;
							$response["data"]["evn_type"] = "Workshop";
							$stmt->close();
						} else {
							$response["status"] = 1;
							$response["message"] = "Failed to save inferences";
						}
					}
					else
					{
						$response["status"] = 1;
						$response["message"] = "Missing mandatory parameters";
					}
				}
				elseif($RuleLevel == "2")
				{
					$checkEmpty = false;
					
					if(empty($input['ans_1'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_2'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_3'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_4'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_5'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_6'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_7'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_8'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_9'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_10'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_11'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_12'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_13'])){
						$checkEmpty = true;
					}elseif(empty($input['ans_14'])){
						$checkEmpty = true;
					}
					
					if(!$checkEmpty)
					{
						//Build rule JSON
						$ans = array(
							'ans_1' => $Ans_1,
							'ans_2' => $Ans_2,
							'ans_3' => $Ans_3,
							'ans_4' => $Ans_4,
							'ans_5' => $Ans_5,
							'ans_6' => $Ans_6,
							'ans_7' => $Ans_7,
							'ans_8' => $Ans_8,
							'ans_9' => $Ans_9,
							'ans_10' => $Ans_10,
							'ans_11' => $Ans_11,
							'ans_12' => $Ans_12,
							'ans_13' => $Ans_13,
							'ans_14' => $Ans_14
						);
						
						$T_Rule["process_level"] = 3;
						$T_Rule["known_facts"][2] = $ans;
						
						$U_EventRule = json_encode($T_Rule, JSON_PRETTY_PRINT);
						
						//Query to register new event
						$insertQuery  = "UPDATE event SET evn_rule=? WHERE evn_id=?";
						if($stmt = $con->prepare($insertQuery)){
							$stmt->bind_param("ss", $U_EventRule, $EventID);
							$stmt->execute();
							$response["status"] = 0;
							$response["message"] = "Inference saved";
							$response["data"]["evn_id"] = $EventID;
							$response["data"]["evn_type"] = "Workshop";
							$stmt->close();
						} else {
							$response["status"] = 1;
							$response["message"] = "Failed to save inferences";
						}
					}
					else
					{
						$response["status"] = 1;
						$response["message"] = "Missing mandatory parameters";
					}
				}
				elseif($RuleLevel == "3")
				{
					// Result generation
					$T_Rule["process_status"] = "completed";
					$T_Rule["process_level"] = 4;
					
					if($T_Rule["known_facts"][1]["ans_1"] == "Registered"){
						// ------- Mutual tasks -------
						appendTask($T_Data, "Special Task Force", "Handle registration of participants");
						appendTask($T_Data, "Special Task Force", "Print the final list of participants");
						appendTask($T_Data, "Special Task Force", "Setup attendance booth for entrance");
						if($T_Rule["known_facts"][2]["ans_1"] == "Free of charge"){
							// ------- Tasks -------
						}elseif($T_Rule["known_facts"][2]["ans_1"] == "Paid"){
							appendTask($T_Data, "Special Task Force", "Collect registration fee");
							appendTask($T_Data, "Special Task Force", "Record collected register fee");
						}
					}
					
					if($T_Rule["known_facts"][1]["ans_2"] == "Yes"){
						// ------- Mutual tasks -------
						if($T_Rule["known_facts"][2]["ans_2"] == "Yes"){
							appendTask($T_Data, "Multimedia", "Design a poster");
							appendTask($T_Data, "Special Task Force", "Distribute printed poster");
							appendTask($T_Data, "Logistics", "Print poster");
							appendTask($T_Data, "Multimedia", "Distribute digital poster across social media");
						}
						
						if($T_Rule["known_facts"][2]["ans_3"] == "Yes"){
							appendTask($T_Data, "Multimedia", "Shoot video for advetisement");
							appendTask($T_Data, "Multimedia", "Produce advetisement video");
							appendTask($T_Data, "Multimedia", "Upload advertisement video into social media");
						}
						
						if($T_Rule["known_facts"][2]["ans_4"] == "Yes"){
							appendTask($T_Data, "Multimedia", "Design a banner");
							appendTask($T_Data, "Logistics", "Order for banner printing");
							appendTask($T_Data, "Special Task Force", "Hang the banner at proper place");
						}
						
						if($T_Rule["known_facts"][2]["ans_5"] == "Yes"){
							appendTask($T_Data, "Multimedia", "Record advertisement audio");
							appendTask($T_Data, "Multimedia", "Distribute advertisement audio across social media");
							appendTask($T_Data, "Special Task Force", "Play advertisement audio on PA Systems");
						}
					}
					
					if($T_Rule["known_facts"][1]["ans_3"] == "Catering"){
						appendTask($T_Data, "Logistics", "Determine what menu shall be ordered");
						appendTask($T_Data, "Logistics", "Deal with catering for food ordering");
						appendTask($T_Data, "Logistics", "Attend the food distribtution booth");
					}elseif($T_Rule["known_facts"][1]["ans_3"] == "Cook"){
						appendTask($T_Data, "Foods", "Determine what menu shall be cook");
						appendTask($T_Data, "Foods", "Buy raw materials for cook");
						appendTask($T_Data, "Foods", "Gather cooking tools");
						appendTask($T_Data, "Foods", "Cook the food");
						appendTask($T_Data, "Foods", "Serve food to participants");
					}
					
					if($T_Rule["known_facts"][1]["ans_4"] == "Printed"){
						appendTask($T_Data, "Multimedia", "Design the certificate for participants");
						appendTask($T_Data, "Logistics", "Buy blank certificates");
						appendTask($T_Data, "Special Task Force", "Mail merge participants data into certificate");
						appendTask($T_Data, "Special Task Force", "Print certificate");
						appendTask($T_Data, "Special Task Force", "Hand over the certificate to participants");
					}elseif($T_Rule["known_facts"][1]["ans_4"] == "Digital"){
						appendTask($T_Data, "Multimedia", "Design the certificate for participants");
						appendTask($T_Data, "Logistics", "Deal with digital certificate vendor");
						appendTask($T_Data, "Special Task Force", "Hand over participants data certificate vendor");
						appendTask($T_Data, "Multimedia", "Inform participants how to access the digital certificate");
					}
					
					if($T_Rule["known_facts"][1]["ans_5"] == "Yes"){
						if($T_Rule["known_facts"][2]["ans_6"] == "Yes"){
							appendTask($T_Data, "Planning and Execution", "Gather digital tutoring material into one folder");
							appendTask($T_Data, "Planning and Execution", "Design slides or notes with related knowledge to the workshop");
							appendTask($T_Data, "Planning and Execution", "Distribute the digital tutoring material to participants");
						}
						
						if($T_Rule["known_facts"][2]["ans_7"] == "Yes"){
							appendTask($T_Data, "Planning and Execution", "Design slides or notes with related knowledge to the workshop");
							appendTask($T_Data, "Planning and Execution", "Print the tutoring handout");
							appendTask($T_Data, "Planning and Execution", "Distribute the printed handout to participants");
						}
						
						if($T_Rule["known_facts"][2]["ans_8"] == "Yes"){
							appendTask($T_Data, "Planning and Execution", "Determine what tools and props shall be use for tutoring");
							appendTask($T_Data, "Logistics", "Lease or lend tools and props related to workshop course");
							appendTask($T_Data, "Planning and Execution", "Supervise participants upon the usage of props");
							appendTask($T_Data, "Planning and Execution", "Distribute and recollect the tools and prop to participants");
						}
					}
					
					if($T_Rule["known_facts"][1]["ans_6"] == "Committee Member"){
						if($T_Rule["known_facts"][2]["ans_9"] == "Among committee"){
							appendTask($T_Data, "Planning and Execution", "Assign several committee members as assistants during tutoring");
						}elseif($T_Rule["known_facts"][2]["ans_9"] == "Outsource"){
							appendTask($T_Data, "Planning and Execution", "Appoint volunteers as assistance during tutoring");
							appendTask($T_Data, "Planning and Execution", "Award volunteers with certificates or appreciation letter");
						}
					}elseif($T_Rule["known_facts"][1]["ans_6"] == "Outsource"){
						if($T_Rule["known_facts"][2]["ans_9"] == "Among committee"){
							appendTask($T_Data, "Planning and Execution", "Assign several committee members as assistants during tutoring");
						}elseif($T_Rule["known_facts"][2]["ans_9"] == "Outsource"){
							appendTask($T_Data, "Planning and Execution", "Appoint volunteers as assistance during tutoring");
							appendTask($T_Data, "Planning and Execution", "Award volunteers with certificates or appreciation letter");
						}
						
						if($T_Rule["known_facts"][2]["ans_10"] == "Yes"){
							appendTask($T_Data, "Planning and Execution", "Award tutor with emoluments");
						}
					}
					
					if($T_Rule["known_facts"][1]["ans_7"] == "Formal"){
						if($T_Rule["known_facts"][2]["ans_11"] == "Yes"){
							appendTask($T_Data, "Food", "Arrange VIP dining place");
							appendTask($T_Data, "Food", "Serve the VIPs for banquet");
							appendTask($T_Data, "Food", "Wear proper formal attire when serving VIP for banquet");
						}
						if($T_Rule["known_facts"][2]["ans_12"] == "Yes"){
							appendTask($T_Data, "Logistics", "Order souvenir for VIP");
							appendTask($T_Data, "Special Task Force", "Wrap VIP souvenir accordingly");
							appendTask($T_Data, "Planning and Execution", "Include souvenir handing session");
						}
						if($T_Rule["known_facts"][2]["ans_13"] == "Yes"){
							appendTask($T_Data, "Logistics", "Order prizes for participants");
							appendTask($T_Data, "Planning and Execution", "Setup prize table and plan prize handing in the ceremony");
							appendTask($T_Data, "Special Task Force", "Become an usher and prize runner");
						}
						if($T_Rule["known_facts"][2]["ans_14"] == "Yes"){
							appendTask($T_Data, "Multimedia", "Prepare montage to play during ceremony");
							appendTask($T_Data, "Multimedia", "Prepare related song to be sang before ceremony starts");
						}
						
					}elseif($T_Rule["known_facts"][1]["ans_7"] == "Informal"){
						if($T_Rule["known_facts"][2]["ans_11"] == "Yes"){
							appendTask($T_Data, "Food", "Arrange VIP dining place");
							appendTask($T_Data, "Food", "Serve the VIPs for banquet");
						}
						if($T_Rule["known_facts"][2]["ans_12"] == "Yes"){
							appendTask($T_Data, "Logistics", "Order souvenir for VIP");
							appendTask($T_Data, "Special Task Force", "Wrap VIP souvenir accordingly");
						}
						if($T_Rule["known_facts"][2]["ans_13"] == "Yes"){
							appendTask($T_Data, "Logistics", "Order prizes for participants");
							appendTask($T_Data, "Planning and Execution", "Setup prize table and plan prize handing in the ceremony");
						}
						if($T_Rule["known_facts"][2]["ans_14"] == "Yes"){
							appendTask($T_Data, "Multimedia", "Prepare montage to play during ceremony");
						}
					}
					
					$T_Rule["data"] = $T_Data;
					$U_EventRule = json_encode($T_Rule, JSON_PRETTY_PRINT);
					
					//Query to register new event
					$insertQuery  = "UPDATE event SET evn_rule=? WHERE evn_id=?";
					if($stmt = $con->prepare($insertQuery)){
						$stmt->bind_param("ss", $U_EventRule, $EventID);
						$stmt->execute();
						$response["status"] = 0;
						$response["message"] = "Result generated";
						$response["data"]["evn_id"] = $EventID;
						$response["data"]["evn_name"] = $T_EVN_NAME;
						$response["data"]["result"] = $T_Data;
						$stmt->close();
					} else {
						$response["status"] = 1;
						$response["message"] = "Failed to save inferences";
					}
				}
				else
				{
					$response["status"] = 1;
					$response["message"] = "Invalid process level";
				}
			}
			else
			{
				$response["status"] = 1;
				$response["message"] = "Event not found";
			}
		}
		else
		{
			$response["status"] = 1;
			$response["message"] = "Event fetch unsuccessfull";
		}
	}
	else
	{
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