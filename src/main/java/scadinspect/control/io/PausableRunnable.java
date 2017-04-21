/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scadinspect.control.io;

import java.util.logging.Level;
import scadinspect.gui.Main;

/**
 *
 * @author balsfull
 */
public abstract class PausableRunnable implements Runnable {

    private final Object LOCK = new Object();
    private byte state = 0;//0 = running, 1 = paused, 2 = done, 3 = terminated

    @Override
    public void run() {
        while (state <= 1) {
            checkPaused();
            if(executeStep()) {
                state = 2;
            }
        }
    }
    
    public abstract boolean executeStep();

    public boolean isPaused() {
        return state == 1;
    }
    
    public boolean hasEndedNaturally() {
        return state == 2;
    }
    
    public boolean isRunning() {
        return state == 0;
    }
    
    public void terminate() {
        synchronized (LOCK) {
            state = 3;
            LOCK.notify();
        }
    }

    public void pause() {
        state = 1;
    }

    public void resume() {
        synchronized (LOCK) {
            state = 0;
            LOCK.notify();
        }
    }

    private void checkPaused() {
        synchronized (LOCK) {
            while (state == 1) {
                try {
                    LOCK.wait();
                } catch (InterruptedException ex) {
                    Main.logger.log(Level.WARNING, ex.getMessage(), ex);
                }
            }
        }
    }
}