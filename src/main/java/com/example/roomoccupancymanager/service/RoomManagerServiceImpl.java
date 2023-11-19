package com.example.roomoccupancymanager.service;

import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.model.OptimizedAccommodation;
import com.example.roomoccupancymanager.service.processor.EconomyForUpgradeGuestProcessor;
import com.example.roomoccupancymanager.service.processor.EconomyGuestProcessor;
import com.example.roomoccupancymanager.service.processor.PremiumGuestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class RoomManagerServiceImpl implements RoomManagerService {
    private static final double PREMIUM_EURO_MIN_PRICE = 100;

    private final PremiumGuestProcessor premiumGuestProcessor;
    private final EconomyForUpgradeGuestProcessor economyForUpgradeGuestProcessor;
    private final EconomyGuestProcessor economyGuestProcessor;

    // made specific field types in order not to make injection ambiguous
    public RoomManagerServiceImpl(PremiumGuestProcessor premiumGuestProcessor,
                                  EconomyForUpgradeGuestProcessor economyForUpgradeGuestProcessor,
                                  EconomyGuestProcessor economyGuestProcessor) {
        this.premiumGuestProcessor = premiumGuestProcessor;
        this.economyForUpgradeGuestProcessor = economyForUpgradeGuestProcessor;
        this.economyGuestProcessor = economyGuestProcessor;
    }

    @Override
    public OptimizedAccommodation optimizeGuestAccommodation(GuestAccommodation guestAccommodation) {
        final var optimizedAccommodation = new OptimizedAccommodation();
        guestAccommodation.guests().sort(Comparator.reverseOrder());
        final var leftmostEconomyGuestIdx = findLeftmostEconomyGuestIdx(guestAccommodation.guests());
        final var processedPremiumGuests = premiumGuestProcessor.processGuest(
                optimizedAccommodation, guestAccommodation, leftmostEconomyGuestIdx);
        log.info("Processed premium guests: {}", processedPremiumGuests);
        final var processedEconomyGuestsForUpgrade = economyForUpgradeGuestProcessor.processGuest(
                optimizedAccommodation, guestAccommodation, leftmostEconomyGuestIdx);
        log.info("Processed economy guests for upgrade: {}", processedEconomyGuestsForUpgrade);
        final var currentEconomyGuestIdx = leftmostEconomyGuestIdx + processedEconomyGuestsForUpgrade;
        final var processedEconomyGuests = economyGuestProcessor.processGuest(
                optimizedAccommodation, guestAccommodation, currentEconomyGuestIdx);
        log.info("Processed economy guests: {}", processedEconomyGuests);
        return optimizedAccommodation;
    }

    // binary search in a list with descending order
    private int findLeftmostEconomyGuestIdx(final List<Double> guests) {
        int leftIdx = 0;
        int rightIdx = guests.size() - 1;
        int leftmostEconomyGuestIdx = guests.size();
        while (leftIdx <= rightIdx) {
            int midIdx = (rightIdx - leftIdx >> 1) + leftIdx;
            double value = guests.get(midIdx);
            if (value < PREMIUM_EURO_MIN_PRICE) {
                leftmostEconomyGuestIdx = midIdx;
                rightIdx = midIdx - 1;
            } else {
                leftIdx = midIdx + 1;
            }
        }
        return leftmostEconomyGuestIdx;
    }
}
