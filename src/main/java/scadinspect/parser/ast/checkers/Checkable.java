package scadinspect.parser.ast.checkers;

/**
 * Created by felix on 22.04.17.
 */
public interface Checkable {

  //TODO maybe implement the actual logic not in the nodes,
  // but in some checkers class to be used with this

  CheckResult check();

}
