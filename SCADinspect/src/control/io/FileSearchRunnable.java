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

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class FileSearchRunnable extends PausableRunnable {

    private final FileFilter filter;
    private final Collection<File> discovered;
    private final Queue<File> check = new LinkedList<>();
    private final boolean recursive;

    /**
     * 
     * @param dir The root directory where the FileSearchRunnable starts searching
     * @param filter FileFilter specifying which Files get collected
     */
    public FileSearchRunnable(File dir, FileFilter filter) {
        this(dir, filter, true);
    }

    /**
     * 
     * @param dir The root directory where the FileSearchRunnable starts searching
     * @param filter FileFilter specifying which Files get collected
     * @param recursive default true
     */
    public FileSearchRunnable(File dir, FileFilter filter, boolean recursive) {
        this.check.offer(dir);
        this.filter = filter;
        this.discovered = new HashSet<>();
        this.recursive = recursive;
    }

    @Override
    public boolean executeStep() {
        if (!check.isEmpty()) {
            File dir = check.poll().getAbsoluteFile();
            File[] discoveredFiles = dir.listFiles(filter);
            if (discoveredFiles == null) {
                //No read permission or directory is empty
                return false;
            }
            discovered.addAll(Arrays.asList(discoveredFiles));
            if (recursive) {
                check.addAll(Arrays.asList(dir.listFiles(f -> f.isDirectory())));
            }
            return false;
        }
        return true;
    }

    public Collection<File> get() {
        return discovered;
    }
}
