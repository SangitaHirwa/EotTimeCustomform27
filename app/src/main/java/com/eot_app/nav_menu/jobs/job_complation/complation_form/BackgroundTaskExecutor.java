package com.eot_app.nav_menu.jobs.job_complation.complation_form;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundTaskExecutor {

    ExecutorService service;

    public BackgroundTaskExecutor() {
    }

    public void createInstance(int threadCount) {
        service = Executors.newFixedThreadPool(threadCount);
    }
    public void excuteTask(Runnable task){
        service.execute(task);
    }
    public void shutDown(){
        service.shutdown();
    }

}
