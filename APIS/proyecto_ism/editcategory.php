<?php
// Permitir acceso desde cualquier origen
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, PUT, OPTIONS, DELETE");
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

// Obtener datos del cuerpo de la solicitud PUT
$data = json_decode(file_get_contents("php://input"), true);

// Validar que el nombre y la descripción existan
if (!isset($data['id']) || empty($data['id']) || !isset($data['nombre']) || empty($data['nombre']) || !isset($data['descripcion']) || empty($data['descripcion'])) {
    http_response_code(400);
    echo json_encode(["error" => "El ID, nombre y descripción de la categoría son obligatorios"]);
    exit();
}

$id = (int)$data['id'];
$categoryName = $conn->real_escape_string($data['nombre']); 
$categoryDescription = $conn->real_escape_string($data['descripcion']); 

// Actualizar la categoría en la base de datos
$sql = "UPDATE categorias SET nombre = '$categoryName', descripcion = '$categoryDescription' WHERE id = $id";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["success" => "Categoría actualizada correctamente"]);
} else {
    http_response_code(500);
    echo json_encode(["error" => "Error al actualizar categoría: " . $conn->error]);
}

// Cerrar conexión
$conn->close();
?>
