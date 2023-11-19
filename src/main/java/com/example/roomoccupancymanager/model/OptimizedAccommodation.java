package com.example.roomoccupancymanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptimizedAccommodation {
    private int usageEconomy;
    private double economyPaid;
    private int usagePremium;
    private double premiumPaid;

    public void addEconomyPayment(double economyPayment) {
        economyPaid += economyPayment;
        usageEconomy++;
    }

    public void addPremiumPayment(double premiumPayment) {
        premiumPaid += premiumPayment;
        usagePremium++;
    }
}
