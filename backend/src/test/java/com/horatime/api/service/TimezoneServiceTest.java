package com.horatime.api.service;

import com.horatime.api.model.TimezoneResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe TimezoneService.
 */
@DisplayName("Testes da Classe TimezoneService")
class TimezoneServiceTest {

    private TimezoneService timezoneService;

    @BeforeEach
    void setUp() {
        timezoneService = new TimezoneService();
    }

    @Test
    @DisplayName("Deve retornar hora atual para São Paulo")
    void testGetCurrentTimeForSaoPaulo() {
        // Arrange
        String location = "São Paulo";

        // Act
        TimezoneResponse response = timezoneService.getCurrentTime(location);

        // Assert
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertNotNull(response.getCurrentTime());
        assertNotNull(response.getTimezone());
        assertTrue(response.getTimezone().contains("Sao_Paulo") || response.getTimezone().contains("São_Paulo"));
    }

    @Test
    @DisplayName("Deve retornar hora atual para Paris")
    void testGetCurrentTimeForParis() {
        // Arrange
        String location = "Paris";

        // Act
        TimezoneResponse response = timezoneService.getCurrentTime(location);

        // Assert
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertNotNull(response.getCurrentTime());
        assertEquals("Europe/Paris", response.getTimezone());
    }

    @Test
    @DisplayName("Deve retornar hora atual para Tóquio")
    void testGetCurrentTimeForTokyo() {
        // Arrange
        String location = "Tóquio";

        // Act
        TimezoneResponse response = timezoneService.getCurrentTime(location);

        // Assert
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertNotNull(response.getCurrentTime());
        assertEquals("Asia/Tokyo", response.getTimezone());
    }

    @Test
    @DisplayName("Deve retornar erro para localidade inválida")
    void testGetCurrentTimeForInvalidLocation() {
        // Arrange
        String location = "LocalidadeInexistente123";

        // Act
        TimezoneResponse response = timezoneService.getCurrentTime(location);

        // Assert
        assertNotNull(response);
        assertEquals("NOT_FOUND", response.getStatus());
        assertNull(response.getCurrentTime());
        assertNotNull(response.getMessage());
    }

    @Test
    @DisplayName("Deve retornar erro para localidade nula")
    void testGetCurrentTimeForNullLocation() {
        // Arrange
        String location = null;

        // Act
        TimezoneResponse response = timezoneService.getCurrentTime(location);

        // Assert
        assertNotNull(response);
        assertEquals("NOT_FOUND", response.getStatus());
        assertNull(response.getCurrentTime());
        assertNotNull(response.getMessage());
    }

    @Test
    @DisplayName("Deve retornar erro para localidade vazia")
    void testGetCurrentTimeForEmptyLocation() {
        // Arrange
        String location = "";

        // Act
        TimezoneResponse response = timezoneService.getCurrentTime(location);

        // Assert
        assertNotNull(response);
        assertEquals("NOT_FOUND", response.getStatus());
        assertNull(response.getCurrentTime());
        assertNotNull(response.getMessage());
    }

    @Test
    @DisplayName("Deve retornar UTC offset válido")
    void testUtcOffsetIsValid() {
        // Arrange
        String location = "São Paulo";

        // Act
        TimezoneResponse response = timezoneService.getCurrentTime(location);

        // Assert
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertNotNull(response.getUtcOffset());
        assertTrue(response.getUtcOffset().matches("-\\d{2}:\\d{2}|\\+\\d{2}:\\d{2}"));
    }
}
