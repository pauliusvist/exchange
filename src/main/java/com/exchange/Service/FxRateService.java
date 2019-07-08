package com.exchange.Service;

import com.exchange.Model.CreateFxRateCmd;
import com.exchange.Model.FxRate;
import com.exchange.Repository.FxRateRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FxRateService {

    @Autowired
    private FxRateRepository fxRateRepository;

    @Transactional
    public Set<FxRate> findAll() {
        List<FxRate> allRates = this.fxRateRepository.findAll();
        return new HashSet<>(allRates);
    }

    @Transactional
    public FxRate getFxRate(@PathVariable Long id) {
        return this.fxRateRepository.findById(id).orElse(new FxRate());
    }

    @Transactional
    public void create(@RequestBody CreateFxRateCmd cmd) {
        final FxRate fxRate = this.fxRateRepository.findByFromCcyIsoAndToCcyIso(cmd.getFromCcyIso(), cmd.getToCcyIso());
        if (fxRate != null) {
            this.update(cmd, fxRate.getId());
        } else {
            final FxRate rate = new FxRate();
            rate.setFromCcyIso(cmd.getFromCcyIso());
            rate.setRate(cmd.getRate());
            rate.setToCcyIso(cmd.getToCcyIso());

            this.fxRateRepository.save(rate);
            final FxRate invertedRate = this.invert(rate);
            this.fxRateRepository.save(invertedRate);
        }
    }

    @Transactional
    public void update(@RequestBody CreateFxRateCmd cmd, @PathVariable Long id) {
        final FxRate rate = this.fxRateRepository.getOne(id);
        if (rate == null) {
            this.create(cmd);
        } else {
            rate.setToCcyIso(cmd.getToCcyIso());
            rate.setRate(cmd.getRate());
            rate.setFromCcyIso(cmd.getFromCcyIso());
            this.fxRateRepository.save(rate);
        }
    }

    @Transactional
    public void importData(String path) throws FileNotFoundException {
        final CsvReader csvReader = new CsvReader();
        final Collection<CreateFxRateCmd> cmds = csvReader.retrieveData(path);
        if (cmds.isEmpty()) {
            throw new FileNotFoundException("Error retrieving data from given path " + path);
        }
        for (CreateFxRateCmd cmd : cmds) {
            this.create(cmd);
        }
    }

    @Transactional
    public FxRate invert(FxRate rate) {
        if (rate == null) {
            throw new IllegalArgumentException("Fx Rate is undefined");
        }
        final String fromIco = rate.getToCcyIso();
        final String toIco = rate.getFromCcyIso();
        final BigDecimal invertedRate = BigDecimal.ONE.divide(rate.getRate(), 18, RoundingMode.CEILING);
        return new FxRate(fromIco, invertedRate, toIco);
    }

    @Transactional
    public BigDecimal convert(FxRate fxRate, BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Enter amount");
        }
        if (fxRate == null) {
            throw new IllegalArgumentException("Select fx rate");
        }
        return fxRate.getRate().multiply(amount);
    }
}
