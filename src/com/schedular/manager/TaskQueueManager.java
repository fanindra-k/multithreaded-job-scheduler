package com.schedular.manager;

import com.schedular.core.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueueManager {
    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Task> retryQueue = new LinkedBlockingQueue<>();
    public void submitTask(Task task){
        taskQueue.offer(task);
    }
    public Task fetchTask() throws Exception{
        Task task = retryQueue.poll();
        if (task != null) {
            return task;
        }
        return taskQueue.take();
    }
    public boolean hasPendingTask(){
        return !taskQueue.isEmpty() ||  !retryQueue.isEmpty();
    }
    public void shutdown() {
        // Clear task queues if needed (optional)
        taskQueue.clear();
        retryQueue.clear();
        System.out.println("TaskQueueManager shut down.");
    }
}
