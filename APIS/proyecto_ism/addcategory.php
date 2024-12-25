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

// Validar que el nombre y la descripción existan
if (!isset($data['nombre']) || empty($data['nombre']) || !isset($data['descripcion']) || empty($data['descripcion'])) {
    http_response_code(400);
    echo json_encode(["error" => "El nombre y la descripción de la categoría son obligatorios"]);
    exit();
}

$categoryName = $conn->real_escape_string($data['nombre']); 
$categoryDescription = $conn->real_escape_string($data['descripcion']); 

// Insertar la categoria en la base de datos
$sql = "INSERT INTO categorias (nombre, descripcion) VALUES ('$categoryName', '$categoryDescription')";

if ($conn->query($sql) === TRUE) {
    echo json_encode(["success" => "Categoría añadida correctamente", "query" => $sql]);
} else {
    http_response_code(500);
    echo json_encode(["error" => "Error al añadir categoría: " . $conn->error, "query" => $sql]);
}

// Cerrar conexión
$conn->close();
?>
