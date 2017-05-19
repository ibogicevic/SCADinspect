package scadinspect.gui.areas;

import java.util.prefs.Preferences;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import scadinspect.gui.DocumentationList;
import scadinspect.gui.dialogs.SettingsDialog;


/**
 * Area for 'Issues' and 'Documentation' Tabulators, sets content for Tabs here.
 *
 * @author Tim Walter
 */
public class TabArea extends TabPane {

  private final DocumentationList documentationList = new DocumentationList();

  private final Tab documentation;

  public TabArea() {

    // Tabs can't be closed
    this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    TableView documentations = generateDocTable(documentationList);

    // documentation tab
    documentation = new Tab();
    documentation.setText("Documentation");
    documentation.setContent(documentations); //SET CONTENT FOR documentationArea HERE

    Preferences userPrefs = Preferences.userRoot().node("DHBW.SCADInspect.Settings");    
    
    this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    if(userPrefs.getBoolean(SettingsDialog.SETTING_DOCUMENTATION, true)) {
      this.getTabs().add(documentation);
    }
  }

  public TableView generateDocTable(DocumentationList documentationList) {
    TableView table = documentationList.generateTableView();
    return table;
  }

  public Tab getDocumentation() {
    return documentation;
  }

  public DocumentationList getDocumentationList() {
    return documentationList;
  }
}

