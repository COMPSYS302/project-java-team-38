package com.example.allgoods;

public enum CreditCardDetails {
    VISA_CARD_1("Visa", "Rohan Dhingra", "4111111111111111", "12/25", "123"),
    VISA_CARD_2("Visa", "Deven Trivedi", "4111 1111 1112 2222", "01/26", "234"),
    VISA_CARD_3("Visa", "Aarav Kumar", "4111 1111 1113 3333", "02/27", "345"),



    MASTERCARD_1("MasterCard", "Rohan Dhingra", "5111 1111 1111 1111", "12/25", "123"),
    MASTERCARD_2("MasterCard", "Deven Trivedi", "5111 1111 1112 2222", "01/26", "234"),
    MASTERCARD_3("MasterCard", "Aarav Kumar", "5111 1111 1113 3333", "02/27", "345"),




    AMEX_CARD_1("Amex", "Rohan Dhingra", "3711 111111 11111", "12/25", "1234"),
    AMEX_CARD_2("Amex", "Deven Trivedi", "3711 111111 22222", "01/26", "2345"),
    AMEX_CARD_3("Amex", "Aarav Kumar", "3711 111111 33333", "02/27", "3456");


    private final String cardType;
    private final String cardHolderName;
    private final String cardNumber;
    private final String expiryDate;
    private final String cvv;

    CreditCardDetails(String cardType, String cardHolderName, String cardNumber, String expiryDate, String cvv) {
        this.cardType = cardType;
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }
}
