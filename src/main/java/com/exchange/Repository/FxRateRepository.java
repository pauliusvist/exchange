package com.exchange.Repository;

import com.exchange.Model.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FxRateRepository extends JpaRepository<FxRate, Long> {
    FxRate findByFromCcyIsoAndToCcyIso(String fromCcyIso, String toCcyIso);
}
