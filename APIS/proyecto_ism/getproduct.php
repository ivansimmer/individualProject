<?php
// Habilitar CORS
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, PUT, OPTIONS, DELETE");
header("Access-Control-Allow-Headers: Content-Type, X-Requested-With, Authorization");

if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {
    http_response_code(200);
    exit();
}

header("Content-Type: application/json");

$host = "localhost";
$user = "root";
$password = "";
$dbname = "proyecto_ism";

$conn = new mysqli($host, $user, $password, $dbname);

// Verificar conexión
if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["error" => "Conexión fallida: " . $conn->connect_error]);
    exit();
}

// Verificar que se haya pasado un ID
if (!isset($_GET['id']) || empty($_GET['id'])) {
    http_response_code(400);
    echo json_encode(["error" => "El ID del producto es obligatorio"]);
    exit();
}

$productId = (int)$_GET['id'];

// Consultar el producto en la base de datos
$sql = "SELECT id, nombre, precio, imagen, categoria FROM productos WHERE id = $productId";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $product = $result->fetch_assoc();
    echo json_encode($product); // Devolver el producto como JSON
} else {
    http_response_code(404);
    echo json_encode(["error" => "Producto no encontrado"]);
}

$conn->close();
?>
