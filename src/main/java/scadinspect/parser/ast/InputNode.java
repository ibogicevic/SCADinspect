package scadinspect.parser.ast;

import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 * Created by Felix Stegmaier on 30.03.2017.
 */
public class InputNode extends ASTNode {
  public List<String> uses;
  public List<StatementNode> statements;

  public InputNode() {
    this.uses = new ArrayList<>();
    this.statements = new ArrayList<>();
  }

  public InputNode(InputNode inputNode) {
    this();
    if (inputNode!=null) {
      this.uses.addAll(inputNode.uses);
      this.statements.addAll(inputNode.statements);
    }
  }

}
