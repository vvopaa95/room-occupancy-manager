package com.example.roomoccupancymanager.service.processor;

import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.model.OptimizedAccommodation;
import org.springframework.stereotype.Component;

@Component
public class EconomyGuestProcessor implements GuestProcessor {
    @Override
    public int processGuest(final OptimizedAccommodation optimizedAccommodation,
                            final GuestAccommodation guestAccommodation,
                            final int currentEconomyGuestIdx) {
        int processedGuests = 0;
        for (int guest = currentEconomyGuestIdx;
             guest < guestAccommodation.guests().size() && optimizedAccommodation.getUsageEconomy() < guestAccommodation.economy();
             guest++) {
            final var moneyAmount = guestAccommodation.guests().get(guest);
            optimizedAccommodation.addEconomyPayment(moneyAmount);
            processedGuests++;
        }
        return processedGuests;
    }
}
