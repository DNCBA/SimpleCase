package com.mhc.tiyan;

import com.mhc.tiyan.framewark.Scheduler;

public class Skill {

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();


        scheduler.addTask(new TvJob());
        scheduler.addTask(new TvJob());
        scheduler.addTask(new TvJob());


        scheduler.dispatch();
    }
}
