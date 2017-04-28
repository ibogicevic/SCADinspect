package scadinspect.parser.ast;

import java.util.List;

/**
 * Created by Felix Stegmaier on 23.03.17.
 */
public abstract class ASTNode {

  public final NodeType type;
  public final List<ASTNode> children;

  protected ASTNode(NodeType type, List<ASTNode> children) {
    this.type = type;
    this.children = children;
  }

}
