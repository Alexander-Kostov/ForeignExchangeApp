package ForeignExchange.ForeignExchangeApp.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "conversion_history")
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fromName;

    private double fromValue;

    private String toName;

    private double toValue;

    private double amount;

    private double result;

    private LocalDate localDate;

    public ConversionHistory() {

    }

    public ConversionHistory(String fromName, double fromValue, String toName, double toValue, double amount, double result, LocalDate localDate) {
        this.fromName = fromName;
        this.fromValue = fromValue;
        this.toName = toName;
        this.toValue = toValue;
        this.amount = amount;
        this.result = result;
        this.localDate = localDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public double getFromValue() {
        return fromValue;
    }

    public void setFromValue(double fromValue) {
        this.fromValue = fromValue;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public double getToValue() {
        return toValue;
    }

    public void setToValue(double toValue) {
        this.toValue = toValue;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
