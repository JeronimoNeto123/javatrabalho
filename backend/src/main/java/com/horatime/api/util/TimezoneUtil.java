package com.horatime.api.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Classe utilitária para operações com fuso horário.
 * Fornece métodos para mapear nomes de localidades para ZoneIds válidos.
 */
public class TimezoneUtil {

    /**
     * Mapa de cidades/países para seus respectivos ZoneIds.
     * Este mapa pode ser expandido conforme necessário.
     */
    private static final Map<String, String> LOCATION_TO_TIMEZONE = new HashMap<>();

    static {
        // Cidades brasileiras
        LOCATION_TO_TIMEZONE.put("são paulo", "America/Sao_Paulo");
        LOCATION_TO_TIMEZONE.put("sao paulo", "America/Sao_Paulo");
        LOCATION_TO_TIMEZONE.put("rio de janeiro", "America/Sao_Paulo");
        LOCATION_TO_TIMEZONE.put("rio", "America/Sao_Paulo");
        LOCATION_TO_TIMEZONE.put("brasília", "America/Sao_Paulo");
        LOCATION_TO_TIMEZONE.put("brasilia", "America/Sao_Paulo");
        LOCATION_TO_TIMEZONE.put("salvador", "America/Sao_Paulo");
        LOCATION_TO_TIMEZONE.put("recife", "America/Sao_Paulo");
        LOCATION_TO_TIMEZONE.put("fortaleza", "America/Sao_Paulo");
        LOCATION_TO_TIMEZONE.put("manaus", "America/Manaus");
        LOCATION_TO_TIMEZONE.put("belém", "America/Belem");
        LOCATION_TO_TIMEZONE.put("belem", "America/Belem");
        LOCATION_TO_TIMEZONE.put("cuiabá", "America/Cuiaba");
        LOCATION_TO_TIMEZONE.put("cuiaba", "America/Cuiaba");
        LOCATION_TO_TIMEZONE.put("brasil", "America/Sao_Paulo");

        // Cidades internacionais
        LOCATION_TO_TIMEZONE.put("paris", "Europe/Paris");
        LOCATION_TO_TIMEZONE.put("frança", "Europe/Paris");
        LOCATION_TO_TIMEZONE.put("france", "Europe/Paris");
        LOCATION_TO_TIMEZONE.put("londres", "Europe/London");
        LOCATION_TO_TIMEZONE.put("london", "Europe/London");
        LOCATION_TO_TIMEZONE.put("reino unido", "Europe/London");
        LOCATION_TO_TIMEZONE.put("united kingdom", "Europe/London");
        LOCATION_TO_TIMEZONE.put("berlim", "Europe/Berlin");
        LOCATION_TO_TIMEZONE.put("berlin", "Europe/Berlin");
        LOCATION_TO_TIMEZONE.put("alemanha", "Europe/Berlin");
        LOCATION_TO_TIMEZONE.put("germany", "Europe/Berlin");
        LOCATION_TO_TIMEZONE.put("tóquio", "Asia/Tokyo");
        LOCATION_TO_TIMEZONE.put("tokyo", "Asia/Tokyo");
        LOCATION_TO_TIMEZONE.put("japão", "Asia/Tokyo");
        LOCATION_TO_TIMEZONE.put("japan", "Asia/Tokyo");
        LOCATION_TO_TIMEZONE.put("sydney", "Australia/Sydney");
        LOCATION_TO_TIMEZONE.put("sidney", "Australia/Sydney");
        LOCATION_TO_TIMEZONE.put("austrália", "Australia/Sydney");
        LOCATION_TO_TIMEZONE.put("australia", "Australia/Sydney");
        LOCATION_TO_TIMEZONE.put("nova york", "America/New_York");
        LOCATION_TO_TIMEZONE.put("new york", "America/New_York");
        LOCATION_TO_TIMEZONE.put("los angeles", "America/Los_Angeles");
        LOCATION_TO_TIMEZONE.put("chicago", "America/Chicago");
        LOCATION_TO_TIMEZONE.put("denver", "America/Denver");
        LOCATION_TO_TIMEZONE.put("dubai", "Asia/Dubai");
        LOCATION_TO_TIMEZONE.put("singapura", "Asia/Singapore");
        LOCATION_TO_TIMEZONE.put("singapore", "Asia/Singapore");
        LOCATION_TO_TIMEZONE.put("hong kong", "Asia/Hong_Kong");
        LOCATION_TO_TIMEZONE.put("hong kong", "Asia/Hong_Kong");
        LOCATION_TO_TIMEZONE.put("moscou", "Europe/Moscow");
        LOCATION_TO_TIMEZONE.put("moscow", "Europe/Moscow");
        LOCATION_TO_TIMEZONE.put("rússia", "Europe/Moscow");
        LOCATION_TO_TIMEZONE.put("russia", "Europe/Moscow");
        LOCATION_TO_TIMEZONE.put("índia", "Asia/Kolkata");
        LOCATION_TO_TIMEZONE.put("india", "Asia/Kolkata");
        LOCATION_TO_TIMEZONE.put("méxico", "America/Mexico_City");
        LOCATION_TO_TIMEZONE.put("mexico", "America/Mexico_City");
        LOCATION_TO_TIMEZONE.put("canadá", "America/Toronto");
        LOCATION_TO_TIMEZONE.put("canada", "America/Toronto");
        LOCATION_TO_TIMEZONE.put("argentina", "America/Argentina/Buenos_Aires");
        LOCATION_TO_TIMEZONE.put("buenos aires", "America/Argentina/Buenos_Aires");
        LOCATION_TO_TIMEZONE.put("chile", "America/Santiago");
        LOCATION_TO_TIMEZONE.put("santiago", "America/Santiago");
        LOCATION_TO_TIMEZONE.put("egito", "Africa/Cairo");
        LOCATION_TO_TIMEZONE.put("egypt", "Africa/Cairo");
        LOCATION_TO_TIMEZONE.put("cairo", "Africa/Cairo");
        LOCATION_TO_TIMEZONE.put("áfrica do sul", "Africa/Johannesburg");
        LOCATION_TO_TIMEZONE.put("africa do sul", "Africa/Johannesburg");
        LOCATION_TO_TIMEZONE.put("south africa", "Africa/Johannesburg");
        LOCATION_TO_TIMEZONE.put("johannesburgo", "Africa/Johannesburg");
        LOCATION_TO_TIMEZONE.put("johannesburg", "Africa/Johannesburg");
    }

