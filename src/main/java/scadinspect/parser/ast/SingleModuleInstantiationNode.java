package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by felix on 22.04.17.
 */
public class SingleModuleInstantiationNode extends ASTNode {

  protected SingleModuleInstantiationNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }

  public static SingleModuleInstantiationNode createSingle(ModuleIdNode moduleId,
      ArgumentsCallNode argsCall) {
    return new SingleModuleInstantiationNode(Types.SINGLE, Arrays.asList(moduleId, argsCall));
  }

  public enum Types implements NodeType {
    SINGLE
  }
}
