package com.rd.transaction;

import com.rd.transaction.chart.ChartApplicationJava;
import com.rd.transaction.data.AnalyzerJava;
import com.rd.transaction.data.DataLoaderJava;
import com.rd.transaction.data.HistogramJava;
import javafx.application.Application;
import kotlin.Pair;
import kotlin.Triple;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.rd.transaction.data.AnalyzerJava.getParsed;

public class MainJava {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        // Load data
        Supplier<Stream<Triple<String, String, String>>> loader =
                new DataLoaderJava("/dataset_remain.xls")
                        .getTransactionData();

        // Compute the problem's solution
        final Map<String, List<Pair<LocalTime, Double>>> result =
                new AnalyzerJava(() -> new HistogramJava(getParsed("14:00"), Duration.ofMinutes(5), 6))
                        .analyze(loader.get());

        // Drawing the charts
        ChartApplicationJava.chart = new ChartApplicationJava.Chart(result, "Account balance by date. Java", "Date", "Balance");
        Application.launch(ChartApplicationJava.class);
    }
}
