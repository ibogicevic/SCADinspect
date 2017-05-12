package scadinspect.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import scadinspect.data.analysis.Issue;
import scadinspect.gui.Main;
import scadinspect.parser.ParseTree;
import scadinspect.parser.Parser;
import scadinspect.parser.ParserResult;
import scadinspect.parser.ast.checkers.CheckResult;
import scadinspect.parser.ast.checkers.CheckState;
import scadinspect.parser.ast.checkers.ExampleChecker;

/**
 * Created by david on 31/03/2017.
 */
public class CodeAnalyzer {

  public static void refresh(boolean filterOn, String fileName) {

    new Thread(() -> {
      List<File> fileList = Main.getInstance().getFileList();

      Map<File, ParserResult> fileParserResultMap = new HashMap<>();
      Map<File, CheckResult> fileCheckResultMap;
      Map<File, Collection<Issue>> fileIssueMap;

      fileList.forEach(file -> {
        try {
          fileParserResultMap.put(file, Parser.parse(file));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      });

      fileCheckResultMap = fileParserResultMap.entrySet().stream()
          .collect(Collectors.toMap(
              Entry::getKey,
              e -> Optional.ofNullable(e.getValue().getParseTree())
                  .map(ParseTree::getRootNode)
                  //.map(n -> n.check(new ExampleChecker(), new CheckState())) //TODO this only uses one example, support generic
                  .map(n -> new ExampleChecker().check(n, new CheckState()))
                  .orElse(new CheckResult(Collections.emptyList()))
              )
          );

     //merge the entries of the two maps here and use the result
      fileIssueMap = fileParserResultMap.entrySet().stream()
          .collect(Collectors.toMap(
              Entry::getKey,
              //should work since both maps have the same keys
              e -> Stream.concat(
                  e.getValue().getIssues().stream(),
                  fileCheckResultMap.get(e.getKey()).getIssues().stream()
                  ).collect(Collectors.toList())
              )
          );

      fileIssueMap.forEach((file, issues) ->
        issues.forEach(issue -> issue.setSourceFile(file.getPath()))
      );

      Main.getInstance().statusArea.setMessage("Issues found: "
          + fileIssueMap.values().stream().map(Collection::size)
          .reduce(Integer::sum).orElse(-1));

      Main.getInstance().tabArea.getIssueList().clearList();

      if(filterOn){
        fileList.forEach(file -> {
          Main.getInstance().tabArea.getIssueList()
              .filterList(fileName, new ArrayList<>(fileParserResultMap.get(file).getIssues()));
        });
      } else {
        fileList.forEach(file -> {
          Main.getInstance().tabArea.getIssueList()
              .addDataToTable(new ArrayList<>(fileParserResultMap.get(file).getIssues()));
        });
      }
    }).start();
  }
}
