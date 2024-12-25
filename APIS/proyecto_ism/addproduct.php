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

// Conexión a la base de datos
$conn = new mysqli($host, $user, $password, $dbname);

// Verificar conexión
if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["error" => "Conexión fallida: " . $conn->connect_error]);
    exit();
}

// Obtener datos del cuerpo de la solicitud POST
$data = json_decode(file_get_contents("php://input"), true);

// Validar que el nombre del producto exista
if (!isset($data['nombre']) || empty($data['nombre'])) {
    http_response_code(400);
    echo json_encode(["error" => "El nombre del producto es obligatorio"]);
    exit();
}

$productName = $conn->real_escape_string($data['nombre']); 
$productPrice = isset($data['precio']) ? (float)$data['precio'] : 0;
$productImage = isset($data['imagen']) ? $conn->real_escape_string($data['imagen']) : 'sin_imagen.jpg';
$productCategory = isset($data['categoria']) ? (int)$data['categoria'] : 0;

// Validar que la categoría existe en la tabla categorias
$checkCategoryQuery = "SELECT id FROM categorias WHERE id = $productCategory";
$categoryResult = $conn->query($checkCategoryQuery);

if ($categoryResult->num_rows === 0) {
    http_response_code(400);
    echo json_encode(["error" => "La categoría especificada no existe"]);
    exit();
}

// Insertar el producto en la base de datos
$sql = "INSERT INTO productos (nombre, precio, imagen, categoria) VALUES ('$productName', $productPrice, '$productImage', '$productCategory')";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["success" => "Producto añadido correctamente", "query" => $sql]);
} else {
    http_response_code(500);
    echo json_encode(["error" => "Error al añadir producto: " . $conn->error, "query" => $sql]);
}

// Cerrar conexión
$conn->close();
?>
