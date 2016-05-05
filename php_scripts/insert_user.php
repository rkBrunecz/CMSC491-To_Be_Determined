<?php
$user_name = $_REQUEST["username"];
$user_pass = $_REQUEST["password"];
$email = $_REQUEST["email"];
$phonenum = $_REQUEST["phonenum"];

$servername = "mpss.csce.uark.edu";
$username = "crowdsource";
$password = "crowdsource123~";
$dbname = "SpotSwapDB";

//Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

//Check connection
if($conn->connect_error)
{
	die("Connection failed: " . $conn->connect_error);
}

// Add a user account to the users table
$sql = "INSERT INTO Users (username, password, email, phonenum) 
VALUES ('". $user_name. "', '". $user_pass. "', '". $email. "', '". $phonenum. "')";

if($conn->query($sql) === TRUE){
	echo "Success" . "<br>";
} else {
	echo "Error: " . $sql . "<br>" . $conn->error . "<br>";
}

$conn->close();
?>