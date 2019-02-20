package com.rd.transaction.data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class HistogramJava {

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Duration incrementTime;
    private final int binCount;
    private final Map<LocalTime, Double> hist;
    private int underflowNums;
    private int overflowNums;


    public HistogramJava(LocalTime startTime, Duration incrementTime, int binCount) {
        this.startTime = startTime;
        this.endTime = startTime.plus(incrementTime.multipliedBy(binCount));
        this.incrementTime = incrementTime;
        this.binCount = binCount;
        this.hist = new LinkedHashMap<>();
        this.underflowNums = 0;
        this.overflowNums = 0;

        LocalTime currentBin = startTime;
        for (int i = 0; i < binCount; i++) {
            hist.putIfAbsent(currentBin, .0);
            currentBin = currentBin.plus(incrementTime);
        }
    }

    public void putValue(LocalTime time, Double value) {
        final Duration duration = Duration.between(startTime, time);
        final long parts = duration.toMillis() / incrementTime.toMillis();
        final int fitted = (duration.toMillis() < 0) ? -1 : 0;
        final LocalTime bucketKey = startTime.plus(incrementTime.multipliedBy(parts + fitted));

        if (hist.containsKey(bucketKey)) {
            hist.compute(bucketKey, (ignore, sum) -> sum += value);
        } else if (time.compareTo(startTime) < 0) {
            underflowNums++;
        } else if (time.compareTo(endTime) >= 0) {
            overflowNums++;
        }
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Duration getIncrementTime() {
        return incrementTime;
    }

    public int getBinCount() {
        return binCount;
    }

    public Map<LocalTime, Double> getHist() {
        return hist;
    }

    public int getUnderflowNums() {
        return underflowNums;
    }

    public void setUnderflowNums(int underflowNums) {
        this.underflowNums = underflowNums;
    }

    public int getOverflowNums() {
        return overflowNums;
    }

    public void setOverflowNums(int overflowNums) {
        this.overflowNums = overflowNums;
    }
}