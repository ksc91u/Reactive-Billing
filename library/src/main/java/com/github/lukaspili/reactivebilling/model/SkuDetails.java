package com.github.lukaspili.reactivebilling.model;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lukasz on 06/05/16.
 */
public class SkuDetails {

    private final String productId;

    private final long priceAmountMicros;

    private final PurchaseType purchaseType;

    private final String price;

    private final String priceCurrencyCode;

    private final String title;

    private final String description;

    private final String freeTrialPeriod;

    private final String subscriptionPeriod;

    private final String introductoryPrice;

    private final String introductoryPricePeriod;

    public SkuDetails(String productId, long priceAmountMicros, PurchaseType purchaseType, String price, String priceCurrencyCode, String title, String description, String freeTrialPeriod, String subscriptionPeriod, String introductoryPrice, String introductoryPricePeriod) {
        this.productId = productId;
        this.priceAmountMicros = priceAmountMicros;
        this.purchaseType = purchaseType;
        this.price = price;
        this.priceCurrencyCode = priceCurrencyCode;
        this.title = title;
        this.description = description;
        this.freeTrialPeriod = freeTrialPeriod;
        this.subscriptionPeriod = subscriptionPeriod;
        this.introductoryPrice = introductoryPrice;
        this.introductoryPricePeriod = introductoryPricePeriod;
    }

    public String getProductId() {
        return productId;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public String getPrice() {
        return price;
    }

    public long getPriceAmountMicros() {
        return priceAmountMicros;
    }

    public String getPriceCurrencyCode() {
        return priceCurrencyCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getFreeTrialPeriod() {
        return parseTrialDate(freeTrialPeriod);
    }

    public int getSubscriptionPeriodInDays() {
        return parseTrialDate(subscriptionPeriod);
    }

    public int getIntroductoryPricePeriodInDays() {
        return parseTrialDate(introductoryPricePeriod);
    }

    public String getIntroductoryPrice(){
        return introductoryPrice;
    }

    private int parseTrialDate(String trial) {
        Pattern p = Pattern.compile("P(\\d+[WD])(\\d+D)?");
        Matcher matcher = p.matcher(trial);

        int days = 0;
        if (!matcher.matches()) {
            return 0;
        } else {

            MatchResult matchResult = matcher.toMatchResult();
            String first = matchResult.group(1);
            if (first.endsWith("W")) {
                days += 7 * Integer.parseInt(first.substring(0, first.length() - 1));
            } else {
                days += Integer.parseInt(first.substring(0, first.length() - 1));
            }
            if (matchResult.group(2) != null) {
                String second = matchResult.group(2);
                days += Integer.parseInt(second.substring(0, second.length() - 1));
            }
        }
        return days;
    }
}
