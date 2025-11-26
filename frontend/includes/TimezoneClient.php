<?php

/**
 * Classe TimezoneClient
 * 
 * Responsável pela comunicação com a API Backend Java.
 * Realiza requisições HTTP GET para obter informações de fuso horário.
 */
class TimezoneClient {

    /**
     * URL base da API Backend
     */
    private $apiBaseUrl;

    /**
     * Timeout para requisições HTTP (em segundos)
     */
    private $timeout;

    /**
     * Construtor da classe
     * 
     * @param string $apiBaseUrl URL base da API (ex: http://localhost:8080)
     * @param int $timeout Timeout em segundos (padrão: 5)
     */
    public function __construct($apiBaseUrl = 'http://localhost:8080', $timeout = 5) {
        $this->apiBaseUrl = rtrim($apiBaseUrl, '/');
        $this->timeout = $timeout;
    }

    /**
     * Obtém a hora atual de uma localidade
     * 
     * @param string $location Nome da cidade ou país
     * @return array Resposta decodificada da API ou array de erro
     */
    public function getTimezone($location) {
        // Validar entrada
        if (empty($location)) {
            return [
                'status' => 'ERROR',
                'message' => 'Localidade não pode estar vazia'
            ];
        }

        try {
            // Construir URL com parâmetro de query
            $url = $this->apiBaseUrl . '/api/timezone?location=' . urlencode($location);

            // Realizar requisição HTTP GET
            $response = $this->makeRequest($url);

            // Decodificar resposta JSON
            $data = json_decode($response, true);

            if (json_last_error() !== JSON_ERROR_NONE) {
                return [
                    'status' => 'ERROR',
                    'message' => 'Erro ao decodificar resposta da API: ' . json_last_error_msg()
                ];
            }

            return $data;

        } catch (Exception $e) {
            return [
                'status' => 'ERROR',
                'message' => 'Erro ao comunicar com a API: ' . $e->getMessage()
            ];
        }
    }

    /**
     * Verifica o status da API (health check)
     * 
     * @return bool true se a API está funcionando, false caso contrário
     */
    public function isApiHealthy() {
        try {
            $url = $this->apiBaseUrl . '/api/timezone/health';
            $response = $this->makeRequest($url);
            return !empty($response);
        } catch (Exception $e) {
            return false;
        }
    }

    /**
     * Obtém informações sobre a API
     * 
     * @return string Informações da API
     */
    public function getApiInfo() {
        try {
            $url = $this->apiBaseUrl . '/api/timezone/info';
            return $this->makeRequest($url);
        } catch (Exception $e) {
            return 'Indisponível';
        }
    }

    /**
     * Realiza uma requisição HTTP GET
     * 
     * @param string $url URL para requisição
     * @return string Resposta da requisição
     * @throws Exception Se houver erro na requisição
     */
    private function makeRequest($url) {
        // Usar cURL se disponível
        if (function_exists('curl_init')) {
            return $this->makeRequestWithCurl($url);
        }
        // Fallback para file_get_contents
        else if (ini_get('allow_url_fopen')) {
            return $this->makeRequestWithFileGetContents($url);
        }
        else {
            throw new Exception('Nenhum método de requisição HTTP disponível (cURL ou allow_url_fopen)');
        }
    }

    /**
     * Realiza requisição HTTP usando cURL
     * 
     * @param string $url URL para requisição
     * @return string Resposta da requisição
     * @throws Exception Se houver erro
     */
    private function makeRequestWithCurl($url) {
        $curl = curl_init();

        curl_setopt_array($curl, [
            CURLOPT_URL => $url,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_TIMEOUT => $this->timeout,
            CURLOPT_HTTPHEADER => [
                'Content-Type: application/json',
                'Accept: application/json'
            ]
        ]);

        $response = curl_exec($curl);
        $error = curl_error($curl);
        curl_close($curl);

        if ($error) {
            throw new Exception('Erro cURL: ' . $error);
        }

        return $response;
    }

    /**
     * Realiza requisição HTTP usando file_get_contents
     * 
     * @param string $url URL para requisição
     * @return string Resposta da requisição
     * @throws Exception Se houver erro
     */
    private function makeRequestWithFileGetContents($url) {
        $context = stream_context_create([
            'http' => [
                'method' => 'GET',
                'timeout' => $this->timeout,
                'header' => "Content-Type: application/json\r\nAccept: application/json\r\n"
            ]
        ]);

        $response = @file_get_contents($url, false, $context);

        if ($response === false) {
            throw new Exception('Erro ao conectar com a API');
        }

        return $response;
    }

    /**
     * Define a URL base da API
     * 
     * @param string $apiBaseUrl Nova URL base
     */
    public function setApiBaseUrl($apiBaseUrl) {
        $this->apiBaseUrl = rtrim($apiBaseUrl, '/');
    }

    /**
     * Obtém a URL base da API
     * 
     * @return string URL base atual
     */
    public function getApiBaseUrl() {
        return $this->apiBaseUrl;
    }
}
?>
