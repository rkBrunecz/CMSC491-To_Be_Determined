<?php
//This code is based off the follow tutorial: http://www.w3schools.com/php/php_mysql_select.asp

$servername = "mpss.csce.uark.edu";
$username = "crowdsource";
$password = "crowdsource123~";
$dbname = "SpotSwapDB";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM Posts";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo "ID: " . $row["_id"]. " - Username: " . $row["username"]. " Location: " . $row["location"]. " Floor: ". $row["floor"]. " # of Seats: ". $row["numseats"]. " Description: ". $row["description"].
		" Window Seat: ". $row["windowseat"]. " Power outlet: ". $row["poweroutlet"]. " Scanner: ". $row["scanner"]. " Whiteboard: ". $row["whiteboard"]. " Mac Computer: ". $row["maccomputers"]. " Rocking Chair: ".
		$row["rockingchair"]. " Reserved to: ". $row["reservedto"]. "<br>";
    }
} else {
    echo "0 results";
}

$conn->close();
?> 