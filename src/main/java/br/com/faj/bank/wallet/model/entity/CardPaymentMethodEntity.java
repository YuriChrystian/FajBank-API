package br.com.faj.bank.wallet.model.entity;

import jakarta.persistence.*;

import java.util.HashMap;

@Entity
@Table(name = "card_payment_method")
public class CardPaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long customer_id;

    @Column(nullable = false)
    String cardNumber;

    @Column(nullable = false)
    String cardHolderName;

    @Column(nullable = false)
    String expirationDate;

    @Column(nullable = false)
    String cvv;

    @Column(nullable = false)
    String brand;

    @Column(nullable = false)
    String type;

    public CardPaymentMethodEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(Long customerId) {
        this.customer_id = customerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastForDigits(){
        return cardNumber.substring(cardNumber.length()-4, cardNumber.length());
    }

    public HashMap<String, String> getMetadata() {
        HashMap<String, String> metadata = new HashMap<>();
        metadata.put("id", String.valueOf(id));
        metadata.put("cardNumber", String.valueOf(getLastForDigits()));
        metadata.put("brand", String.valueOf(brand));
        return metadata;
    }
}
