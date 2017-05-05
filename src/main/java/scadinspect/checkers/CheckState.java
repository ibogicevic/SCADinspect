package scadinspect.checkers;

/**
 * Created by Felix Stegmaier on 23.04.2017.
 */
public class CheckState {
  public final int nestLevel;

  public CheckState() {
    this(0);
  }

  protected CheckState(int nestLevel) {
    this.nestLevel = nestLevel;
  }

  public CheckState increaseNestLevelBy(int i) {
    return new CheckState(this.nestLevel + i);
  }

}
