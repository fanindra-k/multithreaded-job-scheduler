package com.schedular.core;

import com.schedular.manager.TaskQueueManager;
import com.schedular.worker.Worker;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private final List<Worker> workers = new ArrayList<>();
    private final TaskQueueManager taskQueueManager = new TaskQueueManager();

    public void start(int numWorkers) {
        for (int i = 1; i <= numWorkers; i++) {
            Worker worker = new Worker(taskQueueManager, "Worker-" + i);
            workers.add(worker);
            worker.start();  // Actually start the thread
        }
    }

    public void submitTask(Task task) {
        taskQueueManager.submitTask(task);
    }

    public void shutdown() {
        // Stop all workers gracefully
        for (Worker worker : workers) {
            worker.shutdown();  // This will interrupt and stop the worker thread.
        }

        // Wait for all workers to finish their tasks
        for (Worker worker : workers) {
            try {
                worker.join();  // Ensures all worker threads have finished before shutting down
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Scheduler interrupted during shutdown.");
            }
        }

        // Shut down TaskQueueManager
        taskQueueManager.shutdown();  // Assuming taskQueueManager has a shutdown() method.
    }
}
