package com.schedular.worker;

import com.schedular.core.Task;
import com.schedular.manager.TaskQueueManager;

public class Worker extends Thread{
    private final TaskQueueManager taskQueueManager;
    private final String workerName;
    private volatile boolean running = true;

    public Worker(TaskQueueManager taskQueueManager, String workerName) {
        this.taskQueueManager = taskQueueManager;
        this.workerName = workerName;
        this.setName(workerName);
    }

    @Override
    public void run() {
        while(running){
            try{
                Task task = taskQueueManager.fetchTask();
                System.out.println(workerName + " picked task: " + task.getTaskId());
                boolean success = false;
                int attempt = 1;

                while(!success && task.getRetryPolicy().shouldRetry(attempt)){
                    try{
                        success = task.execute();
                        if(!success){
                            System.out.println(workerName + " failed attempt " + attempt + " for task: " + task.getTaskId());
                            Thread.sleep(task.getRetryPolicy().getRetryDelay(attempt));
                        }

                    } catch (Exception e) {
                        System.out.println(workerName + " exception on task " + task.getTaskId() + ": " + e.getMessage());
                    }
                    if (!success) {
                        System.out.println(workerName + " giving up task: " + task.getTaskId());
                    } else {
                        System.out.println(workerName + " completed task: " + task.getTaskId());
                    }

                }

            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println(workerName + " interrupted.");
            }
        }
    }
    public void shutdown() {
        running = false;
        this.interrupt();
    }
}
