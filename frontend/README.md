# HoraTime Frontend - Interface PHP

## Descrição

O **HoraTime Frontend** é uma aplicação web desenvolvida em **PHP** que fornece uma interface amigável para consultar a hora atual em diferentes cidades e países. A aplicação comunica-se com a API Backend Java (Spring Boot) para obter os dados de fuso horário.

## Tecnologias Utilizadas

- **PHP 7.4+** (com suporte a cURL ou `allow_url_fopen`)
- **HTML5**
- **CSS3** (com design responsivo)
- **JavaScript (Vanilla)** (sem dependências externas)

## Estrutura do Projeto

```
horatime-frontend/
├── index.php                    # Página principal
├── api.php                      # Proxy para a API Backend
├── README.md                    # Este arquivo
├── includes/
│   ├── config.php              # Configurações da aplicação
│   └── TimezoneClient.php       # Cliente HTTP para API
├── assets/
│   ├── css/
│   │   └── style.css           # Estilos CSS
│   └── js/
│       └── app.js              # Lógica JavaScript
└── logs/                        # Diretório de logs (criado automaticamente)
```

## Instalação e Execução

### Pré-requisitos

- **PHP 7.4 ou superior**
- **Servidor Web** (Apache, Nginx, etc.) ou servidor PHP embutido
- **Backend Java** rodando em `http://localhost:8080` (ou configure a URL em `includes/config.php`)

### Opção 1: Usando o Servidor PHP Embutido

```bash
cd frontend
php -S localhost:8000
```

A aplicação estará disponível em `http://localhost:8000`.

### Opção 2: Usando Apache

1. Copie o diretório `horatime-frontend` para o diretório raiz do Apache (ex: `/var/www/html/`).
2. Acesse `http://localhost/horatime-frontend/` no navegador.

### Opção 3: Usando Nginx

Configure um virtual host no Nginx apontando para o diretório `horatime-frontend`.

## Configuração

### Arquivo `includes/config.php`

Este arquivo contém as configurações principais da aplicação:

```php
// URL base da API Backend
define('API_BASE_URL', 'http://localhost:8080');

// Timeout para requisições HTTP (em segundos)
define('API_TIMEOUT', 5);

// Nome e versão da aplicação
define('APP_NAME', 'HoraTime');
define('APP_VERSION', '1.0.0');
```

**Para alterar a URL da API:**

Se o Backend Java está rodando em um servidor diferente, edite o arquivo `includes/config.php`:

```php
define('API_BASE_URL', 'http://seu-servidor:8080');
```

## Funcionalidades

### 1. Interface de Pesquisa

- Campo de entrada para digitar o nome da cidade ou país
- Botão de busca para enviar a requisição
- Suporte a pesquisa por Enter (tecla)

### 2. Exibição de Resultados

- **Localidade:** Nome da cidade/país pesquisada
- **Hora Atual:** Hora exata no formato HH:MM:SS
- **Fuso Horário:** Identificador do fuso horário (ex: America/Sao_Paulo)
- **Offset UTC:** Diferença em relação ao UTC (ex: -03:00)

### 3. Validação e Segurança

- Validação de entrada do usuário (comprimento, caracteres permitidos)
- Sanitização de dados para prevenir XSS
- Tratamento de erros com mensagens descritivas
- Verificação de disponibilidade da API

### 4. Design Responsivo

- Interface otimizada para desktop, tablet e mobile
- Gradiente visual atraente
- Animações suaves
- Indicador de carregamento

### 5. Logging

- Registro de requisições bem-sucedidas e erros
- Arquivo de log em `logs/horatime.log`

## Arquivos Principais

### `index.php`

Página principal da aplicação. Contém:
- Estrutura HTML
- Formulário de pesquisa
- Seção de resultados
- Links e informações úteis

### `api.php`

Proxy intermediário entre o frontend JavaScript e a API Backend. Responsabilidades:
- Receber requisições do JavaScript
- Validar parâmetros
- Comunicar com a API Backend
- Retornar respostas em JSON

### `includes/TimezoneClient.php`

Classe responsável pela comunicação HTTP com a API Backend. Funcionalidades:
- Suporte a cURL e `file_get_contents`
- Tratamento de erros
- Timeout configurável
- Health check da API

### `includes/config.php`