    /**
     * Obtém o ZoneId para uma localidade especificada.
     *
     * @param location Nome da cidade ou país (case-insensitive).
     * @return ZoneId correspondente, ou null se não encontrado.
     */
    public static ZoneId getZoneIdForLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return null;
        }

        String normalizedLocation = location.trim().toLowerCase();
        String timezoneId = LOCATION_TO_TIMEZONE.get(normalizedLocation);

        if (timezoneId != null) {
            try {
                return ZoneId.of(timezoneId);
            } catch (Exception e) {
                return null;
            }
        }

        // Tentar encontrar por correspondência parcial
        for (Map.Entry<String, String> entry : LOCATION_TO_TIMEZONE.entrySet()) {
            if (entry.getKey().contains(normalizedLocation) || normalizedLocation.contains(entry.getKey())) {
                try {
                    return ZoneId.of(entry.getValue());
                } catch (Exception e) {
                    // Continuar procurando
                }
            }
        }

        return null;
    }

    /**
     * Obtém a hora atual em um fuso horário específico.
     *
     * @param zoneId O ZoneId desejado.
     * @return LocalDateTime com a hora atual no fuso horário especificado.
     */
    public static LocalDateTime getCurrentTimeInZone(ZoneId zoneId) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * Obtém o offset UTC para um fuso horário específico.
     *
     * @param zoneId O ZoneId desejado.
     * @return String com o offset UTC (ex: "-03:00").
     */
    public static String getUtcOffset(ZoneId zoneId) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return zonedDateTime.getOffset().toString();
    }

    /**
     * Obtém o nome do fuso horário em português.
     *
     * @param zoneId O ZoneId desejado.
     * @return String com o nome do fuso horário.
     */
    public static String getTimezoneName(ZoneId zoneId) {
        return zoneId.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
    }

    /**
     * Verifica se uma localidade é válida.
     *
     * @param location Nome da cidade ou país.
     * @return true se a localidade é válida, false caso contrário.
     */
    public static boolean isValidLocation(String location) {
        return getZoneIdForLocation(location) != null;
    }

    /**
     * Retorna todas as localidades disponíveis.
     *
     * @return Set com todas as localidades mapeadas.
     */
    public static Set<String> getAvailableLocations() {
        return LOCATION_TO_TIMEZONE.keySet();
    }
}
