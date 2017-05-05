package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Felix Stegmaier on 30.03.2017.
 */
public class StatementNode extends ASTNode {

  public final String id;

  public enum Types implements NodeType {
    SEMICOLON,
    INNER_INPUT,
    MODULE_INSTANTIATION,
    ASSIGNMENT,
    MODULE_DEFINITION,
    FUNCTION_DEFINITION
  }

  public static StatementNode createSemicolon() {
    return new StatementNode(Types.SEMICOLON, Collections.emptyList(), null);
  }

  public static StatementNode createInnerInput(InnerInputNode inner) {
    return new StatementNode(Types.INNER_INPUT, Collections.singletonList(inner), null);
  }

  public static StatementNode createModuleInstantiation(ModuleInstantiationNode inst) {
    return new StatementNode(Types.MODULE_INSTANTIATION, Collections.singletonList(inst), null);
  }

  public static StatementNode createAssignment(AssignmentNode assignment) {
    return new StatementNode(Types.ASSIGNMENT, Collections.singletonList(assignment), null);
  }

  public static StatementNode createModuleDefinition(String id, ArgumentsDeclNode args,
      OptionalCommasNode commas, StatementNode statement) {
    return new StatementNode(Types.MODULE_DEFINITION, Arrays.asList(args, commas, statement), id);
  }

  public static StatementNode createFunctionDefinition(String id, ArgumentsDeclNode args,
      OptionalCommasNode commas, ExprNode expr) {
    return new StatementNode(Types.FUNCTION_DEFINITION, Arrays.asList(args, commas, expr), id);
  }

  protected StatementNode(NodeType type, List<ASTNode> children, String id) {
    super(type, children);
    this.id = id;
  }
}
