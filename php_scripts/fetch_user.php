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
WHERE username='". $user_name. "' and password='". $user_pass. "'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo $result->num_rows; 
} else {
    echo "0";
}

$conn->close();
?>