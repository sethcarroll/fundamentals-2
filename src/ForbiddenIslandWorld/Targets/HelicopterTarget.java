package ForbiddenIslandWorld.Targets;

import ForbiddenIslandWorld.Cells.Cell;

// to represent the helicopter
public class HelicopterTarget extends Target {
  public HelicopterTarget(Cell current) {
    super(current);
  }

  // returns true if Helicopter
  public boolean isHeli() {
    return true;
  }
}
