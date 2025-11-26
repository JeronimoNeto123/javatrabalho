package com.horatime.api.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.ZoneId;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe TimezoneUtil.
 */
@DisplayName("Testes da Classe TimezoneUtil")
class TimezoneUtilTest {

    @Test
    @DisplayName("Deve encontrar ZoneId para São Paulo")
    void testGetZoneIdForSaoPaulo() {
        // Arrange & Act
        ZoneId zoneId = TimezoneUtil.getZoneIdForLocation("São Paulo");

        // Assert
        assertNotNull(zoneId);
        assertEquals("America/Sao_Paulo", zoneId.getId());
    }

    @Test
    @DisplayName("Deve encontrar ZoneId para Paris (case-insensitive)")
    void testGetZoneIdForParisIgnoreCase() {
        // Arrange & Act
        ZoneId zoneId = TimezoneUtil.getZoneIdForLocation("PARIS");

        // Assert
        assertNotNull(zoneId);
        assertEquals("Europe/Paris", zoneId.getId());
    }

    @Test
    @DisplayName("Deve retornar null para localidade inválida")
    void testGetZoneIdForInvalidLocation() {
        // Arrange & Act
        ZoneId zoneId = TimezoneUtil.getZoneIdForLocation("LocalidadeInexistente");

        // Assert
        assertNull(zoneId);
    }

    @Test
    @DisplayName("Deve retornar null para localidade nula")
    void testGetZoneIdForNullLocation() {
        // Arrange & Act
        ZoneId zoneId = TimezoneUtil.getZoneIdForLocation(null);

        // Assert
        assertNull(zoneId);
    }

    @Test
    @DisplayName("Deve retornar null para localidade vazia")
    void testGetZoneIdForEmptyLocation() {
        // Arrange & Act
        ZoneId zoneId = TimezoneUtil.getZoneIdForLocation("");

        // Assert
        assertNull(zoneId);
    }

    @Test
    @DisplayName("Deve obter hora atual em um fuso horário")
    void testGetCurrentTimeInZone() {
        // Arrange
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");

        // Act
        LocalDateTime currentTime = TimezoneUtil.getCurrentTimeInZone(zoneId);

        // Assert
        assertNotNull(currentTime);
        assertNotNull(currentTime.getYear());
        assertNotNull(currentTime.getMonth());
        assertNotNull(currentTime.getDayOfMonth());
    }

    @Test
    @DisplayName("Deve obter offset UTC válido")
    void testGetUtcOffset() {
        // Arrange
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");

        // Act
        String offset = TimezoneUtil.getUtcOffset(zoneId);

        // Assert
        assertNotNull(offset);
        assertTrue(offset.matches("-\\d{2}:\\d{2}|\\+\\d{2}:\\d{2}"));
    }

    @Test
    @DisplayName("Deve validar localidade válida")
    void testIsValidLocationForValidLocation() {
        // Arrange & Act
        boolean isValid = TimezoneUtil.isValidLocation("Paris");

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Deve validar localidade inválida")
    void testIsValidLocationForInvalidLocation() {
        // Arrange & Act
        boolean isValid = TimezoneUtil.isValidLocation("LocalidadeInexistente");

        // Assert
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Deve retornar lista de localidades disponíveis")
    void testGetAvailableLocations() {
        // Arrange & Act
        var locations = TimezoneUtil.getAvailableLocations();

        // Assert
        assertNotNull(locations);
        assertFalse(locations.isEmpty());
        assertTrue(locations.contains("são paulo"));
        assertTrue(locations.contains("paris"));
    }
}
