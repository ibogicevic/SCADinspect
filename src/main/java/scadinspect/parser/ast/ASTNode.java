package scadinspect.parser.ast;

import java.util.List;

/**
 * Created by Felix Stegmaier on 23.03.17.
 *
 * <h1>Syntax Tree nodes</h1>
 *
 * This class represents a general Syntax Tree Node.
 *
 * Each grammar rule is represented by a concrete implementation of this class.
 * For the rules have a look at
 *
 * @link src/main/cup/parser.cup
 *
 * Each node has an associated type, that is further defined by the concrete class.
 * @link src/main/cup/parser.cup after each grammar rule.
 *
 * In the end the parse tree is returned by the parser.
 * @see scadinspect.parser.ast.ASTNode#type The types are defined in each concrete class in an enum
 * names 'Types' that implements the NodeType interface.
 * @see scadinspect.parser.ast.NodeType
 *
 *
 * Each node may have several child nodes, that can be arbitrary other ASTNodes, in the order that
 * they appear in the syntax rule.
 * @see scadinspect.parser.ast.ASTNode#children
 *
 * Each concrete implementation of a syntax rule can have additional associated values, which
 * represent the values passed from the lexer to the parser.
 *
 * Concrete Syntax Tree nodes have no public constructor. They need to be constructed using a static
 * factory method. Those are named create[TYPE]([concrete parameters]). This method creates a new
 * instance of the concrete class and inserts the concrete parameters correctly. The respective
 * parameters depend on the grammar rule and generaly are consistent with the children and posible
 * of the syntax rule. Values that are not needed by the specific rule are set to null. For an
 * example
 * @see scadinspect.parser.ast.ExprNode
 *
 * The subclasses will not contain JavaDocs.
 *
 *
 * <h1>Syntax Tree Generation</h1>
 *
 * The Syntax Tree is generated during the runtime of the parser. In general for every garmmar rule
 * reduction a new ASTNode is created that corresponds to the grammar rule and the specific type of
 * it using the factory method. The necessary parameters are gathered from the child rules and the
 * tokens that are passed from the lexer. Those reductions are defined in
 * @see scadinspect.parser.Parser on how the syntax tree generation is triggered
 */
public abstract class ASTNode {

  public final NodeType type;
  public final List<ASTNode> children;

  protected ASTNode(NodeType type, List<ASTNode> children) {
    this.type = type;
    this.children = children;
  }

}
