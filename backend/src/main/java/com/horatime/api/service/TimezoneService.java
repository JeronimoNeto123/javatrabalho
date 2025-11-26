package com.horatime.api.service;

import com.horatime.api.model.TimezoneResponse;
import com.horatime.api.util.TimezoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Serviço responsável pela lógica de negócios de fuso horário.
 * Fornece métodos para buscar a hora atual de uma localidade específica.
 */
@Slf4j
@Service
public class TimezoneService {

    /**
     * Obtém a hora atual para uma localidade especificada.
     *
     * @param location Nome da cidade ou país pesquisada pelo usuário.
     * @return TimezoneResponse contendo a hora atual e informações do fuso horário.
     */
    public TimezoneResponse getCurrentTime(String location) {
        log.info("Buscando hora atual para a localidade: {}", location);

        // Validar entrada
        if (location == null || location.trim().isEmpty()) {
            log.warn("Localidade vazia ou nula fornecida");
            return buildErrorResponse(location, "Localidade não pode estar vazia");
        }

        // Obter o ZoneId para a localidade
        ZoneId zoneId = TimezoneUtil.getZoneIdForLocation(location);

        if (zoneId == null) {
            log.warn("Localidade não encontrada: {}", location);
            return buildErrorResponse(location, "Localidade não encontrada no banco de dados");
        }

        try {
            // Obter a hora atual no fuso horário
            LocalDateTime currentTime = TimezoneUtil.getCurrentTimeInZone(zoneId);
            String utcOffset = TimezoneUtil.getUtcOffset(zoneId);

            log.info("Hora obtida com sucesso para {}: {}", location, currentTime);

            // Construir resposta de sucesso
            return TimezoneResponse.builder()
                    .location(location)
                    .timezone(zoneId.getId())
                    .currentTime(currentTime)
                    .utcOffset(utcOffset)
                    .status("SUCCESS")
                    .message("Hora obtida com sucesso")
                    .build();

        } catch (Exception e) {
            log.error("Erro ao obter hora para a localidade: {}", location, e);
            return buildErrorResponse(location, "Erro ao processar a requisição: " + e.getMessage());
        }
    }

    /**
     * Constrói uma resposta de erro padronizada.
     *
     * @param location Localidade pesquisada.
     * @param message Mensagem de erro.
     * @return TimezoneResponse com status de erro.
     */
    private TimezoneResponse buildErrorResponse(String location, String message) {
        return TimezoneResponse.builder()
                .location(location)
                .status("NOT_FOUND")
                .message(message)
                .build();
    }
}
