<?php
//This code is based off the follow tutorial: http://www.w3schools.com/php/php_mysql_create.asp

$servername = "mpss.csce.uark.edu";
$username = "crowdsource";
$password = "crowdsource123~";

//Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

//Check connection
if($conn->connect_error)
{
	die("Connection failed: " . $conn->connect_error);
}

//Create the database
$sql = "CREATE DATABASE SpotSwapDB";
if($conn->query($sql) === TRUE){
	echo "Database created successfully";
}
else {
	echo "Error creating database: " . $conn->error;
}

$conn->close();
?>