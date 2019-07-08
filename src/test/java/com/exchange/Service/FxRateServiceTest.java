package com.exchange.Service;

import org.junit.Test;

import java.io.FileNotFoundException;

public class FxRateServiceTest {

    @Test
    public void testFxRateService_findAll_returnsUniqueSetOfFxRates() {

    }

    @Test
    public void testFxRateService_getFxRate_returnsFxRateById() {

    }

    @Test
    public void testFxRateService_create_savesNewFxRateAndSaveNewInvertedFxRate() {

    }

    @Test
    public void testFxRateService_create_updatesFxRateIfGivenFxRateAlreadyExists() {

    }

    @Test
    public void testFxRateService_update_updatesFxRate() {

    }

    @Test(expected = FileNotFoundException.class)
    public void testFxRateService_importData_throwsFileNotFoundExceptionWhenFileNotFoundInGivenPath() {

    }

    @Test
    public void testFxRateService_importData_importsCsvFileFromGivenPathAndCreatesNewInstances() {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFxRateService_invert_throwsIllegalArgumentExceptionWhenPassedNull() {

    }

    @Test
    public void testFxRateService_invert_returnsInvertedFxRate() {

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFxRateService_convert_throwsIllegalArgumentExceptionWhenPassedFxRateAsANull() {

    }
    @Test(expected = IllegalArgumentException.class)
    public void testFxRateService_convert_throwsIllegalArgumentExceptionWhenPassedAmountAsAANull() {

    }

    @Test
    public void testFxRateService_convert_returnsConvertedAmount() {

    }
 }
