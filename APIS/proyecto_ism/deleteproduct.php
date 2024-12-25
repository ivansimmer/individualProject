<?php
// Permitir acceso desde cualquier origen
header("Access-Control-Allow-Origin: *");

// Permitir métodos específicos (POST, GET, DELETE, etc.)
header("Access-Control-Allow-Methods: POST, GET, OPTIONS, DELETE");

// Permitir ciertos encabezados personalizados
header("Access-Control-Allow-Headers: Content-Type, X-Requested-With, Authorization");

header('Content-Type: application/json');

// Si es una preflight request (OPTIONS), responde y finaliza
if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {
    http_response_code(200);
    exit();
}

// Configuración de la conexión a la base de datos
$host = "localhost";
$user = "root";          // Tu usuario de base de datos
$password = "";          // Tu contraseña de base de datos
$dbname = "proyecto_ism"; // Nombre de tu base de datos

header('Content-Type: application/json');

// Conexión a la base de datos
$conn = new mysqli($host, $user, $password, $dbname);

// Verificar conexión
if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["error" => "Conexión fallida: " . $conn->connect_error]);
    exit();
}

// Obtener ID del producto desde la URL
if (!isset($_GET['id']) || empty($_GET['id'])) {
    http_response_code(400);
    echo json_encode(["error" => "ID del producto es obligatorio"]);
    exit();
}

$productID = intval($_GET['id']); // Asegurar que es un número entero

// Eliminar el producto de la base de datos
$sql = "DELETE FROM productos WHERE id = $productID";

if ($conn->query($sql) === TRUE) {
    if ($conn->affected_rows > 0) {
        echo json_encode(["success" => "Producto eliminado correctamente"]);
    } else {
        http_response_code(404);
        echo json_encode(["error" => "Producto no encontrado"]);
    }
} else {
    http_response_code(500);
    echo json_encode(["error" => "Error al eliminar producto: " . $conn->error]);
}

// Cerrar conexión
$conn->close();
?>
