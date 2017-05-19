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
 * Created by David B. Maier on 31/03/2017.
 *
 * The Code Analyzer manages parsing and checking the given file(s).
 * This is done using a thread to ensure parsing and checking does not block the UI.
 * Only contains one static method (which can be called when using both manual and auto-refresh).
 */
public class CodeAnalyzer {

  /**
   * Executes parser and performs checkers on all given files.
   * This method follows these steps to manage execution of parser and checker(s):
   * - start a new thread
   * - parse each file and store the results in a map
   * - collect all checkers and perform them on the successfully parsed files
   * - merge issues created by parser and checkers into one map
   * - assign source files to issues
   * - display number of issues in statusArea
   * - display issues in UI's issue table
   */
  public static void refresh() {

    new Thread(() -> {
      List<File> fileList = Main.getInstance().getFileList();

      Map<File, ParserResult> fileParserResultMap = new HashMap<>();
      Map<File, Collection<CheckResult>> fileCheckResultMap;
      Map<File, Collection<Issue>> fileIssueMap;

      // parse all files and collect the results
      fileList.forEach(file -> {
        try {
          fileParserResultMap.put(file, Parser.parse(file));
        } catch (FileNotFoundException e) {
          //TODO Use logger
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

      // merge the entries of the two maps that contain issues (parse result and check result) here
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

      // assign source file to issues
      fileIssueMap.forEach((file, issues) ->
          issues.forEach(issue -> issue.setSourceFile(file.getPath()))
      );

      // display number of issues in statusArea
      Main.getInstance().statusArea.setMessage("Issues found: "
          + fileIssueMap.values().stream().map(Collection::size)
          .reduce(Integer::sum).orElse(-1));

    }).start();

  }

}
