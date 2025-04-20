package com.schedular.model;

import com.schedular.core.RetryPolicy;
import com.schedular.core.Task;

public class SimpleTask implements Task {
    private final String taskId;
    private final RetryPolicy retryPolicy;

    public SimpleTask(String taskId, RetryPolicy retryPolicy) {
        this.taskId = taskId;
        this.retryPolicy = retryPolicy;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public boolean execute() throws Exception {
        System.out.println("Executing the Task : "+taskId);
        // Simulating the work
        Thread.sleep(2000);
        if (Math.random() < 0.7) {
            // Simulate task failure
            throw new Exception("Task failed.");
        }
        return true;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

}
