package com.rd.transaction.data

import com.rd.transaction.toLocalTime
import java.time.Duration
import java.time.LocalTime
import java.util.stream.Stream
import kotlin.streams.asSequence

class Analyzer(val histogramFactory: () -> Histogram = { Histogram("14:00".toLocalTime(), Duration.ofMinutes(5), 6) }) {

    fun analyze(data: Stream<Triple<String, String, String>>): Map<String, List<Pair<LocalTime, Double>>> {
        return data
            .asSequence()
            .groupBy({ it.second }) { it.first.toLocalTime() to it.third.toDouble() }
            .mapValues {
                val histogram = histogramFactory()
                it.value.forEach { histogram.putValue(it) }
                histogram.hist.map { it.key + histogram.incrementTime to it.value }
            }
    }
}