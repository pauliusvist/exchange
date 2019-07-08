package com.exchange.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "FxRate")
public class FxRate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String fromCcyIso;
    private String toCcyIso;
    @Column(precision = 32, scale = 18)
    private BigDecimal rate;

    public FxRate() {}

    public FxRate(String fromCcyIso, BigDecimal rate, String toCcyIso) {
        this.fromCcyIso = fromCcyIso;
        this.toCcyIso = toCcyIso;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCcyIso() {
        return this.fromCcyIso;
    }

    public void setFromCcyIso(String fromCcyIso) {
        this.fromCcyIso = fromCcyIso;
    }

    public String getToCcyIso() {
        return this.toCcyIso;
    }

    public void setToCcyIso(String toCcyIso) {
        this.toCcyIso = toCcyIso;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return this.fromCcyIso + "/" + this.toCcyIso + " " + this.rate;
    }
}
