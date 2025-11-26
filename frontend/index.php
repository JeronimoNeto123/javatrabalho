<?php

/**
 * HoraTime Frontend - Página Principal
 * 
 * Interface de usuário para pesquisa de fuso horário.
 */

require_once __DIR__ . '/includes/config.php';
require_once __DIR__ . '/includes/TimezoneClient.php';

// Criar cliente para verificar status da API
$client = new TimezoneClient(API_BASE_URL, API_TIMEOUT);
$apiHealthy = $client->isApiHealthy();

?>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="<?php echo APP_DESCRIPTION; ?>">
    <meta name="author" content="">
    <title><?php echo APP_NAME; ?> - Sistema de Fuso Horário</title>
    
    <!-- CSS -->
    <link rel="stylesheet" href="assets/css/style.css">
    
    <!-- Favicon -->
    <link rel="icon" type="image/svg+xml" href="data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'><text y='75' font-size='75' fill='%23667eea'>⏰</text></svg>">
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1>⏰ <?php echo APP_NAME; ?></h1>
            <p><?php echo APP_DESCRIPTION; ?></p>
            
            <!-- Status da API -->
            <div class="api-status <?php echo $apiHealthy ? 'online' : 'offline'; ?>">
                <span><?php echo $apiHealthy ? '✓ API Online' : '✗ API Offline'; ?></span>
            </div>
        </div>

        <!-- Seção de Pesquisa -->
        <div class="search-section">
            <label for="locationInput">Pesquise uma Cidade ou País:</label>
            <div class="search-container">
                <input 
                    type="text" 
                    id="locationInput" 
                    placeholder="Ex: São Paulo, Paris, Tóquio..." 
                    autocomplete="off"
                    <?php echo !$apiHealthy ? 'disabled' : ''; ?>
                >
                <button 
                    id="searchButton" 
                    type="button"
                    <?php echo !$apiHealthy ? 'disabled' : ''; ?>
                >
                    Buscar
                </button>
            </div>
        </div>

        <!-- Indicador de Carregamento -->
        <div id="loading" class="loading">
            <div class="spinner"></div>
            <p style="margin-top: 15px; color: #666;">Buscando informações...</p>
        </div>

        <!-- Seção de Resultado -->
        <div id="resultSection" class="result-section">
            <div class="result-item">
                <div class="result-label">Localidade</div>
                <div class="result-value location" id="resultLocation">-</div>
            </div>

            <div class="result-item">
                <div class="result-label">Hora Atual</div>
                <div class="result-value time" id="resultTime">--:--:--</div>
            </div>

            <div class="result-item">
                <div class="result-label">Fuso Horário</div>
                <div class="result-value" id="resultTimezone">-</div>
            </div>

            <div class="result-item">
                <div class="result-label">Offset UTC</div>
                <div class="result-value" id="resultOffset">-</div>
            </div>
        </div>

        <!-- Mensagem de Erro -->
        <div id="errorMessage" class="error-message"></div>

        <!-- Mensagem de Sucesso -->
        <div id="successMessage" class="success-message"></div>

        <!-- Informações Úteis -->
        <div style="margin-top: 30px; padding: 20px; background: #f0f4ff; border-radius: 8px; border-left: 4px solid #667eea;">
            <h3 style="color: #667eea; margin-bottom: 10px; font-size: 1.1em;">💡 Dicas:</h3>
            <ul style="color: #555; font-size: 0.95em; line-height: 1.6; margin-left: 20px;">
                <li>Digite o nome completo da cidade ou país</li>
                <li>Acentuação é permitida (ex: São Paulo, Brasília)</li>
                <li>A busca é case-insensitive (maiúsculas/minúsculas)</li>
                <li>Exemplos: Paris, Tóquio, Nova York, Sydney, Dubai</li>
            </ul>
        </div>

        <!-- Footer -->
        <div class="footer">
            <p><?php echo APP_NAME; ?> v<?php echo APP_VERSION; ?> | Desenvolvido por <strong>Jeronimo Neto</strong></p>
            <p style="margin-top: 10px; font-size: 0.85em;">
                <a href="#" onclick="alert('Versão: ' + '<?php echo APP_VERSION; ?>'); return false;">Sobre</a> | 
                <a href="https://github.com" target="_blank">GitHub</a> | 
                <a href="#" onclick="alert('Suporte: contato@horatime.com'); return false;">Suporte</a>
            </p>
        </div>
    </div>

    <!-- JavaScript -->
    <script src="assets/js/app.js"></script>

    <!-- Script para verificar status da API -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const apiStatus = document.querySelector('.api-status');
            
            if (apiStatus.classList.contains('offline')) {
                const locationInput = document.getElementById('locationInput');
                const searchButton = document.getElementById('searchButton');
                
                // Mostrar mensagem de erro
                const errorDiv = document.getElementById('errorMessage');
                errorDiv.textContent = 'API Backend está offline. Verifique se o servidor Java está rodando em http://localhost:8080';
                errorDiv.classList.add('show');
            }
        });
    </script>
</body>
</html>
