package com.rd.transaction.data

import com.rd.transaction.toLocalTime
import junit.framework.TestCase
import org.junit.Test
import java.time.Duration

class HistogramJavaTest {

    @Test
    fun putAllInFirstBucket() {
        val histogram = HistogramJava("14:00".toLocalTime(), Duration.ofMinutes(5), 6)
        IntRange(1, 100).asSequence().forEach { _ -> histogram.putValue("14:00".toLocalTime() ,  1.0) }
        TestCase.assertEquals(100.0, histogram.hist["14:00".toLocalTime()])
        TestCase.assertEquals(0, histogram.overflowNums)
        TestCase.assertEquals(0, histogram.underflowNums)
    }

    @Test
    fun putAllInLast() {
        val histogram = HistogramJava("14:00".toLocalTime(), Duration.ofMinutes(5), 6)
        IntRange(1, 100).asSequence().forEach { _ -> histogram.putValue("14:29".toLocalTime() , 1.0) }
        TestCase.assertEquals(100.0, histogram.hist["14:25".toLocalTime()])
        TestCase.assertEquals(0, histogram.overflowNums)
        TestCase.assertEquals(0, histogram.underflowNums)
    }

    @Test
    fun putAllInOverflow() {
        val histogram = HistogramJava("14:00".toLocalTime(), Duration.ofMinutes(5), 6)
        IntRange(1, 100).asSequence().forEach { _ -> histogram.putValue("14:30".toLocalTime() , 1.0) }
        TestCase.assertEquals(null, histogram.hist["14:30".toLocalTime()])
        TestCase.assertEquals(0.0, histogram.hist["14:25".toLocalTime()])
        TestCase.assertEquals(100, histogram.overflowNums)
        TestCase.assertEquals(0, histogram.underflowNums)
    }

    @Test
    fun putAllInUnderflow() {
        val histogram = HistogramJava("14:00".toLocalTime(), Duration.ofMinutes(5), 6)
        IntRange(1, 100).asSequence().forEach { _ -> histogram.putValue("13:59".toLocalTime() , 1.0) }
        TestCase.assertEquals(null, histogram.hist["13:55".toLocalTime()])
        TestCase.assertEquals(0.0, histogram.hist["14:00".toLocalTime()])
        TestCase.assertEquals(0, histogram.overflowNums)
        TestCase.assertEquals(100, histogram.underflowNums)
    }

    @Test
    fun putOneToAll() {
        val histogram = HistogramJava("14:00".toLocalTime(), Duration.ofMinutes(5), 6)
        histogram.putValue("13:55".toLocalTime() , 1.0)
        histogram.putValue("13:59".toLocalTime() , 1.0)
        histogram.putValue("14:00".toLocalTime() , 1.0)
        histogram.putValue("14:05".toLocalTime() , 1.0)
        histogram.putValue("14:10".toLocalTime() , 1.0)
        histogram.putValue("14:15".toLocalTime() , 1.0)
        histogram.putValue("14:20".toLocalTime() , 1.0)
        histogram.putValue("14:25".toLocalTime() , 1.0)
        histogram.putValue("14:30".toLocalTime() , 1.0)

        histogram.hist.values.withIndex().forEach {
            when {
                it.index == 0 -> TestCase.assertEquals(1.0, it.value)
                it.index == 5 -> TestCase.assertEquals(1.0, it.value)
            }
        }
        TestCase.assertEquals(1, histogram.overflowNums)
        TestCase.assertEquals(2, histogram.underflowNums)
    }
}