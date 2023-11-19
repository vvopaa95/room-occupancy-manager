package com.example.roomoccupancymanager.service.processor;

import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.model.OptimizedAccommodation;
import org.springframework.stereotype.Component;

@Component
public class PremiumGuestProcessor implements GuestProcessor {
    @Override
    public int processGuest(final OptimizedAccommodation optimizedAccommodation,
                            final GuestAccommodation guestAccommodation,
                            final int currentEconomyGuestIdx) {
        int processedGuests = 0;
        for (int guest = 0;
             guest < currentEconomyGuestIdx && optimizedAccommodation.getUsagePremium() < guestAccommodation.premium();
             guest++) {
            final var moneyAmount = guestAccommodation.guests().get(guest);
            optimizedAccommodation.addPremiumPayment(moneyAmount);
            processedGuests++;
        }
        return processedGuests;
    }
}
