<?php
$user_name = $_REQUEST["username"];
$location = $_REQUEST["location"];
$floor = $_REQUEST["floor"];
$numseats = $_REQUEST["numseats"];
$description = $_REQUEST["description"];
$windowseat = $_REQUEST["windowseat"];
$poweroutlet = $_REQUEST["poweroutlet"];
$scanner = $_REQUEST["scanner"];
$whiteboard = $_REQUEST["whiteboard"];
$maccomputers = $_REQUEST["maccomputers"];
$rockingchair = $_REQUEST["rockingchair"];
$image = $_REQUEST["image"];

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

// Add a post into the database
$sql = "INSERT INTO Posts (username, location, floor, numseats, description, windowseat, poweroutlet, scanner, whiteboard, maccomputers, rockingchair, image, reservedto) 
VALUES ('". $user_name. "', '". $location. "', '". $floor. "', '". $numseats. "', '". $description. "', '". $windowseat. "', '". $poweroutlet. "', '". $scanner. "', '". $whiteboard. "', '". $maccomputers. "', '". $rockingchair. "', ". $image. ", '')";

if($conn->query($sql) === TRUE){
	echo "Success" . "<br>";
} else {
	echo "Error: " . $sql . "<br>" . $conn->error . "<br>";
}

$conn->close();
?>