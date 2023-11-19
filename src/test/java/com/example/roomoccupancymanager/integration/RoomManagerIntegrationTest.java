package com.example.roomoccupancymanager.integration;

import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.model.OptimizedAccommodation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomManagerIntegrationTest {
    private static final String CONTROLLER_URI = "/room/optimize-guest-accommodation";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldOptimizeRoomsWithExpectedResult() throws Exception {
        final var guestsData = Arrays.asList(48.55, 36.34, 18.0, 10.0, 145.0, 365.0);
        final var guestAccommodation = GuestAccommodation.builder()
                .guests(guestsData)
                .premium(4)
                .economy(1)
                .build();
        final var mvcResult = mockMvc.perform(prepareRequest(guestAccommodation))
                .andExpect(status().isOk())
                .andReturn();
        final var contentAsString = mvcResult.getResponse().getContentAsString();
        final var optimizedAccommodation = objectMapper.readValue(contentAsString, OptimizedAccommodation.class);
        assertEquals(4, optimizedAccommodation.getUsagePremium());
        assertEquals(594.89, optimizedAccommodation.getPremiumPaid());
        assertEquals(1, optimizedAccommodation.getUsageEconomy());
        assertEquals(18, optimizedAccommodation.getEconomyPaid());
    }

    @Test
    void shouldThrowValidationExceptionWhenGuestAccommodationDataIsInvalid() throws Exception {
        final var guestsData = Arrays.asList(48.55, -1.0);
        final var guestAccommodation = GuestAccommodation.builder()
                .guests(guestsData)
                .premium(3)
                .economy(-1)
                .build();
        final var mvcResult = mockMvc.perform(prepareRequest(guestAccommodation))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertSame(MethodArgumentNotValidException.class, Objects.requireNonNull(mvcResult.getResolvedException()).getClass());
    }

    private RequestBuilder prepareRequest(final GuestAccommodation guestAccommodation) throws JsonProcessingException {
        return post(CONTROLLER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guestAccommodation));
    }
}