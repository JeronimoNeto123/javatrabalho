package com.horatime.api.controller;

import com.horatime.api.model.TimezoneResponse;
import com.horatime.api.service.TimezoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST responsável por gerenciar as requisições de fuso horário.
 * Define os endpoints da API para consulta de hora em diferentes localidades.
 */
@Slf4j
@RestController
@RequestMapping("/api/timezone")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TimezoneController {

    @Autowired
    private TimezoneService timezoneService;

    /**
     * Endpoint para obter a hora atual de uma localidade.
     *
     * @param location Nome da cidade ou país (parâmetro de query).
     * @return ResponseEntity contendo a TimezoneResponse.
     */
    @GetMapping
    public ResponseEntity<TimezoneResponse> getTimezone(@RequestParam(name = "location", required = false) String location) {
        log.info("Requisição recebida para localidade: {}", location);

        // Validar entrada
        if (location == null || location.trim().isEmpty()) {
            log.warn("Parâmetro 'location' não fornecido ou vazio");
            TimezoneResponse errorResponse = TimezoneResponse.builder()
                    .status("ERROR")
                    .message("Parâmetro 'location' é obrigatório")
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Chamar o serviço
        TimezoneResponse response = timezoneService.getCurrentTime(location);

        // Retornar resposta apropriada baseada no status
        if ("SUCCESS".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Endpoint de health check para verificar se a API está funcionando.
     *
     * @return ResponseEntity com mensagem de status.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.info("Health check realizado");
        return ResponseEntity.ok("HoraTime API está funcionando corretamente");
    }

    /**
     * Endpoint para obter informações sobre a API.
     *
     * @return ResponseEntity com informações da API.
     */
    @GetMapping("/info")
    public ResponseEntity<String> info() {
        log.info("Info endpoint acessado");
        return ResponseEntity.ok("HoraTime API v1.0.0 - Sistema de Fuso Horário");
    }
}
