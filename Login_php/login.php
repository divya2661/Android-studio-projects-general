<?php

reqire('connect.php');

$email = $_POST['email'];
$password = $_POST['password'];
$query = "SELECT Role,Name FROM poeple where Email='$email' and Password='$password'";

$result = mysql_query($query);

$row = mysql_fetch_array($result);

echo "oh yeah".$row[0];
if($row[1]){
echo $row[1];
}

mysql_close($con);

?>

<!DOCTYPE html>
<html>
<head>
   <title>login</title>
</head>
<body>
<form action="login.php" method="post">
   <input type="text" name="email" placeholder="Email">
   <input type="password" name="password" placeholder="password">
   <button type = "submit" value="submit">Submit</button>
</form>
</body>
</html>
