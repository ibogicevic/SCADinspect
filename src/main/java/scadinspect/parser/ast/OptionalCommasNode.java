package scadinspect.parser.ast;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 21.04.17.
 */
public class OptionalCommasNode extends ASTNode {

  public enum Types implements NodeType {
    EMPTY,
    COMMA
  }

  public static OptionalCommasNode createEmpty() {
    return new OptionalCommasNode(Types.EMPTY, Collections.emptyList());
  }

  public static OptionalCommasNode createComma(OptionalCommasNode commas) {
    return new OptionalCommasNode(Types.COMMA, Collections.singletonList(commas));
  }

  protected OptionalCommasNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
