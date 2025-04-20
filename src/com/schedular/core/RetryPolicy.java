package com.schedular.core;

public interface RetryPolicy {
    public boolean shouldRetry(int attempt);
    public long getRetryDelay(int attempt);
}
