package test.gui;

import org.junit.Before;
import org.junit.Test;

import gui.Main;
import javafx.application.Platform;

public class MainTest {

	@Before
	public synchronized void startApplication() {
		// launch the application from a separate thread
		Launcher launcher = new Launcher();
		launcher.start();
		// wait with the tests until application is loaded
		while (Main.getInstance() == null) {
			try {
				this.wait(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testExitButton() {
		Platform.exit();
	}

}