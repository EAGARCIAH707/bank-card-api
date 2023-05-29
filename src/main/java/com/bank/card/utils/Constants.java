package com.bank.card.utils;

public class Constants {

    public static final int RAND_NUM_LENGTH = 10;
    public static final int CARD_VALIDITY_YEARS = 3;
    public static final int ANN_HOUR_LIMIT = 24;
    public static final String CURRENCY = "USD";
    public static final String DATE_FORMAT = "MM/yyyy";


    public static String CARD_NOT_FOUND = "The card was not found in the system.";
    public static String INACTIVE_CARD = "The card is inactive and cannot be used.";
    public static String LOCKED_CARD = "The card has been locked.";
    public static String EXPIRED_CARD = "The card has expired and is no longer valid.";

    public static String TRANSACTION_NOT_FOUND = "The transaction could not be found in the system.";
    public static String INSUFFICIENT_BALANCE = "The account has insufficient balance to complete the transaction.";
    public static String ANNULATION_TIME_EXCEEDED = "The time limit for cancellation has been exceeded.";

    private Constants() {
    }

}
