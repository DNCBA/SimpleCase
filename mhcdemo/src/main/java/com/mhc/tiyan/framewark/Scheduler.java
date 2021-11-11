package com.mhc.tiyan.framewark;

import com.mhc.tiyan.TvJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    private List<Task> taskList = new ArrayList<>();
    private ExecutorService executorService = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS, new SynchronousQueue<>());


    public void dispatch() {
        Dispatcher dispatcher = new Dispatcher();
        executorService.submit(dispatcher);
    }

    public void addTask(Task task) {
        taskList.add(task);
        LOGGER.info("scheduler add task: {}", task.getName());
    }


    class Dispatcher implements Runnable {

        private final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

        private volatile Boolean stop = false;

        @Override
        public void run() {
            while (!stop) {
                long currentTimeMillis = System.currentTimeMillis();
                if (taskList.isEmpty()) {
                    continue;
                }
                for (Task task : taskList) {
                    if (currentTimeMillis >= task.getTime() && task.getCount() > 0) {
                        task.run();
                        task.incrCount();
                        LOGGER.info("dispatcher run task: {}, time:{}, count:{}", task.getName(), task.getTime(), task.getCount());
                    }
                }
            }
            LOGGER.info("dispatcher is stop");
        }

        public void stop() {
            stop = true;
        }
    }


}
