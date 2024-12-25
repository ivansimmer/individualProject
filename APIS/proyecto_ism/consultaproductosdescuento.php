<?php
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

    // Consulta para obtener todos los productos
    $sql = "SELECT p.nombre AS nombre_producto, p.precio, p.imagen, p.categoria, c.nombre AS nombre_categoria
            FROM productos p 
            INNER JOIN categorias c 
            ON p.categoria = c.id
            WHERE p.descuento IS NOT NULL";
    $result = $conn->query($sql);

    header("Content-type: text/xml");
    echo '<?xml version="1.0" encoding="UTF-8"?>';
    echo '<productos>';

    // Verifica si hay resultados y los muestra
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            echo '<producto>';
                echo '<nombre_producto>' . $row["nombre_producto"] . '</nombre_producto>';
                echo '<precio>' . $row["precio"] . '</precio>';
                echo '<nombre_categoria>' . $row["nombre_categoria"] . '</nombre_categoria>';
                echo '<imagen>' . $row["imagen"] . '</imagen>';
            echo '</producto>';
        }
    } else {
        echo "0 resultados";
    }

    echo '</productos>';

    $conn->close();
?>
