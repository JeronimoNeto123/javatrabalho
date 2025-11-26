package com.horatime.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe de modelo que representa a resposta da API de fuso horário.
 * Encapsula os dados da hora atual de uma localidade específica.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimezoneResponse {

    /**
     * Nome da localidade (cidade ou país) pesquisada.
     */
    private String location;

    /**
     * Identificador do fuso horário (ex: "America/Sao_Paulo").
     */
    private String timezone;

    /**
     * Hora atual na localidade, formatada em ISO 8601.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime currentTime;

    /**
     * Offset do fuso horário em relação ao UTC (ex: "-03:00").
     */
    private String utcOffset;

    /**
     * Status da requisição: "SUCCESS" ou "NOT_FOUND".
     */
    private String status;

    /**
     * Mensagem descritiva (útil para erros).
     */
    private String message;
}
