package scadinspect.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.HTMLEditor;

/**
 * Area for 'Issues' and 'Documentation' Tabulators, sets content for Tabs here.
 * @author Tim Walter
 */
public class TabArea extends TabPane {

	public TabArea() {
		Tab issues = new Tab();
		issues.setText("Issues");
		//issues.setContent(); SET CONTENT FOR issueArea HERE
		Tab documentation = new Tab();
		documentation.setText("Documentation");
		//issues.setContent(); SET CONTENT FOR documentationArea HERE

		this.getTabs().add(issues);
		this.getTabs().add(documentation);
	}
}
