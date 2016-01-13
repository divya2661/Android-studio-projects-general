<?php

require('connect.php');

$Name = $_POST['name'];
$Role = $_POST['role'];
$Email = $_POST['email'];
$Passwd = $_POST['password'];

$query = "INSERT INTO people (Name,Role,Email,Password) VALUES ('$Name','$Role','$Email','$Passwd')";

if(!mysql_query($query)){
	echo "Failed to process query".mysql_error();
}
else
{
	echo "Successfully regestered."
}
mysql_close($con)
?>

<!DOCTYPE html>
<html>
<head>
   <title>login</title>
</head>
<body>
<form action="login.php" method="post">
   <input type="text" name="name" placeholder="Nmail">
   <input type="text" name="role" placeholder="Role">
   <input type="text" name="Email" placeholder="email">
   <input type="password" name="password" placeholder="password">
   <button type = "submit" value="submit">Submit</button>
</form>
</body>
</html>