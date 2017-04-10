package ForbiddenIslandWorld.Predicates;

import ForbiddenIslandWorld.Cells.Cell;

// returns if the cell is a land
public class IsLand implements IPred<Cell> {

  public boolean apply(Cell cell) {
    return !cell.isFlooded;
  }
}