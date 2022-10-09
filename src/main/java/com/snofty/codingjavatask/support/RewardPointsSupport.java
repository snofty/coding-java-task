package com.snofty.codingjavatask.support;

import com.snofty.codingjavatask.model.Customer;
import com.snofty.codingjavatask.model.OfferDetails;
import com.snofty.codingjavatask.model.RewardDetails;
import com.snofty.codingjavatask.model.RewardMetadata;
import com.snofty.codingjavatask.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RewardPointsSupport {

    public OfferDetails getOffers(List<Transaction> transactions) {
        OfferDetails offerDetails = new OfferDetails();
        Map<String, List<Transaction>> transactionsPerCustomer = transactions.stream().collect(Collectors.groupingBy(Transaction::getCustomerId));
        Set<RewardDetails> totalRewardDetails = transactionsPerCustomer.entrySet()
                .stream()
                .map(stringListEntry -> {
                    String customerId = stringListEntry.getKey();
                    List<Transaction> customerTransactions = stringListEntry.getValue();
                    Map<Month, List<Transaction>> customerMonthlyTransactions = customerTransactions.stream().collect(Collectors.groupingBy(transaction -> transaction.getDateTime().getMonth()));
                    Set<RewardMetadata> rewards = customerMonthlyTransactions.entrySet().stream().map(monthListEntry -> {
                        Month month = monthListEntry.getKey();
                        List<Transaction> monthlyTransactions = monthListEntry.getValue();
                        int monthlyPoints = monthlyTransactions.stream().mapToInt(transaction -> getRewardPoints(transaction.getAmount())).sum();
                        RewardMetadata rewardMetadata = new RewardMetadata();
                        rewardMetadata.setPoints(monthlyPoints);
                        rewardMetadata.setMonth(month);
                        return rewardMetadata;
                    }).collect(Collectors.toSet());
                    RewardDetails rewardDetails = new RewardDetails();
                    Customer customer = new Customer();
                    customer.setId(customerId);
                    rewardDetails.setCustomer(customer);
                    rewardDetails.setRewards(rewards);
                    rewardDetails.setTotalPoints(rewards.stream().mapToInt(RewardMetadata::getPoints).sum());
                    return rewardDetails;
                }).collect(Collectors.toSet());
        offerDetails.setRewardDetails(totalRewardDetails);
        return offerDetails;
    }

    public Integer getRewardPoints(BigDecimal amount) {
        int points = 0;
        int amountValue = amount.intValue();
        points += getPointsForOver50(amountValue);
        points += getPointsForOver100(amountValue);
        return points;
    }

    private int getPointsForOver100(int amountValue) {
        int amountOver100 = amountValue > 100 ? (amountValue - 100) : 0;
        return amountOver100 * 2;
    }

    private int getPointsForOver50(int amountValue) {
        if (amountValue <= 50) {
            return 0;
        } else if (amountValue < 100) {
            return 100 - amountValue;
        } else {
            return 50;
        }
    }
}
