package scadinspect.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.HTMLEditor;

/**
 * Integrated editor for SCAD-files
 * @author ivan
 */
public class EditorArea extends TabPane {

	HTMLEditor htmlEditor = new HTMLEditor();
	
	final String INITIAL_HTML = "<html>"
			+ "<head>"
			+ "<style type=\"text/css\">"
			+ "body {font-size: 12pt; font-family: monospace;}"
			+ "r {color:green;}" // reserved word
			+ "f {color:violet;}" // function
			+ "c {color:lightgreen;}" // comment
			+ "</style>"
			+ "</head><body contenteditable=\"true\">"
			+ "<r>module</r> {<br>"
			+ "  <f>&nbsp;&nbsp;circle</f>(20);<br>"
			+ "}"
			+ "</body></html>";
	
	private void refreshSyntax() {
		
	}
	
	public EditorArea() {
						
		// hide toolbar
		htmlEditor.lookup(".top-toolbar").setManaged(false);
	    htmlEditor.lookup(".top-toolbar").setVisible(false);
	    htmlEditor.lookup(".bottom-toolbar").setManaged(false);
	    htmlEditor.lookup(".bottom-toolbar").setVisible(false);
	    htmlEditor.setOnKeyReleased((event) -> {refreshSyntax();});
	    htmlEditor.setHtmlText(INITIAL_HTML);
	    System.out.println(htmlEditor.getHtmlText());
	    htmlEditor.applyCss();

		Tab tab1 = new Tab();
		tab1.setText("File1");
		tab1.setContent(htmlEditor);
		Tab tab2 = new Tab();
		tab2.setText("File2");
		this.getTabs().add(tab1);
		this.getTabs().add(tab2);
	}
}
