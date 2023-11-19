package com.example.roomoccupancymanager.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.util.List;

@Builder
public record GuestAccommodation(@PositiveOrZero(message = "amount of economy rooms cannot be < 0")
                                 int economy,
                                 @PositiveOrZero(message = "amount of premium rooms cannot be < 0")
                                 int premium,
                                 @NotEmpty(message = "guests info cannot be empty")
                                 List<@Positive(message = "Guest money amount must be positive") Double> guests) {
}
