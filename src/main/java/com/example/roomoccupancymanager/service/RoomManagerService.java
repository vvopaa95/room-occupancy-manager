package com.example.roomoccupancymanager.service;

import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.model.OptimizedAccommodation;

public interface RoomManagerService {

    OptimizedAccommodation optimizeGuestAccommodation(GuestAccommodation guestAccommodation);
}
