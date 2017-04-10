package ForbiddenIslandWorld.Cells;


import java.awt.*;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

///Represents a single square of the game area
public class Cell {
  // size of each cell
  static final int CELL_SIZE = 10;
  // represents absolute height of this cell, in feet
  public double height;
  // In logical coordinates, with the origin at the top-left corner of the screen
  public int x;
  public int y;
  // the four adjacent cells to this one
  public Cell left;
  public Cell top;
  public Cell right;
  public Cell bottom;
  // reports whether this cell is flooded or not
  public boolean isFlooded;

  // constructor
  public Cell(int x, int y, double height, Cell left, Cell top, Cell right, Cell bottom,
              boolean isFlooded) {
    this.x = x;
    this.y = y;
    this.height = height;
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.isFlooded = isFlooded;
  }

  // convenience constructor for initializing
  public Cell(int x, int y, double height) {
    this.x = x;
    this.y = y;
    this.height = height;
    this.left = null;
    this.top = null;
    this.right = null;
    this.bottom = null;
    this.isFlooded = false;
  }

  // draws the cell
  public WorldImage drawCell(int waterHeight) {
    // blue to black
    if (this.isFlooded) {
      return new RectangleImage(CELL_SIZE, CELL_SIZE, OutlineMode.SOLID, new Color(
              0, 0, this.decreaseValue(waterHeight)));
    }
    // green to red
    else if (this.height <= waterHeight) {
      return new RectangleImage(CELL_SIZE, CELL_SIZE, OutlineMode.SOLID, new Color(
              this.decreaseValue(waterHeight), this.increaseValue(waterHeight), 0));
    }
    // white to green
    else {
      return new RectangleImage(CELL_SIZE, CELL_SIZE, OutlineMode.SOLID, new Color(
              this.increaseValue(waterHeight), 255, this.increaseValue(waterHeight)));
    }
  }

  // decrease color value
  public int decreaseValue(int waterHeight) {
    return (int) Math.max(255 - (Math.abs(waterHeight - this.height)
            * 64 / 8), 0);
  }

  // increase color value
  public int increaseValue(int waterHeight) {
    return (int) Math.min(Math.abs(waterHeight - this.height)
            * 64 / 8, 255);
  }

  // returns true if the cell is part of a coast
  public boolean isCoast() {
    return !this.isFlooded
            && (this.left.isFlooded || this.top.isFlooded
            || this.right.isFlooded || this.bottom.isFlooded);

  }

  // returns true if the cell is under water
  public boolean isUnderWater(int waterHeight) {
    return this.height <= waterHeight;
  }

  // flood a cell
  public void flood(int waterHeight) {
    this.isFlooded = true;
    if (this.left.isUnderWater(waterHeight) && !this.left.isFlooded) {
      this.left.flood(waterHeight);
    }
    if (this.top.isUnderWater(waterHeight) && !this.top.isFlooded) {
      this.top.flood(waterHeight);
    }
    if (this.right.isUnderWater(waterHeight) && !this.right.isFlooded) {
      this.right.flood(waterHeight);
    }
    if (this.bottom.isUnderWater(waterHeight) && !this.bottom.isFlooded) {
      this.bottom.flood(waterHeight);
    }
  }

  // returns true if OceanCell
  public boolean isOcean() {
    return false;
  }

  // initializes the Cell's left to the given
  public void initLeft(Cell that) {
    this.left = that;
  }

  // initalizes the Cell's top to the given
  public void initTop(Cell that) {
    this.top = that;
  }

  // initializes the Cell's right to the given
  public void initRight(Cell that) {
    this.right = that;
  }

  // initializes the Cell's bottom o the given
  public void initBottom(Cell that) {
    this.bottom = that;
  }
}
