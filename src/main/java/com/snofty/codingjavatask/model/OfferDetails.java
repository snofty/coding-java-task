package com.snofty.codingjavatask.model;

import lombok.Data;

import java.util.Set;

@Data
public class OfferDetails {
    private Set<RewardDetails> rewardDetails;
}
