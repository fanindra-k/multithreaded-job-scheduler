package com.schedular.core;

public interface Task {
    public String getTaskId();
    public boolean execute() throws Exception;
    public RetryPolicy getRetryPolicy();
}
