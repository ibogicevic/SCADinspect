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
	public synchronized void startApplication() {
		System.out.println("Start");
		// launch the application in a different thread
		Launcher launcher = new Launcher();
		launcher.start();
		// wait with the tests until application is loaded
		while (Main.getInstance() == null) {
			try {
				System.out.println("Wait...");
				this.wait(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void clickButton(String name) {
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
			if (child instanceof Hyperlink) {
				Hyperlink hlink = (Hyperlink)child;
				System.out.println("Test1"+javafx.application.Platform.isFxApplicationThread());
				Platform.runLater(
						new Runnable() {
							@Override
							public void run() {
								System.out.println("Test2"+javafx.application.Platform.isFxApplicationThread());
								testsToRun--;
							}
						});
				if (hlink.getText().equals(name)) {

				}
			}
		}
	}

	@Test
	public void testAboutDialog() {
		clickButton("About");
		assertTrue(true);
	}

	@After
	public synchronized void testExitButton() {
		System.out.println("Triggering Exit");
		// wait until all tests are finished (triggered from ui thread)
		while (testsToRun > 0) {
			System.out.println("Wait on test finish...");
			try {
				this.wait(250);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Finishing Exit");
		Platform.exit();
	}
}