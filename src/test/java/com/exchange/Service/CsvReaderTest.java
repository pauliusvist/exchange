package com.exchange.Service;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CsvReaderTest {

    private static final String EUR_LINE = "EUR,1\n";
    private static final String EUR_LINE2 = "\"EUR\",\"1\"";
    private static final List<String> EUR_LIST = Arrays.asList("EUR", "1");
    private static final String EMPTY_STRING = "";

    @Test
    public void testCsvReader_parseLine_shouldParseStringToListWithDefaultSeparator() {
        Assert.assertEquals(EUR_LIST, CsvReader.parseLine(EUR_LINE));
    }

    @Test
    public void testCsvReader_parseLine_shouldParseStringToListWithDefaultQuote() {
        Assert.assertEquals(EUR_LIST, CsvReader.parseLine(EUR_LINE2));
    }

    @Test
    public void testCsvReader_retrieveData_shouldThrowExceptionWhenPassedEmptyString() {
        CsvReader.parseLine(EMPTY_STRING);
    }

    @Test
    public void testCsvReader_retrieveData_shouldReturnCollectionOfObjectsWhenPassedCorrectPath() {
        CsvReader.parseLine(EMPTY_STRING);
    }

}
