package scadinspect.checkers;

/**
 * Created by Felix Stegmaier on 23.04.2017.
 *
 * State to be passed into a check performed on a single ASTNode
 *
 * Can be used to eg count things.
 *
 * May be changed or subclassed to include more data if needed
 *
 * Might be included into a sublass of CheckResult
 *
 * @see scadinspect.checkers.CheckResult
 * @see scadinspect.checkers.Checker
 *
 */
public class CheckState {

  public CheckState() {

  }

}
