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
import scadinspect.checkers.CheckResult;
import scadinspect.checkers.CheckState;
import scadinspect.checkers.Checker;
import scadinspect.checkers.SpecialVariablesChecker;
import scadinspect.data.analysis.Issue;
import scadinspect.gui.Main;
import scadinspect.parser.ParseTree;
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
      Map<File, Collection<CheckResult>> fileCheckResultMap;
      Map<File, Collection<Issue>> fileIssueMap;

      fileList.forEach(file -> {
        try {
          fileParserResultMap.put(file, Parser.parse(file));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      });

      /* list of checkers and initial check states */
      //TODO put all the checkers with initial check state into this map
      Map<Checker, CheckState> checkers = new HashMap<>();
      checkers.put(new SpecialVariablesChecker(), new CheckState());

      //  for each successful parse, perform the checker and save all results
      fileCheckResultMap = fileParserResultMap.entrySet().stream()
          .collect(Collectors.toMap(
              Entry::getKey,
              e -> Optional.ofNullable(e.getValue().getParseTree())
                  .map(ParseTree::getRootNode)
                  .map(node -> checkers.entrySet().stream()
                      .map(c -> c.getKey().check(node, c.getValue()))
                      .collect(Collectors.toList()))
                  .orElse(Collections.emptyList())
          ));

      //merge the entries of the two maps that contain issues (parse result and check result) here
      // and use the issues of all results
      fileIssueMap = fileParserResultMap.entrySet().stream()
          .collect(Collectors.toMap(
              Entry::getKey,
              //should work since both maps have the same keys
              e -> Stream.concat(
                  e.getValue().getIssues().stream(),
                  fileCheckResultMap.get(e.getKey()).stream()
                      .flatMap(r -> r.getIssues().stream()))
                  .collect(Collectors.toList())
          ));

      //Assign source file to issues
      fileIssueMap.forEach((file, issues) ->
          issues.forEach(issue -> issue.setSourceFile(file.getPath()))
      );

      Main.getInstance().statusArea.setMessage("Issues found: "
          + fileIssueMap.values().stream().map(Collection::size)
          .reduce(Integer::sum).orElse(-1));

      Main.getInstance().tabArea.getIssueList().clearList();

      fileList.forEach(file -> {
        Main.getInstance().tabArea.getIssueList()
            .addDataToTable(new ArrayList<>(fileIssueMap.get(file)));
      });

    }).start();

  }

}
