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
    $sql = "SELECT v.rating AS rating, v.descripcion, v.fecha, u.email AS email_usuario, p.nombre AS nombre_producto
            FROM valoraciones v 
            JOIN usuarios u 
            ON v.id_usuario = u.id
            JOIN productos p
            ON v.id_producto = p.id";
    $result = $conn->query($sql);

    header("Content-type: text/xml");
    echo '<?xml version="1.0" encoding="UTF-8"?>';
    echo '<valoraciones>';

    // Verifica si hay resultados y los muestra
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            echo '<valoracion>';
                echo '<rating>' . $row["rating"] . '</rating>';
                echo '<descripcion>' . $row["descripcion"] . '</descripcion>';
                echo '<fecha>' . $row["fecha"] . '</fecha>';
                echo '<email_usuario>' . $row["email_usuario"] . '</email_usuario>';
                echo '<nombre_producto>' . $row["nombre_producto"] . '</nombre_producto>';
            echo '</valoracion>';
        }
    } else {
        echo "0 resultados";
    }

    echo '</valoraciones>';

    $conn->close();
?>
