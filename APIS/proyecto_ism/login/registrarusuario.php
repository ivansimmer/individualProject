<?php
    header("Content-Type: text/xml");

    // Conexión a la base de datos
    $conn = new mysqli("localhost", "root", "", "proyecto_ism");

    // Verificar conexión
    if ($conn->connect_error) {
        die("<respuesta><estado>error</estado><mensaje>No se pudo conectar a la base de datos.</mensaje></respuesta>");
    }

    // Obtener los datos enviados
    $usuario = $_POST['usuario'];
    $contrasena = $_POST['contrasena'];
    $email = $_POST['email'];
    $telefono = $_POST['telefono'];
    $fecha_registro = $_POST['fecha_registro'];

    // Insertar usuario en la base de datos
    $sql = "INSERT INTO usuarios (usuario, contrasena, email, telefono, fecha_registro) VALUES ('$usuario', '$contrasena', '$email', '$telefono', FROM_UNIXTIME($fecha_registro))";

    if ($conn->query($sql) === TRUE) {
        echo "<respuesta><estado>ok</estado><mensaje>Usuario registrado exitosamente.</mensaje></respuesta>";
    } else {
        echo "<respuesta><estado>error</estado><mensaje>Error al registrar el usuario: " . $conn->error . "</mensaje></respuesta>";
    }

    $conn->close();
?>
