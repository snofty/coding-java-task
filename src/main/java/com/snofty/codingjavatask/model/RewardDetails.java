package com.snofty.codingjavatask.model;

import lombok.Data;

import java.util.Set;

@Data
public class RewardDetails {
    private Customer customer;
    private Set<RewardMetadata> rewards;
    private int totalPoints;
}
