<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: text/xml; charset=UTF-8");
    
    $host = "localhost";
    $username = "root"; // Usuario predeterminado de XAMPP
    $password = "";     // Contraseña predeterminada de XAMPP (vacia)
    $dbname = "proyecto_ism"; // Cambia este nombre por el nombre de tu base de datos

    // Conexión a la base de datos
    $conn = new mysqli($host, $username, $password, $dbname);

    // Verifica la conexión
    if ($conn->connect_error) {
        die("Conexión fallida: " . $conn->connect_error);
    }

    // Consulta para obtener todas las categorias
    $sql = "SELECT id, nombre AS nombre_categoria, descripcion
            FROM categorias";
    $result = $conn->query($sql);

    header("Content-type: text/xml");
    echo '<?xml version="1.0" encoding="UTF-8"?>';
    echo '<categorias>';

    // Verifica si hay resultados y los muestra
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            echo '<categoria>';
                echo '<id>' . $row["id"] . '</id>';
                echo '<nombre_categoria>' . htmlspecialchars($row["nombre_categoria"]) . '</nombre_categoria>';
                echo '<descripcion>' . htmlspecialchars($row["descripcion"]) . '</descripcion>';
            echo '</categoria>';
        }
    }
    echo '</categorias>';

    $conn->close();
?>
