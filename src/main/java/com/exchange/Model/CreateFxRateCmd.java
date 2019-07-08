package com.exchange.Model;

import java.math.BigDecimal;

public class CreateFxRateCmd {

    private String fromCcyIso;
    private BigDecimal rate;
    private String toCcyIso;

    public String getFromCcyIso() {
        return fromCcyIso;
    }

    public void setFromCcyIso(String fromCcyIso) {
        this.fromCcyIso = fromCcyIso;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getToCcyIso() {
        return toCcyIso;
    }

    public void setToCcyIso(String toCcyIso) {
        this.toCcyIso = toCcyIso;
    }
}
