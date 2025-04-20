package com.schedular.model;

import com.schedular.core.RetryPolicy;

public class SimpleRetryPolicy implements RetryPolicy {
    private static final int MAX_ATTEMPTS = 3;
    private static final long RETRY_DELAY = 1000;

//    public SimpleRetryPolicy(int maxRtries, int delayMillis) {
//        this.MAX_ATTEMPTS = maxRtries;
//        this.RETRY_DELAY = delayMillis;
//    }

    @Override
    public boolean shouldRetry(int attempt) {
        return attempt <= MAX_ATTEMPTS;
    }

    @Override
    public long getRetryDelay(int attempt) {
        return RETRY_DELAY;
    }
}
