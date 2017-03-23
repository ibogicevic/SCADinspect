package scadinspect.parser;

import java_cup.runtime.Symbol;
import scadinspect.parser.ast.ASTNode;

/**
 * Created by felix on 23.03.17.
 */
public class ParseTree {

  private final Symbol rootSymbol;
  private final ASTNode rootNode;

  public ParseTree(Symbol rootSymbol, ASTNode rootNode) {
    this.rootSymbol = rootSymbol;
    this.rootNode = rootNode;
  }

  public Symbol getRootSymbol() {
    return rootSymbol;
  }

  public ASTNode getRootNode() {
    return rootNode;
  }

  @Override
  public String toString() {
    return "ParseTree{" +
        "rootSymbol=" + rootSymbol +
        ", rootNode=" + rootNode +
        '}';
  }
}
