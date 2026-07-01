/*
 * Copyright [2026] [Ahaviss]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
