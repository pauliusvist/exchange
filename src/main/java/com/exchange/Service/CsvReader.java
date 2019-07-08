package com.exchange.Service;

import com.exchange.Model.CreateFxRateCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

@Service
public class CsvReader {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    private static final String DEFAULT_CURRENCY = "EUR";

    @Autowired
    private FxRateService fxRateService;

    @Transactional
    Collection<CreateFxRateCmd> retrieveData(String path) throws FileNotFoundException {

        if (path.isEmpty() || path.equals(" ")) {
            throw new IllegalArgumentException("File path is undefined");
        }
        final File file = new File(path);
        final Scanner scanner = new Scanner(file);
        final Map<String, BigDecimal> icoRateMap = new HashMap<>();

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            List<String> row = parseLine(line);
            icoRateMap.put(row.get(0), new BigDecimal(row.get(1)));
        }
        scanner.close();

        final Collection<CreateFxRateCmd> cmds = new ArrayList<>();
        for (Entry<String, BigDecimal> entry : icoRateMap.entrySet()) {
            final String fromCcy = entry.getKey();
            final BigDecimal rate = entry.getValue();
            final CreateFxRateCmd cmd = new CreateFxRateCmd();
            cmd.setFromCcyIso(fromCcy);
            cmd.setRate(rate);
            cmd.setToCcyIso(DEFAULT_CURRENCY);

            cmds.add(cmd);
        }

        return cmds;
    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }
}
