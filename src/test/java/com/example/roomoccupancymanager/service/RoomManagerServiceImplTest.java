package com.example.roomoccupancymanager.service;

import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.service.processor.EconomyForUpgradeGuestProcessor;
import com.example.roomoccupancymanager.service.processor.EconomyGuestProcessor;
import com.example.roomoccupancymanager.service.processor.PremiumGuestProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomManagerServiceImplTest {

    private final RoomManagerService roomManagerService = new RoomManagerServiceImpl(
            new PremiumGuestProcessor(), new EconomyForUpgradeGuestProcessor(), new EconomyGuestProcessor());

    private final List<Double> guestsData = Arrays.asList(
            23d, 45d, 155d, 374d, 22d, 99.99, 100d, 101d, 115d, 209d);


    @ParameterizedTest
    @CsvSource({"3,3,3,738.0,3,167.99", "7,5,6,1054.0,4,189.99", "2,7,2,583.0,4,189.99", "7,1,7,1153.99,1,45.0"})
    void shouldOptimizeGuestAccommodationSuccessfully(int premiumRooms,
                                                      int economyRooms,
                                                      int usagePremium,
                                                      double premiumAmount,
                                                      int usageEconomy,
                                                      double economyAmount) {
        final var guestAccommodation = GuestAccommodation.builder()
                .guests(guestsData)
                .economy(economyRooms)
                .premium(premiumRooms)
                .build();
        final var optimizedAccommodatedRooms = roomManagerService.optimizeGuestAccommodation(guestAccommodation);
        assertEquals(usagePremium, optimizedAccommodatedRooms.getUsagePremium());
        assertEquals(premiumAmount, optimizedAccommodatedRooms.getPremiumPaid());
        assertEquals(usageEconomy, optimizedAccommodatedRooms.getUsageEconomy());
        assertEquals(economyAmount, optimizedAccommodatedRooms.getEconomyPaid());
    }

    @Test
    void shouldProcessOnlyOneEconomyGuestForUpgrade() {
        final var guestAccommodation = GuestAccommodation.builder()
                .guests(Arrays.asList(25.25, 50.0, 70.0))
                .economy(0)
                .premium(1)
                .build();
        final var optimizedAccommodatedRooms = roomManagerService.optimizeGuestAccommodation(guestAccommodation);
        assertEquals(1, optimizedAccommodatedRooms.getUsagePremium());
        assertEquals(70, optimizedAccommodatedRooms.getPremiumPaid());
        assertEquals(0, optimizedAccommodatedRooms.getUsageEconomy());
        assertEquals(0, optimizedAccommodatedRooms.getEconomyPaid());
    }
}