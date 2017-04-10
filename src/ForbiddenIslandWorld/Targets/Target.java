package ForbiddenIslandWorld.Targets;

import java.awt.*;

import ForbiddenIslandWorld.Cells.Cell;

import javalib.worldimages.*;

// to represent anything that needs to be picked up
public class Target {
  public Cell current;
  public static boolean pickedUp;


  // constructor
  public Target(Cell current) {
    this.current = current;
    this.pickedUp = false;
    //this.image = image;
  }

  // returns true if Helicopter
  public boolean isHeli() {
    return false;
  }

  // draws a Target
  public WorldImage targetImage() {
    if (isHeli()) {
      return new FromFileImage("helicopter.png");
    } else {
      return new RectangleImage(10, 10, OutlineMode.SOLID, new Color(150,
              0, 180));
    }
  }
}