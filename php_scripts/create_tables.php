<?php
//This code is based off the follow tutorial: http://www.w3schools.com/php/php_mysql_create_table.asp

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

// Create user table
$sql = "CREATE TABLE Users (
_id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(30) NOT NULL,
password VARCHAR(30) NOT NULL,
email VARCHAR(30) NOT NULL,
phonenum VARCHAR(30) NOT NULL,
loggedin VARCHAR(5) NOT NULL
)";

if($conn->query($sql) === TRUE){
	echo "Table Users created successfully" . "<br>";
}
else {
	echo "Error creating table: "  . $conn->error . "<br>";
}

// Add an Admin account to the users table
$sql = "INSERT INTO Users (username, password, email, phonenum, loggedin) 
VALUES ('Admin', 'Admin', 'brunecz1@umbc.edu', '4108293586', 'FALSE')";
if($conn->query($sql) === TRUE){
	echo "Admin account created successfully" . "<br>";
} else {
	echo "Error: " . $sql . "<br>" . $conn->error . "<br>";
}

// Create posts table
$sql = "CREATE TABLE Posts (
_id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(30) NOT NULL,
location VARCHAR(30) NOT NULL,
floor VARCHAR(30) NOT NULL,
numseats VARCHAR(30) NOT NULL,
description VARCHAR(300) NOT NULL,
windowseat VARCHAR(5) NOT NULL,
poweroutlet VARCHAR(5) NOT NULL,
scanner VARCHAR(5) NOT NULL,
whiteboard VARCHAR(5) NOT NULL,
maccomputers VARCHAR(5) NOT NULL,
rockingchair VARCHAR(5) NOT NULL,
image BLOB,
reservedto VARCHAR(30)
)";

if($conn->query($sql) === TRUE){
	echo "Tables Posts created successfully";
}
else {
	echo "Error creating table: " . $conn->error;
}

$conn->close();
?>