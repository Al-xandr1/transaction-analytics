package com.rd.transaction

import com.rd.transaction.chart.ChartApplication
import com.rd.transaction.data.Analyzer
import com.rd.transaction.data.DataLoader
import com.rd.transaction.data.Histogram
import javafx.application.Application
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.stream.Stream


fun main() {
    // Load data
    val transactionData: () -> Stream<Triple<String, String, String>> =
        DataLoader("/dataset_remain.xls").getTransactionData()

    // Compute the problem's solution
    val result =
        Analyzer { Histogram("14:00".toLocalTime(), Duration.ofMinutes(5), 6) }
            .analyze(transactionData())

    // Drawing the charts
    ChartApplication.chart = ChartApplication.Companion.Chart(result, title = "Account balance by date. Kotlin")
    Application.launch(ChartApplication::class.java)
}

public fun String?.toLocalTime(): LocalTime {
    return LocalTime.parse(this, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
}
