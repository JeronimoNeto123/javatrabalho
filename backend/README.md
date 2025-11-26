# HoraTime Backend - API REST de Fuso Horário
## pre requisitos
Java Development Kit (JDK)
11
Necessário para compilar e executar o Backend Java.

Apache Maven
3.6
Ferramenta de automação de construção para o projeto Java.

PHP
7.4
Necessário para executar o Frontend.

Servidor Web
Apache, Nginx ou Servidor Embutido do PHP
Necessário para servir os arquivos do Frontend PHP.

## como executar
ps: a pasta deve estar na pasta htdos do xamp

1.Abra o terminal ou prompt de comando.

2.Navegue até o diretório raiz do projeto Backend: cd backend.

3.Execute o comando Maven para compilar e iniciar a aplicação Spring Boot:mvn spring-boot:run

para funcionar o front

1.Abra um novo terminal (deixe o terminal do Java rodando).

2.Navegue até o diretório raiz do projeto Frontend: cd frontend

3.Inicie o servidor embutido do PHP na porta 8000: php -S localhost:8000

4.Acesse a aplicação no seu navegador: http://localhost:8000
## Descrição

O **HoraTime Backend** é uma API REST desenvolvida em **Java com Spring Boot** que fornece informações de hora atual em diferentes localidades do mundo. A API permite que clientes (como o frontend PHP) consultem a hora atual de uma cidade ou país específico.

## Tecnologias Utilizadas

- **Java 21 (LTS)**
- **Spring Boot 3.5.0**
- **Maven 3.x**
- **JUnit 5** (para testes)
- **Lombok** (para reduzir boilerplate)
- **Jackson** (para serialização JSON)

## Estrutura do Projeto

```
horatime-backend/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/horatime/api/
│   │   │   ├── HoraTimeApplication.java       # Classe principal
│   │   │   ├── controller/
│   │   │   │   └── TimezoneController.java    # Endpoints REST
│   │   │   ├── service/
│   │   │   │   └── TimezoneService.java       # Lógica de negócios
│   │   │   ├── model/
│   │   │   │   └── TimezoneResponse.java      # Modelo de resposta
│   │   │   └── util/
│   │   │       └── TimezoneUtil.java          # Utilitários
│   │   └── resources/
│   │       └── application.properties         # Configuração
│   └── test/
│       └── java/com/horatime/api/
│           ├── service/
│           │   └── TimezoneServiceTest.java
│           └── util/
│               └── TimezoneUtilTest.java
```

## Endpoints da API

### 1. Obter Hora Atual de uma Localidade

**Requisição:**
```
GET /api/timezone?location=<nome_da_cidade_ou_pais>
```

**Parâmetros:**
- `location` (obrigatório): Nome da cidade ou país (ex: "São Paulo", "Paris", "Tóquio")

**Resposta (Sucesso - 200 OK):**
```json
{
  "location": "São Paulo",
  "timezone": "America/Sao_Paulo",
  "currentTime": "2024-11-26T15:30:45",
  "utcOffset": "-03:00",
  "status": "SUCCESS",
  "message": "Hora obtida com sucesso"
}
```

**Resposta (Erro - 404 Not Found):**
```json
{
  "location": "LocalidadeInexistente",
  "status": "NOT_FOUND",
  "message": "Localidade não encontrada no banco de dados"
}
```

### 2. Health Check

**Requisição:**
```
GET /api/timezone/health
```

**Resposta (200 OK):**
```
HoraTime API está funcionando corretamente
```

### 3. Informações da API

**Requisição:**
```
GET /api/timezone/info
```

**Resposta (200 OK):**
```
HoraTime API v1.0.0 - Sistema de Fuso Horário
```

## Localidades Suportadas

O sistema suporta as seguintes localidades (e variações):

### Brasil
- São Paulo, Rio de Janeiro, Brasília, Salvador, Recife, Fortaleza, Manaus, Belém, Cuiabá

### Internacional
- Paris (França), Londres (Reino Unido), Berlim (Alemanha), Tóquio (Japão), Sydney (Austrália), Nova York, Los Angeles, Chicago, Denver, Dubai, Singapura, Hong Kong, Moscou (Rússia), Índia, México, Canadá, Argentina, Chile, Egito, África do Sul

**Nota:** A lista pode ser expandida editando o mapa `LOCATION_TO_TIMEZONE` na classe `TimezoneUtil.java`.

## Instalação e Execução

### Pré-requisitos

- Java 21 (instalada e disponível no PATH) ou JDK 21
- Maven 3.8 ou superior

### Passos para Executar (Java 21)

1. **Clonar ou acessar o repositório:**
   ```bash
   cd horatime-backend
   ```

2. **Compilar o projeto:**
   ```bash
   mvn clean compile
   ```

3. **Executar os testes:**
   ```bash
   mvn test
   ```

4. **Construir o JAR:**
   ```bash
   mvn clean package
   ```

5. **Executar a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

   Ou, se preferir executar o JAR diretamente:
   ```bash
   java -jar target/horatime-backend-1.0.0.jar
   ```

A API estará disponível em `http://localhost:8080`.

## Exemplos de Uso

### Usando cURL

```bash
# Obter hora em São Paulo
curl "http://localhost:8080/api/timezone?location=São Paulo"

# Obter hora em Paris
curl "http://localhost:8080/api/timezone?location=Paris"

# Health check
curl "http://localhost:8080/api/timezone/health"
```

### Usando JavaScript/Fetch

```javascript
fetch('http://localhost:8080/api/timezone?location=São Paulo')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Erro:', error));
```

### Usando PHP

```php
$location = 'São Paulo';
$url = "http://localhost:8080/api/timezone?location=" . urlencode($location);

$response = file_get_contents($url);
$data = json_decode($response, true);

echo "Hora em " . $data['location'] . ": " . $data['currentTime'];
```

### Boas Práticas Implementadas

1. **Separação de Responsabilidades:** Controller, Service e Util separados em pacotes distintos.
2. **Logging:** Utilização de SLF4J para logging estruturado.
3. **Tratamento de Erros:** Respostas HTTP apropriadas para diferentes cenários.
4. **Validação de Entrada:** Validação de parâmetros antes de processar.
5. **Testes Unitários:** Cobertura de testes para as classes principais.
6. **CORS:** Habilitado para permitir requisições de diferentes origens.
7. **Documentação:** Código comentado e README detalhado.
8. **Configuração Externalizada:** Uso de `application.properties` para configurações.

## Extensões Futuras

1. **Banco de Dados:** Integrar um banco de dados para gerenciar localidades.
2. **Autenticação:** Adicionar autenticação JWT para proteger endpoints.
3. **Cache:** Implementar cache para melhorar performance.
4. **Documentação Swagger:** Adicionar Swagger/OpenAPI para documentação interativa.
5. **Mais Localidades:** Expandir a lista de localidades suportadas.
6. **Conversão de Fuso Horário:** Adicionar endpoint para converter entre fusos horários.

## Contribuição

Para contribuir com melhorias, por favor:

1. Faça um fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`).
3. Commit suas mudanças (`git commit -am 'Adiciona MinhaFeature'`).
4. Push para a branch (`git push origin feature/MinhaFeature`).
5. Abra um Pull Request.

## Licença

Este projeto é licenciado sob a MIT License - veja o arquivo LICENSE para detalhes.

## Autor

Desenvolvido por **Manus AI** como parte do projeto HoraTime.

## Suporte

Para dúvidas ou problemas, abra uma issue no repositório do projeto.
