package scadinspect.checkers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;
import scadinspect.data.analysis.Issue;
import scadinspect.data.analysis.Issue.issueType;
import scadinspect.parser.ast.ASTNode;
import scadinspect.parser.ast.ArgumentCallNode;
import scadinspect.parser.ast.ArgumentDeclNode;
import scadinspect.parser.ast.AssignmentNode;
import scadinspect.parser.ast.ExprNode;
import scadinspect.parser.ast.ModuleIdNode;
import scadinspect.parser.ast.StatementNode;

/**
 * Created by felix on 05.05.17.
 *
 * Some variable ids are reserved, they start with $. one can use his own variables starting with $,
 * but this is discouraged.
 *
 * This is a basic checker implementation to find such ids in the parse tree.
 * It checks for the current ASTNode, whether it is of a type that contains an id,
 * and if this id starts with $ and is not a reserved variable.
 * If so, an according issue is created.
 *
 * Lastly, the checker applies itself to all child nodes fo the current node
 * and returns the union of all child results and the possible created issue.
 *
 * The check state is not used, since this check doesn't need it
 *
 *
 *
 * What is important to implement more checkers:
 * It must perform a check of one ASTNode at a time.
 * It must apply itself to all child nodes of the given node.
 * It may pass a state to the deeper recursion levels.
 * It must return a CheckResult containing the union of found Issues of the current node
 * and all the Issues returned be the checks of the child nodes.
 * It should have a unique ISSUE_ID
 */
public class SpecialVariablesChecker implements Checker {

  private final static Collection<String> ALLOWED = Arrays.asList("$fa", "$fs", "$fn", "$t",
      "$vpr", "$vpt", "$vpd", "$children");

  private final static String ISSUE_ID = "W-101";
  private final static Issue.issueType ISSUE_TYPE = issueType.WARNING;
  private final static String ISSUE_DESCRIPTION = "Non-reserved identifier starting with $";


  @Override
  public CheckResult check(ASTNode astNode, CheckState state) {

    CheckResult result = null;

    if (astNode instanceof ArgumentCallNode
        && astNode.type == ArgumentCallNode.Types.ID_EXPR) {
      ArgumentCallNode node = (ArgumentCallNode) astNode;
      if (node.id.startsWith("$")
          && !ALLOWED.contains(node.id)) {
        //TODO find out line number from ast node, 0 is used here
        //TODO therefore insert line number in ast node in parser
        result = new CheckResult(Collections.singletonList(
            new Issue(ISSUE_TYPE, null, 0, ISSUE_ID, ISSUE_DESCRIPTION + " <" + node.id + ">")));
      }
    } else if (astNode instanceof ArgumentDeclNode) {
      ArgumentDeclNode node = (ArgumentDeclNode) astNode;
      if (node.id.startsWith("$")
          && !ALLOWED.contains(node.id)) {
        result = new CheckResult(Collections.singletonList(
            new Issue(ISSUE_TYPE, null, 0, ISSUE_ID, ISSUE_DESCRIPTION + " <" + node.id + ">")));
      }
    } else if (astNode instanceof AssignmentNode) {
      AssignmentNode node = (AssignmentNode) astNode;
      if (node.id.startsWith("$")
          && !ALLOWED.contains(node.id)) {
        result = new CheckResult(Collections.singletonList(
            new Issue(ISSUE_TYPE, null, 0, ISSUE_ID, ISSUE_DESCRIPTION + " <" + node.id + ">")));
      }
    } else if (astNode instanceof ExprNode
        && (astNode.type == ExprNode.Types.ID
        || astNode.type == ExprNode.Types.EXPR_DOT_ID
        || astNode.type == ExprNode.Types.FUNCTION_CALL)) {
      ExprNode node = (ExprNode) astNode;
      if (node.id.startsWith("$")
          && !ALLOWED.contains(node.id)) {
        result = new CheckResult(Collections.singletonList(
            new Issue(ISSUE_TYPE, null, 0, ISSUE_ID, ISSUE_DESCRIPTION + " <" + node.id + ">")));
      }
    } else if (astNode instanceof ModuleIdNode
        && astNode.type == ModuleIdNode.Types.ID) {
      ModuleIdNode node = (ModuleIdNode) astNode;
      if (node.id.startsWith("$")
          && !ALLOWED.contains(node.id)) {
        result = new CheckResult(Collections.singletonList(
            new Issue(ISSUE_TYPE, null, 0, ISSUE_ID, ISSUE_DESCRIPTION + " <" + node.id + ">")));
      }
    } else if (astNode instanceof StatementNode
        && (astNode.type == StatementNode.Types.MODULE_DEFINITION
        || astNode.type == StatementNode.Types.FUNCTION_DEFINITION)) {
      StatementNode node = (StatementNode) astNode;
      if (node.id.startsWith("$")
          && !ALLOWED.contains(node.id)) {
        result = new CheckResult(Collections.singletonList(
            new Issue(ISSUE_TYPE, null, 0, ISSUE_ID, ISSUE_DESCRIPTION + " <" + node.id + ">")));
      }
    }

    if (result != null) {
      return CheckResult.mergeAll(Stream.concat(Stream.of(result),
          astNode.children.stream().map(n -> this.check(n, null))));
    } else {
      return CheckResult.mergeAll(astNode.children.stream().map(n -> this.check(n, null)));
    }


  }
}