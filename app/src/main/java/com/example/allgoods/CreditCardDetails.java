package com.example.allgoods;

public enum CreditCardDetails {
    VISA_CARD_1("Visa", "Rohan Dhingra", "4111 1111 1111 1111", "12/25", "123"),
    VISA_CARD_2("Visa", "Deven Trivedi", "4111 1111 1112 2222", "01/26", "234"),
    VISA_CARD_3("Visa", "Aarav Kumar", "4111 1111 1113 3333", "02/27", "345"),
    VISA_CARD_4("Visa", "Saanvi Singh", "4111 1111 1114 4444", "03/28", "456"),
    VISA_CARD_5("Visa", "Mohammed Ali", "4111 1111 1115 5555", "04/29", "567"),
    VISA_CARD_6("Visa", "Ying Lee", "4111 1111 1116 6666", "05/30", "678"),
    VISA_CARD_7("Visa", "Juan Carlos", "4111 1111 1117 7777", "06/31", "789"),
    VISA_CARD_8("Visa", "Emily Davis", "4111 1111 1118 8888", "07/32", "890"),
    VISA_CARD_9("Visa", "Nina O'Brien", "4111 1111 1119 9999", "08/33", "901"),
    VISA_CARD_10("Visa", "Alex Johnson", "4111 1111 1120 0000", "09/34", "012"),


    MASTERCARD_1("MasterCard", "Rohan Dhingra", "5111 1111 1111 1111", "12/25", "123"),
    MASTERCARD_2("MasterCard", "Deven Trivedi", "5111 1111 1112 2222", "01/26", "234"),
    MASTERCARD_3("MasterCard", "Aarav Kumar", "5111 1111 1113 3333", "02/27", "345"),
    MASTERCARD_4("MasterCard", "Saanvi Singh", "5111 1111 1114 4444", "03/28", "456"),
    MASTERCARD_5("MasterCard", "Mohammed Ali", "5111 1111 1115 5555", "04/29", "567"),
    MASTERCARD_6("MasterCard", "Ying Lee", "5111 1111 1116 6666", "05/30", "678"),
    MASTERCARD_7("MasterCard", "Juan Carlos", "5111 1111 1117 7777", "06/31", "789"),
    MASTERCARD_8("MasterCard", "Emily Davis", "5111 1111 1118 8888", "07/32", "890"),
    MASTERCARD_9("MasterCard", "Nina O'Brien", "5111 1111 1119 9999", "08/33", "901"),
    MASTERCARD_10("MasterCard", "Alex Johnson", "5111 1111 1120 0000", "09/34", "012"),



    AMEX_CARD_1("Amex", "Rohan Dhingra", "3711 111111 11111", "12/25", "1234"),
    AMEX_CARD_2("Amex", "Deven Trivedi", "3711 111111 22222", "01/26", "2345"),
    AMEX_CARD_3("Amex", "Aarav Kumar", "3711 111111 33333", "02/27", "3456"),
    AMEX_CARD_4("Amex", "Saanvi Singh", "3711 111111 44444", "03/28", "4567"),
    AMEX_CARD_5("Amex", "Mohammed Ali", "3711 111111 55555", "04/29", "5678"),
    AMEX_CARD_6("Amex", "Ying Lee", "3711 111111 66666", "05/30", "6789"),
    AMEX_CARD_7("Amex", "Juan Carlos", "3711 111111 77777", "06/31", "7890"),
    AMEX_CARD_8("Amex", "Emily Davis", "3711 111111 88888", "07/32", "8901"),
    AMEX_CARD_9("Amex", "Nina O'Brien", "3711 111111 99999", "08/33", "9012"),
    AMEX_CARD_10("Amex", "Alex Johnson", "3711 111112 00000", "09/34", "0123");


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
