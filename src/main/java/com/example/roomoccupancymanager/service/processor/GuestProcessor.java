package com.example.roomoccupancymanager.service.processor;

import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.model.OptimizedAccommodation;

public interface GuestProcessor {

    int processGuest(OptimizedAccommodation optimizedAccommodation,
                     GuestAccommodation guestAccommodation,
                     int currentEconomyGuestIdx);
}
