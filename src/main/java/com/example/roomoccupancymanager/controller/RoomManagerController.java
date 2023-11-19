package com.example.roomoccupancymanager.controller;

import com.example.roomoccupancymanager.model.OptimizedAccommodation;
import com.example.roomoccupancymanager.model.GuestAccommodation;
import com.example.roomoccupancymanager.service.RoomManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomManagerController {
    private final RoomManagerService roomManagerService;

    @PostMapping("/optimize-guest-accommodation")
    public OptimizedAccommodation optimizeAccommodation(@Valid @RequestBody GuestAccommodation guestAccommodation) {
        return roomManagerService.optimizeGuestAccommodation(guestAccommodation);
    }
}
