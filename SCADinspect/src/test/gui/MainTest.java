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
package test.gui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.MainFrame;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;

public class MainTest {

	private int testsToRun = 1;
	
	public synchronized void decreaseTestsToRun() {
		testsToRun--;
	}
	
	@Before
	public synchronized void startApplication() throws InterruptedException {
		System.out.println("Start");
		// launch the application in a different thread
		Launcher launcher = new Launcher();
		launcher.start();
		// wait with the tests until application is loaded
		while (MainFrame.getInstance() == null) {
			System.out.println("Wait...");
			this.wait(250);
		}
	}

	private void fireControl(String className, String name) {
		System.out.println("Click");
		// buffer all buttons
		List<Node> buttons = new ArrayList<Node>();
		for (Node child : MainFrame.getInstance().toolbarArea.getChildren()) {
			for (Node subChild : ((HBox)child).getChildren()) {
				buttons.add(subChild);
			}
		}
		// find button and fire it
		for (Node child : buttons) {
			if (child.getClass().toString().endsWith(className)) {
				Hyperlink hlink = (Hyperlink)child;
				if (hlink.getText().equals(name)) {
					Platform.runLater(
							new Runnable() {
								@Override
								public void run() {
									hlink.fire();
									System.out.println("Fire!");
									decreaseTestsToRun();
								}
							});
				}
			}
		}
	}

	@Test
	public void testAboutDialog() throws InterruptedException {
		fireControl("Hyperlink", "About");
		this.wait(100);
		System.out.println("Close1");
		MainFrame.getInstance().aboutDialog.close();
		System.out.println("Close2");
		assertTrue(true);
	}
	
//	@Test
//	public void testHelpDialog() throws InterruptedException {
//		fireControl("Hyperlink", "Help");
//		this.wait(1000);
//		MainFrame.getInstance().helpDialog.close();
//		assertTrue(true);
//	}
	
	@After
	public synchronized void testExitButton() throws InterruptedException {
		System.out.println("Triggering Exit");
		// wait until all tests are finished (triggered from ui thread)
		while (testsToRun > 0) {
			System.out.println("Wait on test finish...");
			this.wait(250);
		}
		System.out.println("Finishing Exit");
		Platform.exit();
	}
}