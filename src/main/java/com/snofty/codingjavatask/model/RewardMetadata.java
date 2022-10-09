package com.snofty.codingjavatask.model;

import lombok.Data;

import java.time.Month;

@Data
public class RewardMetadata {
    private Month month;
    private int points;
}
