package scadinspect.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
          fileParserResultMap.put(file, Parser.parse(new BufferedReader(new FileReader(file))));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      });

      Main.getInstance().statusArea.setMessage("Issues found: "
          + fileParserResultMap.values().stream().map(r -> r.getIssues().size())
          .reduce(Integer::sum).orElse(-1));

      Main.getInstance().tabArea.issueList.clearList();

      fileList.forEach(file -> {
        Main.getInstance().tabArea.issueList
            .addDataToTable(new ArrayList<>(fileParserResultMap.get(file).getIssues()));
      });

    }).start();

  }

}
