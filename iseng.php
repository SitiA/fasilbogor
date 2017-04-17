<?php

$host = 'localhost';
$user = 'root';
$pass = '';
$dbname = 'fasilitasumum';

$con =mysqli_connect($host, $user, $pass, $dbname) or die ('Unable to connect');

if(mysqli_connect_error($con))
{
	echo "failed to connect to database".mysqli_connect_error();
}
$response = array();
$response["detail"] = array();

$sql = "SELECT * FROM detail inner join info on info.id_info = detail.id_info inner join kategori on kategori.id_kategori = detail.id_kategori";

$result=mysqli_query($con,$sql);
if ($result) {
	
	while ($row=mysqli_fetch_array($result)){
		$tmp = array();
        $tmp["id_detail"] = $row["id_detail"];
        $tmp["nama_detail"] = $row["nama_detail"];
        $tmp["alamat_detail"] = $row["alamat_detail"];
        $tmp["jam_opr_detail"] = $row["jam_opr_detail"];
        $tmp["lat_detail"] = $row["lat_detail"];
        $tmp["long_detail"] = $row["long_detail"];
        $tmp["no_telepon"] = $row["no_telepon"];
        $tmp["id_info"] = $row["id_info"];
        $tmp["nama_info"] = $row["nama_info"];
        $tmp["id_kategori"] = $row["id_kategori"];
        $tmp["nama_kategori"] = $row["nama_kategori"];
 
        array_push($response["detail"], $tmp);
	}
	    header('Content-Type: application/json');
     
   
    print json_encode($response);;
}
mysqli_close($con);
?>
