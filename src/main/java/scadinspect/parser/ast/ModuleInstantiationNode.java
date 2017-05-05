package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 21.04.17.
 */
public class ModuleInstantiationNode extends ASTNode {

  public enum Types implements NodeType {
    WITH_EXCLAMATION,
    WITH_HASH,
    WITH_PERCENT,
    WITH_ASTERISK,
    SINGLE,
    IF_ELSE
  }

  public static ModuleInstantiationNode createWithExclamationMark(ModuleInstantiationNode module) {
    return new ModuleInstantiationNode(Types.WITH_EXCLAMATION,
        Collections.singletonList(module));
  }

  public static ModuleInstantiationNode createWithHash(ModuleInstantiationNode module) {
    return new ModuleInstantiationNode(Types.WITH_HASH,
        Collections.singletonList(module));
  }

  public static ModuleInstantiationNode createWithPercent(ModuleInstantiationNode module) {
    return new ModuleInstantiationNode(Types.WITH_PERCENT,
        Collections.singletonList(module));
  }

  public static ModuleInstantiationNode createWithAsterisk(ModuleInstantiationNode module) {
    return new ModuleInstantiationNode(Types.WITH_ASTERISK,
        Collections.singletonList(module));
  }

  public static ModuleInstantiationNode createSingle(SingleModuleInstantiationNode singleModule,
      ChildStatementNode childStatement) {
    return new ModuleInstantiationNode(Types.SINGLE, Arrays.asList(singleModule, childStatement));
  }

  public static ModuleInstantiationNode createIfElse(IfElseStatementNode ifelse) {
    return new ModuleInstantiationNode(Types.IF_ELSE, Collections.singletonList(ifelse));
  }

  protected ModuleInstantiationNode(NodeType type, List<ASTNode> children) {
    super(type, children);
  }
}
