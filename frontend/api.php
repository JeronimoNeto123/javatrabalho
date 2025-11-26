<?php

/**
 * API Proxy - HoraTime Frontend
 * 
 * Este arquivo atua como intermediário entre o frontend JavaScript e a API Backend Java.
 * Realiza validações de segurança e retorna respostas em JSON.
 */

header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');

// Incluir configurações
require_once __DIR__ . '/includes/config.php';
require_once __DIR__ . '/includes/TimezoneClient.php';

// Tratar requisições OPTIONS (CORS preflight)
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit;
}

// Verificar se é uma requisição GET
if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    http_response_code(405);
    echo json_encode([
        'status' => 'ERROR',
        'message' => 'Método não permitido. Use GET.'
    ]);
    exit;
}

// Obter parâmetro de localidade
$location = isset($_GET['location']) ? $_GET['location'] : '';

// Sanitizar entrada
$location = sanitizeInput($location);

// Validar localidade
if (!isValidLocation($location)) {
    http_response_code(400);
    echo json_encode([
        'status' => 'ERROR',
        'message' => 'Localidade inválida. Insira uma cidade ou país válido.'
    ]);
    logMessage("Localidade inválida fornecida: {$location}", 'WARNING');
    exit;
}

try {
    // Criar cliente da API
    $client = new TimezoneClient(API_BASE_URL, API_TIMEOUT);

    // Verificar se a API está disponível
    if (!$client->isApiHealthy()) {
        http_response_code(503);
        echo json_encode([
            'status' => 'ERROR',
            'message' => 'API Backend indisponível. Tente novamente mais tarde.'
        ]);
        logMessage("API Backend indisponível", 'ERROR');
        exit;
    }

    // Obter dados de fuso horário
    $response = $client->getTimezone($location);

    // Registrar requisição bem-sucedida
    logMessage("Requisição bem-sucedida para: {$location}", 'INFO');

    // Retornar resposta
    if (isset($response['status']) && $response['status'] === 'SUCCESS') {
        http_response_code(200);
    } else {
        http_response_code(404);
    }

    echo json_encode($response);

} catch (Exception $e) {
    http_response_code(500);
    echo json_encode([
        'status' => 'ERROR',
        'message' => 'Erro ao processar requisição: ' . $e->getMessage()
    ]);
    logMessage("Exceção capturada: " . $e->getMessage(), 'ERROR');
}

?>
