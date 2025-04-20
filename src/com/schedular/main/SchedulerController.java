package com.schedular.main;

import com.schedular.core.Scheduler;
import com.schedular.core.Task;
import com.schedular.model.SimpleRetryPolicy;
import com.schedular.model.SimpleTask;

public class SchedulerController {
    public static void main(String[] args) {
        // Create the Scheduler and TaskQueueManager
        Scheduler scheduler = new Scheduler();

        // Start the Scheduler with 3 workers
        scheduler.start(3);

        // Create and submit tasks
        for (int i = 1; i <= 5; i++) {
            Task task = new SimpleTask("task-" + i, new SimpleRetryPolicy());
            scheduler.submitTask(task);
        }

        // Give some time for workers to process tasks
        try {
            Thread.sleep(5000); // wait for workers to complete tasks
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Shutdown the system
        scheduler.shutdown();
    }
}
