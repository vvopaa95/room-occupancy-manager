package com.example.roomoccupancymanager.controller;

import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.model.OptimizedAccommodation;
import com.example.roomoccupancymanager.service.RoomManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomManagerControllerTest {
    @InjectMocks
    private RoomManagerController roomManagerController;

    @Mock
    private RoomManagerService roomManagerService;

    @Test
    void shouldOptimizeRooms() {
        final var guestAccommodation = GuestAccommodation.builder()
                .economy(1)
                .premium(1)
                .guests(List.of(1.0, 100.0))
                .build();
        final var expectedOptimizedAccommodation = OptimizedAccommodation.builder()
                .usagePremium(1)
                .premiumPaid(100)
                .usageEconomy(1)
                .economyPaid(1.0)
                .build();
        when(roomManagerService.optimizeGuestAccommodation(guestAccommodation))
                .thenReturn(expectedOptimizedAccommodation);
        final var optimizedAccommodatedRooms = roomManagerController.optimizeAccommodation(guestAccommodation);
        verify(roomManagerService).optimizeGuestAccommodation(guestAccommodation);
        verifyNoMoreInteractions(roomManagerService);
        assertEquals(expectedOptimizedAccommodation, optimizedAccommodatedRooms);
    }
}