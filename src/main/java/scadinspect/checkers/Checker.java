package scadinspect.checkers;

import scadinspect.parser.ast.ASTNode;

/**
 * Created by Felix Stegmaier on 23.04.2017.
 */
public interface Checker {

  CheckResult check(ASTNode astNode, CheckState state);

}
