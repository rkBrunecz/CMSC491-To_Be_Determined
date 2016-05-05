<?php
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

//Drop user table
$sql = "DROP TABLE Users";
if($conn->query($sql) === TRUE){
	echo "Table Users dropped successfully" . "<br>";
}
else {
	echo "Error dropping table: "  . $conn->error . "<br>";
}

//Drop posts table
$sql = "DROP TABLE Posts";
if($conn->query($sql) === TRUE){
	echo "Table Posts dropped successfully" . "<br>";
}
else {
	echo "Error dropping table: "  . $conn->error . "<br>";
}

$conn->close();
?>
