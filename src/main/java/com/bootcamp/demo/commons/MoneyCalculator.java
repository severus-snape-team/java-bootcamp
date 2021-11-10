package com.bootcamp.demo.commons;

import com.bootcamp.demo.dto.ScooterDTO;
import com.bootcamp.demo.entity.Scooter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

// class that will contain required BigDecimal operations
// To be completed by need

public class MoneyCalculator {

    private static RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    private static int DECIMALS = Currency.getInstance("RON").getDefaultFractionDigits();
    private static final BigDecimal TWO = new BigDecimal("2");
    private static BigDecimal HUNDRED = new BigDecimal("100");

    private MoneyCalculator() {
    }

    public static BigDecimal getSum(BigDecimal amountOne, BigDecimal amountTwo){
        return amountOne.add(amountTwo);
    }

    public static BigDecimal getDifference(BigDecimal amountOne, BigDecimal amountTwo){
        return amountTwo.subtract(amountOne);
    }

    public static BigDecimal getMultiplication(BigDecimal amountOne, BigDecimal amountTwo){
        return rounded(amountOne.multiply(amountTwo));
    }

    public static BigDecimal getDivision(BigDecimal amountOne, BigDecimal amountTwo){
        return amountTwo.divide(amountOne, ROUNDING_MODE);
    }

    public static BigDecimal getAverage(BigDecimal amountOne, BigDecimal amountTwo){
        return getSum(amountOne, amountTwo).divide(TWO, ROUNDING_MODE);
    }

    public static BigDecimal rounded(BigDecimal number){
        return number.setScale(DECIMALS, ROUNDING_MODE);
    }

    public static BigDecimal getPercentage(BigDecimal amountOne, double percentage){
        BigDecimal result = amountOne.multiply(new BigDecimal(percentage));
        result = result.divide(HUNDRED, ROUNDING_MODE);
        return rounded(result);
    }




}
