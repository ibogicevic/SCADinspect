package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class ArgumentsCallNode extends ASTNode{

  public enum Types implements NodeType {
    EMPTY,
    ARGUMENT_CALL,
    WITH_COMMA
  }

  public static ArgumentsCallNode createEmpty() {
    return new ArgumentsCallNode(Types.EMPTY, Collections.emptyList());
  }

  public static ArgumentsCallNode createArgumentCall(ArgumentCallNode arg) {
    return new ArgumentsCallNode(Types.ARGUMENT_CALL, Collections.singletonList(arg));
  }

  public static ArgumentsCallNode createWithComma(ArgumentsCallNode args,
      OptionalCommasNode commas, ArgumentCallNode arg) {
    return new ArgumentsCallNode(Types.WITH_COMMA, Arrays.asList(args, commas, arg));
  }

  protected ArgumentsCallNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
