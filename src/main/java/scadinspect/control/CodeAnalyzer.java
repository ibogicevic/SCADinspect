package scadinspect.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import scadinspect.gui.Main;
import scadinspect.parser.Parser;
import scadinspect.parser.ParserResult;

/**
 * Created by david on 31/03/2017.
 */
public class CodeAnalyzer {

  public static void refresh() {

    new Thread(() -> {
      List<File> fileList = Main.getInstance().getFileList();

      Map<File, ParserResult> fileParserResultMap = new HashMap<>();

      fileList.forEach(file -> {
        try {
          fileParserResultMap.put(file, Parser.parse(file));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      });

      Main.getInstance().statusArea.setMessage("Issues found: "
          + fileParserResultMap.values().stream().map(r -> r.getIssues().size())
          .reduce(Integer::sum).orElse(-1));

      Main.getInstance().tabArea.getIssueList().clearList();

      fileList.forEach(file -> {
        Main.getInstance().tabArea.getIssueList()
            .addDataToTable(new ArrayList<>(fileParserResultMap.get(file).getIssues()));
      });

    }).start();

  }

}
