package com.exchange.Controller;

import com.exchange.Model.CreateFxRateCmd;
import com.exchange.Model.FxRate;
import com.exchange.Service.FxRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Set;

@RestController
@Api(value = "FxRate")
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api")
public class FxRateController {

    @Autowired
    private FxRateService fxRateService;

    @GetMapping(value = "/fxRate")
    @ApiOperation(value = "Get fx rates", notes = "Returns collection of fx rates")
    public Set<FxRate> getFxRates() {
        return this.fxRateService.findAll();
    }

    @GetMapping(value = "/fxRates/{id}")
    @ApiOperation(value = "Get fx rate", notes = "Returns specific fx rate")
    public FxRate getFxRate(@PathVariable Long id) {
        return this.fxRateService.getFxRate(id);
    }

    @PostMapping(value = "/fxRates/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "New fx rate", notes = "Creates new fx rate")
    public void createFxRate(@RequestBody CreateFxRateCmd cmd) {
        this.fxRateService.create(cmd);
    }

    @PutMapping(value = "/fxRates/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Update fx rate", notes = "Updates fx rate")
    public void updateFxRate(@RequestBody CreateFxRateCmd cmd, @PathVariable Long id) {
        this.fxRateService.update(cmd, id);
    }

    @GetMapping(value = "/fxRate/{id}/convert")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Convert", notes = "Convert currency")
    public BigDecimal convert(@PathVariable Long id, @RequestParam BigDecimal amount) {
        final FxRate fxRate = this.fxRateService.getFxRate(id);
        return this.fxRateService.convert(fxRate, amount);
    }

    @PostMapping(value = "/fxRate/import")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Import", notes = "Import fx rates")
    public void importRates(@RequestParam String path) throws FileNotFoundException {
        this.fxRateService.importData(path);
    }
}
