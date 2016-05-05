<?php
$user_name = $_REQUEST["username"];
$user_email = $_REQUEST["email"];
$phone_num = $_REQUEST["phonenum"];

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


$sql = "SELECT username
FROM Users
WHERE username='". $user_name. "'";
$result = $conn->query($sql);

$num_name_match = $result->num_rows;

$sql = "SELECT email
FROM Users
WHERE email='". $user_email. "'";
$result = $conn->query($sql);

$num_email_match = $result->num_rows;

$sql = "SELECT phonenum
FROM Users
WHERE phonenum='". $phone_num. "'";
$result = $conn->query($sql);

$num_phone_match = $result->num_rows;

echo $num_name_match. "~". $num_email_match. "~". $num_phone_match;

$conn->close();
?>