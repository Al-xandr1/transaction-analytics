package com.rd.transaction.data;

import kotlin.Triple;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DataLoaderJava {

    private static final String DEFAULT_FILE_NAME = "/dataset_remain.xls";

    private String dataFile;

    public DataLoaderJava() {
        dataFile = DEFAULT_FILE_NAME;
    }

    public DataLoaderJava(String dataFile) {
        this.dataFile = (dataFile == null || dataFile.trim().isEmpty()) ? DEFAULT_FILE_NAME : dataFile;
    }

    public Supplier<Stream<Triple<String, String, String>>> getTransactionData() throws IOException, InvalidFormatException {
        List<Triple<String, String, String>> list;
        InputStream resourceAsStream = getClass().getResourceAsStream(dataFile);
        if (resourceAsStream == null) {
            resourceAsStream = new FileInputStream(dataFile);
        }
        try (final Workbook workbook = WorkbookFactory.create(resourceAsStream)) {
            final Sheet sheet = workbook.getSheetAt(0);
            final Stream<Row> rowStream =
                    StreamSupport.stream(Spliterators.spliteratorUnknownSize(sheet.iterator(), Spliterator.ORDERED), false);
            final DataFormatter dataFormatter = new DataFormatter();

            list = rowStream.map(it ->
                    new Triple<>(
                            dataFormatter.formatCellValue(it.getCell(0)),
                            dataFormatter.formatCellValue(it.getCell(1)),
                            dataFormatter.formatCellValue(it.getCell(2))
                    ))
                    .skip(1)
                    .collect(Collectors.toList());
        }

        return list::stream;
    }
}
