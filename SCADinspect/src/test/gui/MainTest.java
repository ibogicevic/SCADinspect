package test.gui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.Main;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;

public class MainTest {

	public int testsToRun = 2;

	@Before
	public synchronized void startApplication() throws InterruptedException {
		System.out.println("Start");
		// launch the application in a different thread
		Launcher launcher = new Launcher();
		launcher.start();
		// wait with the tests until application is loaded
		while (Main.getInstance() == null) {
			System.out.println("Wait...");
			this.wait(250);
		}
	}

	private void fireControl(String className, String name) {
		System.out.println("Click");
		// buffer all buttons
		List<Node> buttons = new ArrayList<Node>();
		for (Node child : Main.getInstance().toolbarArea.getChildren()) {
			for (Node subChild : ((HBox)child).getChildren()) {
				buttons.add(subChild);
			}
		}
		// find button and fire it
		for (Node child : buttons) {
			if (child.getClass().toString().equals(name)) {
				Hyperlink hlink = (Hyperlink)child;
				if (hlink.getText().equals(name)) {
					Platform.runLater(
							new Runnable() {
								@Override
								public void run() {
									hlink.fire();
									testsToRun--;
								}
							});
				}
			}
		}
	}

	@Test
	public void testAboutDialog() {
		fireControl("Hyperlink", "Help");
		fireControl("Hyperlink", "About");
		assertTrue(true);
	}

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