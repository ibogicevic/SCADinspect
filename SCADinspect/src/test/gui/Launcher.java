package test.gui;

import gui.Main;

public class Launcher extends Thread {

	@Override
	public void run() {
		// launch the application
		String[] args = new String[0];
		Main.main(args);		
	}

}
