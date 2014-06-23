package com.spotify.heroic.aggregation;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.spotify.heroic.metrics.model.Statistics;
import com.spotify.heroic.model.DataPoint;
import com.spotify.heroic.model.DateRange;
import com.spotify.heroic.model.Sampling;

public interface Aggregation {
    @RequiredArgsConstructor
    public static class Result {
        @Getter
        private final List<DataPoint> result;
        @Getter
        private final Statistics.Aggregator statistics;
    }

    public interface Session {
        /**
         * Stream datapoints into this aggregator.
         * 
         * Must be thread-safe.
         * 
         * @param datapoints
         */
        public void update(Iterable<DataPoint> datapoints);

        /**
         * Get the result of this aggregator.
         */
        public Result result();
    }

    /**
     * Create an aggregation session.
     * 
     * @return
     */
    public Session session(DateRange range);

    /**
     * Get a hint of the sampling this aggregation uses.
     */
    public Sampling getSampling();

    /**
     * Get a guesstimate of how big of a memory the aggregation would need. This
     * is for the invoker to make the decision whether or not to execute the
     * aggregation.
     * 
     * @return
     */
    public long getCalculationMemoryMagnitude(DateRange range);
}