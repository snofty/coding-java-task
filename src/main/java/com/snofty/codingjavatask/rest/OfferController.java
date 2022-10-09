package com.snofty.codingjavatask.rest;

import com.snofty.codingjavatask.model.OfferDetails;
import com.snofty.codingjavatask.model.Transaction;
import com.snofty.codingjavatask.support.RewardPointsSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class OfferController {

    private final RewardPointsSupport rewardPointsSupport;

    public OfferController(RewardPointsSupport rewardPointsSupport) {
        this.rewardPointsSupport = rewardPointsSupport;
    }

    @PostMapping(path = "/rewards")
    public Mono<OfferDetails> generateRewardPoints(@RequestBody List<Transaction> transactions) {
        return Mono.just(rewardPointsSupport.getOffers(transactions));
    }
}
