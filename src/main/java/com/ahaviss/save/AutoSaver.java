/*
 * Copyright (c) 2026 Ahaviss. All Rights Reserved.
 *
 * This file is part of the Banking-App project.
 * Unauthorized copying, modification, or distribution of this file,
 * via any medium is strictly prohibited.
 */

package com.ahaviss.save;

import com.ahaviss.logs.manager.LogManager;
import com.ahaviss.session.Session;

public class AutoSaver implements Runnable {
    private volatile long milliseconds = 30000;
    private volatile boolean running = true;
    private volatile Thread workerThread;
    public void setMilliseconds (long milliseconds) {
        this.milliseconds = milliseconds;
        workerThread.interrupt();
        System.out.println("[AUTOSAVER]: Save duration changed.");
    }
    public void stop() {
        running = false;
        workerThread.interrupt();
    }
    public boolean getRunning () {return running;}
    @Override
    public void run () {
        this.workerThread = Thread.currentThread();
        while (running) {
            try {Thread.sleep(milliseconds);}
            catch (InterruptedException e) {continue;}
            SaveData.locks.writeLock().lock();
            try {
                SaveData.saveData(Session.getAdmins(), Session.getAccounts(), Session.getOwner(), LogManager.getLogs());
            }
            finally {SaveData.locks.writeLock().unlock();}
        }
        System.out.println("[AUTOSAVER]: AutoSaver disabled.");
    }
}
