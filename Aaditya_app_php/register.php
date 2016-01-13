<?php
	
	require_once 'include/DB_Functions.php';
	$db = new DB_Functions();

	$response = array("error"=>FALSE);

	if(isset($_POST['first_name']) && isset($_POST['last_name'])&& isset($_POST['email']) && isset($_POST['password']) && isset($_POST['contact_no'])){

		
		$first_name = $_POST['first_name'];
		$last_name = $_POST['last_name'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		$contact_no = $_POST['contact_no'];
		$facebook_link = $_POST['facebook_link'];
		$twitter_link = $_POST['twitter_link'];


		
		//check if user is already there

		if($db->isUserExisted($email)){
			$response["error"] = TRUE;
			$response["error_msg"] = "User already exists with email ".$email;
			echo json_encode($response);
		}
		else{

			
			$user = $db->storeUser($first_name,$last_name,$email,$password,$contact_no,$facebook_link,$twitter_link);
			echo "\n".$user["name"];
			if($user){
				$response["error"] = FALSE;
				$response["uid"] = $user["unique_id"];
				$response["user"]["First_name"] = $user["first_name"];
		        $response["user"]["Last_name"] = $user["last_name"];
		        $response["user"]["Contact"] = $user["contact_no"];
		        $response["user"]["email"] = $user["email"];
		        $response["user"]["Fadebook_link"] = $user["facebook_link"];
		        $response["user"]["Twitter_link"] = $user["twitter_link"];
		        $response["user"]["created_at"] = $user["created_at"];
		        $response["user"]["updated_at"] = $user["updated_at"];
				echo json_encode($response);
			}
			else{
				$response["error"] = TRUE;
				$response["error_msg"] = "Unknown error occurred in registration";
			}
		}

	}
	else{
		$response["error"] = TRUE;
		$response["error_msg"] = "Required parameters not available";
		echo json_encode($response);
	}
 
?>