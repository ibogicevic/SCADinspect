package test.gui;

import gui.MainFrame;

public class Launcher extends Thread {

	@Override
	public void run() {
		// launch the application
		String[] args = new String[0];
		MainFrame.main(args);
	}

}
