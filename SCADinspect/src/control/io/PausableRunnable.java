/*******************************************************************************
 * SCADinspect – https://github.com/ibogicevic/SCADinspect
 * Copyright (C) 2017 Ivan Bogicevic and others
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.io;

import java.util.logging.Level;

import gui.MainFrame;

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
                    MainFrame.logger.log(Level.WARNING, ex.getMessage(), ex);
                }
            }
        }
    }
}
