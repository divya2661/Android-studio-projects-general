<?php

$host = "mysql7.000webhost.com";
$user = "a3028503_divya";
$passwd = "love14578";
$database = "a3028503_College";

$con=mysql_connect($host,$user,$passwd);

if (mysql_errno($con))
{
   echo "Failed to connect to MySQL: " . mysql_error();
}
if(!mysql_select_db($database,$con)){
   echo "could not select database: ".mysql_error($con);
}

?>