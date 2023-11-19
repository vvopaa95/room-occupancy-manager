package com.example.roomoccupancymanager.service.processor;

import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.model.OptimizedAccommodation;
import org.springframework.stereotype.Component;

@Component
public class EconomyForUpgradeGuestProcessor implements GuestProcessor {
    @Override
    public int processGuest(final OptimizedAccommodation optimizedAccommodation,
                            final GuestAccommodation guestAccommodation,
                            final int currentEconomyGuestIdx) {
        int processedGuests = 0;
        int economyLeftOver = getEconomyGuestsLeftOver(
                guestAccommodation.guests().size(), currentEconomyGuestIdx, guestAccommodation.economy());
        for (int guest = currentEconomyGuestIdx;
             guest < currentEconomyGuestIdx + economyLeftOver && optimizedAccommodation.getUsagePremium() < guestAccommodation.premium();
             guest++) {
            final var moneyAmount = guestAccommodation.guests().get(guest);
            optimizedAccommodation.addPremiumPayment(moneyAmount);
            processedGuests++;
        }
        return processedGuests;
    }

    private int getEconomyGuestsLeftOver(final int guestNumber,
                                         final int leftmostEconomyGuestIdx,
                                         final int economyRooms) {
        int economyGuests = guestNumber - leftmostEconomyGuestIdx;
        return economyGuests - economyRooms;
    }
}
