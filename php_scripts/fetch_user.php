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

$sql = "SELECT *
FROM Users
WHERE username='". $user_name. "'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
	echo $row["phonenum"]. "~". $row["loggedin"]; 
} 
else
{
	echo "0";
}

$conn->close();
?>