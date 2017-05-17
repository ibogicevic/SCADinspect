package scadinspect.checkers;

import scadinspect.parser.ast.ASTNode;

/**
 * Created by Felix Stegmaier on 23.04.2017.
 *
 * Generic Checker to check a single ASTNode
 *
 * What is important to implement more checkers:
 * It must perform a check of one ASTNode at a time.
 * It must apply itself to all child nodes of the given node.
 * It may pass a state to the deeper recursion levels.
 * It must return a CheckResult containing the union of found Issues of the current node
 * and all the Issues returned be the checks of the child nodes.
 * It should have a unique ISSUE_ID
 */
public interface Checker {

  CheckResult check(ASTNode astNode, CheckState state);

}
