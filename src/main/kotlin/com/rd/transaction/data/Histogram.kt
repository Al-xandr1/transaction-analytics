package com.rd.transaction.data

import java.time.Duration
import java.time.LocalTime

/**
 * Histogram with open ranges: [..),[..),[..)
 */
class Histogram(val startTime: LocalTime, val incrementTime: Duration, val binCount: Int) {
    val hist = LinkedHashMap<LocalTime, Double>()
    val endTime = startTime + incrementTime.multipliedBy(binCount.toLong())
    var underflowNums: Long = 0
    var overflowNums: Long = 0

    init {
        var currentBin = startTime
        for (i in 1..binCount) {
            hist.putIfAbsent(currentBin, .0)
            currentBin += incrementTime
        }
    }

    fun putValue(timedValue: Pair<LocalTime, Double>) {
        val duration = Duration.between(startTime, timedValue.first)
        val parts = duration.toMillis() / incrementTime.toMillis()
        val fitted = if (duration.toMillis() < 0) -1 else 0
        val bucketKey: LocalTime = startTime + incrementTime.multipliedBy(parts + fitted)
        when {
            hist.containsKey(bucketKey) ->
                hist.compute(bucketKey) { _, sum -> sum!!.plus(timedValue.second) }

            timedValue.first < startTime -> underflowNums++

            timedValue.first >= endTime -> overflowNums++
        }
    }
}