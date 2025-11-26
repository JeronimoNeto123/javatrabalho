<?php

/**
 * Arquivo de Configuração - HoraTime Frontend
 * 
 * Contém configurações gerais da aplicação PHP.
 */

// Configurações de Encoding
header('Content-Type: text/html; charset=utf-8');
mb_internal_encoding('UTF-8');

// Configurações da API Backend
define('API_BASE_URL', 'http://localhost:8080');
define('API_TIMEOUT', 5);

// Configurações da Aplicação
define('APP_NAME', 'HoraTime');
define('APP_VERSION', '1.0.0');
define('APP_DESCRIPTION', 'Sistema de Consulta de Fuso Horário');

// Configurações de Segurança
define('ENABLE_CACHE', false);
define('CACHE_DURATION', 3600); // 1 hora

// Configurações de Logging
define('ENABLE_LOGGING', true);
define('LOG_FILE', __DIR__ . '/../logs/horatime.log');

// Configurações de Erro
error_reporting(E_ALL);
ini_set('display_errors', 0);
ini_set('log_errors', 1);

// Criar diretório de logs se não existir
if (ENABLE_LOGGING && !is_dir(__DIR__ . '/../logs')) {
    mkdir(__DIR__ . '/../logs', 0755, true);
}

/**
 * Função para logging
 * 
 * @param string $message Mensagem a ser registrada
 * @param string $level Nível de log (INFO, WARNING, ERROR)
 */
function logMessage($message, $level = 'INFO') {
    if (!ENABLE_LOGGING) {
        return;
    }

    $timestamp = date('Y-m-d H:i:s');
    $logMessage = "[{$timestamp}] [{$level}] {$message}\n";

    if (file_exists(dirname(LOG_FILE))) {
        file_put_contents(LOG_FILE, $logMessage, FILE_APPEND);
    }
}

/**
 * Função para sanitizar entrada do usuário
 * 
 * @param string $input Entrada a ser sanitizada
 * @return string Entrada sanitizada
 */
function sanitizeInput($input) {
    return htmlspecialchars(trim($input), ENT_QUOTES, 'UTF-8');
}

/**
 * Função para validar localidade
 * 
 * @param string $location Localidade a ser validada
 * @return bool true se válida, false caso contrário
 */
function isValidLocation($location) {
    // Verificar se não está vazio
    if (empty($location)) {
        return false;
    }

    // Verificar comprimento (mínimo 2, máximo 100 caracteres)
    if (strlen($location) < 2 || strlen($location) > 100) {
        return false;
    }

    // Verificar se contém apenas letras, espaços, hífens e acentos
    if (!preg_match('/^[a-záéíóúâêôãõç\s\-]+$/i', $location)) {
        return false;
    }

    return true;
}

?>
