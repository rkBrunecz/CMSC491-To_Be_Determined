<?php
$user_name = $_REQUEST["username"];
$location = $_REQUEST["location"];

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

$sql = "SELECT username, location
FROM Posts
WHERE username!='". $user_name. "' and location='". $location. "' and reservedto=''";
$result = $conn->query($sql);

echo $result->num_rows; 


$conn->close();
?>