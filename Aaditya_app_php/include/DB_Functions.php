<?php

class DB_Functions{
	private $conn;

	//constructor

	function __construct(){
			require_once 'DB_Connect.php';

			$db = new DB_Connect();
			$this->conn = $db->connect();
	}

	// destructor
    function __destruct() {
         
    }

    //store values in database
    public function storeUser($first_name,$last_name,$email,$password,$contact_no,$facebook_link,$twitter_link){

    	//echo "\n store user";
    	$uuid = uniqid('',true);
    	//encrypting the password
    	$hash = $this->hashSSA($password); 
    	$encrypted_pass = $hash["encrypted"];
    	$salt = $hash["salt"];

    	$stmt = $this->conn->prepare("INSERT INTO users(unique_id, first_name,last_name,contact_no,email,facebook_link,twitter_link ,encrypted_password, salt, created_at) VALUES(?,?,?,?,?,?,?,?,?, NOW())");
    	$stmt->bind_param("sssssssss", $uuid, $first_name,$last_name,$contact_no, $email,$facebook_link,$twitter_link,$encrypted_pass, $salt);
        $result = $stmt->execute();
	    //$stmt->close();

	    if($result){

        	$stmt = $this->conn->prepare("SELECT unique_id,first_name,last_name,contact_no,email,facebook_link,twitter_link,encrypted_password, salt FROM users WHERE email = ?");
        	$stmt->bind_param("s",$email);
        	$stmt->execute();
        	$stmt->bind_result($user["unique_id"],$user["first_name"],$user["last_name"],$user["contact_no"],$user["email"],$user["facebook_link"],$user["twitter_link"],$user["encrypted_password"],$user["salt"]);
            $stmt->fetch();
        	return $user;
        }
        else{
        	echo $stmt->error;
        	return false;
        }
        $stmt->close;
    }

    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
 			
 			//echo "\n get user by email or pass";
        $stmt = $this->conn->prepare("SELECT unique_id,first_name,last_name,contact_no,email,facebook_link,twitter_link,encrypted_password, salt FROM users WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        if ($stmt->execute()) {

            $stmt->bind_result($user["unique_id"],$user["first_name"],$user["last_name"],$user["contact_no"],$user["email"],$user["facebook_link"],$user["twitter_link"],$user["encrypted_password"],$user["salt"]);
            $stmt->fetch();
            $hashed_password = $this->checkhashSHA($user["salt"],$password);
            if($hashed_password==$user["encrypted_password"]){

	           	$stmt->close();
	            return $user;
            }
            else
            {
            	$stmt->close();
	            return NULL;
            }

        } else {
            return NULL;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
    	//echo "\n is user exist ran";
        $stmt = $this->conn->prepare("SELECT email from users WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }

    //to encrypt password
    public function hashSSA($password){
    	$salt = sha1(rand());
    	$salt = substr($salt,0,10);
    	$encrypted = base64_encode(sha1($password.$salt,true));
    	$hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    public function checkhashSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true));
        return $hash;
    }
}


?>
