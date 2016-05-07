<?php
$user_name = $_REQUEST["username"];
$user_pass = $_REQUEST["password"];

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

$sql = "SELECT username, password 
FROM Users
WHERE username='". $user_name. "' and password='". $user_pass. "' and loggedin='FALSE'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
	$sql = "UPDATE Users
	SET loggedin='TRUE'
	WHERE username='". $user_name. "' and password='". $user_pass. "'";
	$conn->query($sql);
	
    echo $result->num_rows; 
} else {
	$sql = "SELECT loggedin 
	FROM Users
	WHERE username='". $user_name. "' and password='". $user_pass. "'";
	$result = $conn->query($sql);
	
	$row = $result->fetch_assoc();
	if($row["loggedin"] == "TRUE")
		echo "-1";
	else
		echo "0";
}

$conn->close();
?>