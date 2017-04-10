package ForbiddenIslandWorld.Cells;

import java.awt.*;

import javalib.worldimages.*;

// represents the ocean
public class OceanCell extends Cell {
  public OceanCell(int x, int y, double height, Cell left, Cell top, Cell right, Cell bottom,
                   boolean isFlooded) {
    super(x, y, 0, left, top, right, bottom, true);
  }

  // convenience constructor for initializing
  public OceanCell(int x, int y) {
    super(x, y, 0);
    this.left = null;
    this.top = null;
    this.right = null;
    this.bottom = null;
    this.isFlooded = true;
  }

  // draws the ocean cell
  WorldImage drawCell() {
    return new RectangleImage(CELL_SIZE,
            CELL_SIZE, OutlineMode.SOLID, new Color(0, 0, 255));
  }

  // returns true if OceanCell
  public boolean isOcean() {
    return true;
  }
}