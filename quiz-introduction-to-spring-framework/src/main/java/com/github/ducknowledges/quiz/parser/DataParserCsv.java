package com.github.ducknowledges.quiz.parser;

import static com.github.ducknowledges.quiz.parser.DataParserError.PARSE_DATA_ERROR;
import static com.github.ducknowledges.quiz.parser.DataParserError.PARSE_FORMAT_ERROR;

import com.github.ducknowledges.quiz.parser.formatchecker.ParsingFormatChecker;
import com.github.ducknowledges.quiz.reader.DataReaderCreator;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class DataParserCsv implements DataParser<List<String>> {

    private final DataReaderCreator dataReaderCreator;
    private final ParsingFormatChecker<CSVRecord> formatChecker;

    public DataParserCsv(
            DataReaderCreator dataReaderCreator,
            ParsingFormatChecker<CSVRecord> formatChecker) {
        this.dataReaderCreator = dataReaderCreator;
        this.formatChecker = formatChecker;
    }

    @Override
    public List<List<String>> parseToRecords() {
        List<CSVRecord> records;
        String parsedPath = dataReaderCreator.getReadablePath();
        try (Reader csvReader = dataReaderCreator.createReader()) {
            records = CSVFormat.RFC4180.parse(csvReader).getRecords();
            if (!formatChecker.hasWrongFormattedRecords(records)) {
                return records.stream().map(CSVRecord::toList).collect(Collectors.toList());
            }
            String description = formatChecker.getDescriptionOfWrongRecords(records);
            System.err.println(PARSE_FORMAT_ERROR.message(parsedPath, description));
        } catch (IOException e) {
            System.err.println(PARSE_DATA_ERROR.message(parsedPath));
        }
        return List.of();
    }
}
