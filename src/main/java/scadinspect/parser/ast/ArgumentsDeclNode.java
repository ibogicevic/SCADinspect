package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 21.04.17.
 */
public class ArgumentsDeclNode extends ASTNode {

  public enum Types implements NodeType {
    EMPTY,
    ARGUMENT_DECL,
    WITH_COMMA
  }

  public static ArgumentsDeclNode createEmpty() {
    return new ArgumentsDeclNode(Types.EMPTY, Collections.emptyList());
  }

  public static ArgumentsDeclNode createArgumentDecl(ArgumentDeclNode arg) {
    return new ArgumentsDeclNode(Types.ARGUMENT_DECL, Collections.singletonList(arg));
  }

  public static ArgumentsDeclNode createWithComma(ArgumentsDeclNode args,
      OptionalCommasNode commas, ArgumentDeclNode arg) {
    return new ArgumentsDeclNode(Types.WITH_COMMA, Arrays.asList(args, commas, arg));
  }

  protected ArgumentsDeclNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