Arquivo de configuração centralizado. Contém:
- Configurações da API
- Funções utilitárias (sanitização, validação, logging)
- Constantes da aplicação

### `assets/css/style.css`

Estilos CSS da aplicação:
- Design moderno com gradiente
- Layout responsivo
- Animações e transições
- Temas para diferentes estados (sucesso, erro, carregamento)

### `assets/js/app.js`

Lógica JavaScript do frontend:
- Manipulação de eventos
- Requisições AJAX
- Atualização dinâmica da interface
- Formatação de dados

## Exemplos de Uso

### Pesquisar Hora em São Paulo

1. Abra `http://localhost:8000` no navegador
2. Digite "São Paulo" no campo de pesquisa
3. Clique em "Buscar" ou pressione Enter
4. A hora atual em São Paulo será exibida

### Pesquisar Hora em Paris

1. Digite "Paris" no campo de pesquisa
2. Clique em "Buscar"
3. A hora atual em Paris será exibida com o fuso horário Europe/Paris

## Localidades Suportadas

O sistema suporta mais de 40 localidades, incluindo:

- **Brasil:** São Paulo, Rio de Janeiro, Brasília, Salvador, Recife, Fortaleza, Manaus, Belém, Cuiabá
- **Europa:** Paris, Londres, Berlim, Moscou
- **Ásia:** Tóquio, Singapura, Hong Kong, Dubai, Índia
- **Américas:** Nova York, Los Angeles, Chicago, Denver, México, Canadá, Argentina, Chile
- **Oceania:** Sydney, Austrália
- **África:** Egito, África do Sul

## Tratamento de Erros

A aplicação trata diversos cenários de erro:

| Erro | Mensagem | Solução |
| :--- | :--- | :--- |
| Campo vazio | "Por favor, insira uma cidade ou país" | Digite uma localidade válida |
| Localidade inválida | "Localidade não encontrada" | Verifique o nome da localidade |
| API offline | "API Backend indisponível" | Inicie o servidor Java |
| Erro de conexão | "Erro ao conectar com a API" | Verifique a URL da API |

## Boas Práticas Implementadas

1. **Separação de Responsabilidades:** Lógica, apresentação e configuração separadas
2. **Validação de Entrada:** Todos os dados do usuário são validados
3. **Sanitização:** Proteção contra XSS e injeção de código
4. **Tratamento de Erros:** Mensagens descritivas para o usuário
5. **Logging:** Registro de eventos importantes
6. **Responsividade:** Design adaptável a diferentes tamanhos de tela
7. **Acessibilidade:** Uso de labels, atributos ARIA quando necessário
8. **Performance:** Sem dependências externas, apenas JavaScript vanilla

## Troubleshooting

### "API Backend indisponível"

**Causa:** O servidor Java não está rodando.

**Solução:**
1. Inicie o Backend Java: `mvn spring-boot:run` (no diretório `horatime-backend`)
2. Verifique se está rodando em `http://localhost:8080`
3. Teste com: `curl http://localhost:8080/api/timezone/health`

### "Localidade não encontrada"

**Causa:** A localidade digitada não está no banco de dados.

**Solução:**
1. Verifique a ortografia
2. Use nomes conhecidos (ex: "São Paulo" em vez de "SP")
3. Consulte a lista de localidades suportadas

### "Erro ao conectar com a API"

**Causa:** Problema de conexão entre frontend e backend.

**Solução:**
1. Verifique se o Backend está rodando
2. Verifique a URL da API em `includes/config.php`
3. Verifique se há firewall bloqueando a conexão
4. Verifique os logs em `logs/horatime.log`

## Extensões Futuras

1. **Histórico de Pesquisas:** Armazenar pesquisas recentes
2. **Favoritos:** Permitir marcar localidades favoritas
3. **Comparação de Fusos:** Comparar horas em múltiplas localidades
4. **Alertas:** Notificar quando for uma hora específica
5. **Internacionalização:** Suporte a múltiplos idiomas
6. **Temas:** Modo claro/escuro

## Contribuição

Para contribuir com melhorias:

1. Faça um fork do projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## Licença

Este projeto é licenciado sob a MIT License.

## Autor

Desenvolvido por **Manus AI** como parte do projeto HoraTime.

## Suporte

Para dúvidas ou problemas, abra uma issue no repositório do projeto.
