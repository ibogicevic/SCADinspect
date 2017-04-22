package scadinspect.parser.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 21.04.17.
 */
public class ExprNode extends ASTNode {

  public final String string;
  public final String id;
  public final Double number;

  public enum Types implements NodeType {
    TRUE,
    FALSE,
    UNDEF,
    ID,
    EXPR_DOT_ID,
    STRING,
    NUMBER,
    EXPR_COLON_EXPR,
    EXPR_COLON_EXPR_COLON_EXPR,
    OPTIONAL_COMMAS,
    VECTOR,
    MULTIPLICATION,
    DIVISION,
    MODULO,
    ADDITION,
    SUBTRACTION,
    LESS,
    LESS_EQUAL,
    EQUAL,
    NOT_EQUAL,
    GREATER_EQUAL,
    GREATER,
    AND,
    OR,
    UNARY_PLUS,
    UNARY_MINUS,
    NOT,
    PARENTHESES,
    TERNARY,
    ARRAY_ACCESS,
    FUNCTION_CALL,
    LET,
    ASSERT,
    ECHO
  }

  public static ExprNode createTrue() {
    return new ExprNode(Types.TRUE, Collections.emptyList(), null, null, null);
  }
  public static ExprNode createFalse() {
    return new ExprNode(Types.FALSE, Collections.emptyList(), null, null, null);
  }
  public static ExprNode createUndef() {
    return new ExprNode(Types.UNDEF, Collections.emptyList(), null, null, null);
  }
  public static ExprNode createId(String id) {
    return new ExprNode(Types.ID, Collections.emptyList(), null, id, null);
  }
  public static ExprNode createExprDotId(ExprNode expr, String id) {
    return new ExprNode(Types.EXPR_DOT_ID, Collections.singletonList(expr), null, id, null);
  }
  public static ExprNode createString(String string) {
    return new ExprNode(Types.STRING, Collections.emptyList(), string, null, null);
  }
  public static ExprNode createNumber(Double number) {
    return new ExprNode(Types.NUMBER, Collections.emptyList(), null, null, number);
  }
  //TODO find out whot theses [e:e] [e:e:e] [,,] do
  public static ExprNode createExprColonExpr(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.EXPR_COLON_EXPR, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createExprColonExprColonExpr(ExprNode expr1, ExprNode expr2, ExprNode expr3) {
    return new ExprNode(Types.EXPR_COLON_EXPR_COLON_EXPR, Arrays.asList(expr1, expr2, expr3),
        null, null, null);
  }
  public static ExprNode createOptionalCommas(OptionalCommasNode commas) {
    return new ExprNode(Types.OPTIONAL_COMMAS, Collections.singletonList(commas), null, null, null);
  }
  public static ExprNode createVector(VectorExprNode vector, OptionalCommasNode commas) {
    return new ExprNode(Types.VECTOR, Arrays.asList(vector, commas), null, null, null);
  }
  public static ExprNode createMultiplication(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.MULTIPLICATION, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createDivision(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.DIVISION, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createModulo(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.MODULO, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createAddition(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.ADDITION, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createSubtraction(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.SUBTRACTION, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createLess(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.LESS, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createLessEqual(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.LESS_EQUAL, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createEqual(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.EQUAL, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createNotEqual(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.NOT_EQUAL, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createGreaterEqual(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.GREATER_EQUAL, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createGreater(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.GREATER, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createAnd(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.AND, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createOr(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.OR, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createUnaryPlus(ExprNode expr) {
    return new ExprNode(Types.UNARY_PLUS, Collections.singletonList(expr), null, null, null);
  }
  public static ExprNode createUnaryMinus(ExprNode expr) {
    return new ExprNode(Types.UNARY_MINUS, Collections.singletonList(expr), null, null, null);
  }
  public static ExprNode createNot(ExprNode expr) {
    return new ExprNode(Types.NOT, Collections.singletonList(expr), null, null, null);
  }
  public static ExprNode createParentheses(ExprNode expr) {
    return new ExprNode(Types.PARENTHESES, Collections.singletonList(expr), null, null, null);
  }
  public static ExprNode createTernary(ExprNode expr1, ExprNode expr2, ExprNode expr3) {
    return new ExprNode(Types.TERNARY, Arrays.asList(expr1, expr2, expr3), null, null, null);
  }
  //TODO find out if this is really array access
  public static ExprNode createArrayAccess(ExprNode expr1, ExprNode expr2) {
    return new ExprNode(Types.ARRAY_ACCESS, Arrays.asList(expr1, expr2), null, null, null);
  }
  public static ExprNode createFunctionCall(String id, ArgumentsCallNode argumentsCall) {
    return new ExprNode(Types.FUNCTION_CALL, Collections.singletonList(argumentsCall), null, id, null);
  }
  public static ExprNode createLet(ArgumentsCallNode argumentsCall, ExprNode expr) {
    return new ExprNode(Types.LET, Arrays.asList(argumentsCall, expr), null, null, null);
  }
  public static ExprNode createAssert(ArgumentsCallNode argumentsCall, ExprOrEmptyNode expr) {
    return new ExprNode(Types.ASSERT, Arrays.asList(argumentsCall, expr), null, null, null);
  }
  public static ExprNode createEcho(ArgumentsCallNode argumentsCall, ExprOrEmptyNode expr) {
    return new ExprNode(Types.ECHO, Arrays.asList(argumentsCall, expr), null, null, null);
  }


  protected ExprNode(NodeType type, List<ASTNode> children, String string, String id, Double number) {
    super(type, children);
    this.string = string;
    this.id = id;
    this.number = number;
  }
}
