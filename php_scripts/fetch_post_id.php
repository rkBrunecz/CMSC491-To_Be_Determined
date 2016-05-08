<?php
$id = $_REQUEST["id"];

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
FROM Posts
WHERE _id='". $id. "'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
	$row = $result->fetch_assoc();
	
	echo $row["_id"]. "~". $row["username"]. "~". $row["location"]. "~". $row["floor"]. "~". $row["numseats"]. "~". $row["description"]. "~". $row["windowseat"]. "~".
	$row["poweroutlet"]. "~". $row["pc"]. "~". $row["whiteboard"]. "~". $row["maccomputers"]. "~". $row["rockingchair"]. "~". $row["silence"]. "<br>"; 
} 
else
{
	echo "0";
}

$conn->close();
?>