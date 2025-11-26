/**
 * HoraTime Frontend - JavaScript
 * 
 * Gerencia a interatividade da interface e comunicação com a API.
 */

document.addEventListener('DOMContentLoaded', function() {
    const locationInput = document.getElementById('locationInput');
    const searchButton = document.getElementById('searchButton');
    const loadingDiv = document.getElementById('loading');
    const resultSection = document.getElementById('resultSection');
    const errorMessage = document.getElementById('errorMessage');

    // Event listeners
    searchButton.addEventListener('click', handleSearch);
    locationInput.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            handleSearch();
        }
    });

    /**
     * Manipula o evento de pesquisa
     */
    function handleSearch() {
        const location = locationInput.value.trim();

        // Validar entrada
        if (!location) {
            showError('Por favor, insira uma cidade ou país');
            return;
        }

        // Limpar mensagens anteriores
        clearMessages();

        // Mostrar loading
        showLoading(true);

        // Fazer requisição à API
        fetchTimezone(location)
            .then(data => {
                showLoading(false);

                if (data.status === 'SUCCESS') {
                    displayResult(data);
                } else {
                    showError(data.message || 'Localidade não encontrada');
                }
            })
            .catch(error => {
                showLoading(false);
                showError('Erro ao conectar com a API: ' + error.message);
                console.error('Erro:', error);
            });
    }

    /**
     * Realiza a requisição à API
     * 
     * @param {string} location Nome da localidade
     * @returns {Promise} Promise com os dados da API
     */
    function fetchTimezone(location) {
        const apiUrl = '/api.php?location=' + encodeURIComponent(location);

        return fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na resposta da API: ' + response.statusText);
                }
                return response.json();
            });
    }

    /**
     * Exibe o resultado da pesquisa
     * 
     * @param {object} data Dados retornados pela API
     */
    function displayResult(data) {
        // Formatar hora
        const timeFormatted = formatTime(data.currentTime);

        // Atualizar elementos do resultado
        document.getElementById('resultLocation').textContent = data.location;
        document.getElementById('resultTime').textContent = timeFormatted;
        document.getElementById('resultTimezone').textContent = data.timezone;
        document.getElementById('resultOffset').textContent = data.utcOffset;

        // Mostrar seção de resultado
        resultSection.classList.add('show');

        // Log de sucesso
        console.log('Resultado obtido:', data);
    }

    /**
     * Formata a hora para exibição
     * 
     * @param {string} timeString Hora em formato ISO
     * @returns {string} Hora formatada
     */
    function formatTime(timeString) {
        if (!timeString) return 'N/A';

        try {
            // Criar objeto Date a partir da string ISO
            const date = new Date(timeString + 'Z'); // Adicionar Z para indicar UTC
            
            // Formatar hora
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            const seconds = String(date.getSeconds()).padStart(2, '0');

            return `${hours}:${minutes}:${seconds}`;
        } catch (error) {
            console.error('Erro ao formatar hora:', error);
            return timeString;
        }
    }

    /**
     * Exibe mensagem de erro
     * 
     * @param {string} message Mensagem de erro
     */
    function showError(message) {
        errorMessage.textContent = message;
        errorMessage.classList.add('show');
        resultSection.classList.remove('show');
    }

    /**
     * Exibe ou oculta o indicador de carregamento
     * 
     * @param {boolean} show true para mostrar, false para ocultar
     */
    function showLoading(show) {
        if (show) {
            loadingDiv.classList.add('show');
            searchButton.disabled = true;
        } else {
            loadingDiv.classList.remove('show');
            searchButton.disabled = false;
        }
    }

    /**
     * Limpa mensagens de erro e resultado
     */
    function clearMessages() {
        errorMessage.classList.remove('show');
        resultSection.classList.remove('show');
    }

    // Focar no input ao carregar a página
    locationInput.focus();
});

/**
 * Função para atualizar a hora em tempo real
 * (Opcional: pode ser usada para atualizar a hora exibida a cada segundo)
 */
function updateTimeDisplay() {
    const resultTime = document.getElementById('resultTime');
    
    if (resultTime && resultTime.textContent !== 'N/A') {
        // Aqui você pode adicionar lógica para atualizar a hora
        // Por exemplo, incrementar 1 segundo a cada chamada
        // Porém, isso requer armazenar a hora base e o offset
    }
}

// Atualizar hora a cada segundo (opcional)
// setInterval(updateTimeDisplay, 1000);
