<?php
$user_name = $_REQUEST["username"];

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

$sql = "UPDATE Users
SET loggedin='FALSE'
WHERE username='". $user_name. "'";
$conn->query($sql);

$conn->close();
?>