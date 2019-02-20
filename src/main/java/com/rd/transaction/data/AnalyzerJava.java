package com.rd.transaction.data;

import kotlin.Pair;
import kotlin.Triple;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnalyzerJava {

    private Supplier<HistogramJava> histogramFactory;

    public AnalyzerJava(Supplier<HistogramJava> histogramFactory) {
        this.histogramFactory = histogramFactory;
    }

    public Map<String, List<Pair<LocalTime, Double>>> analyze(Stream<Triple<String, String, String>> data) {

        final Map<String, List<Triple<String, String, String>>> transactionsByAccount =
                data.collect(Collectors.groupingBy(Triple<String, String, String>::getSecond, Collectors.toList()));

        return transactionsByAccount.entrySet().stream().map((entry) -> {
            final HistogramJava hist = histogramFactory.get();
            entry.getValue().forEach((triple) -> hist.putValue(getParsed(triple.getFirst()), Double.parseDouble(triple.getThird())));
            return new Pair(entry.getKey(), hist.getHist().entrySet().stream().map(en -> new Pair<>(en.getKey().plus(hist.getIncrementTime()), en.getValue())).collect(Collectors.toList()));
        }).collect(Collectors.toMap((Pair pair) -> (String) pair.getFirst(), r -> (List) r.getSecond()));
    }

    public static LocalTime getParsed(String first) {
        return LocalTime.parse(first, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
    }
}