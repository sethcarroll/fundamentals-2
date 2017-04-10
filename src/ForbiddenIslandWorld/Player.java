package ForbiddenIslandWorld;

import ForbiddenIslandWorld.Cells.Cell;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.WorldImage;

// to represent a player
class Player {
  Cell current;

  // constructor
  Player(Cell current) {
    this.current = current;
  }

  // move the player based on the key
  void move(String ke) {
    if (ke.equals("left")) {
      if (this.current.left.isFlooded || this.current.left.isOcean() || this.current.x == 0) {
        this.current = this.current;
      } else {
        this.current = this.current.left;
      }
    } else if (ke.equals("up")) {
      if (this.current.top.isFlooded || this.current.top.isOcean() || this.current.y == 0) {
        this.current = this.current;
      } else {
        this.current = this.current.top;
      }
    } else if (ke.equals("right")) {
      if (this.current.right.isFlooded || this.current.right.isOcean() ||
              this.current.x == ForbiddenIslandWorld.ISLAND_SIZE) {
        this.current = this.current;
      } else {
        this.current = this.current.right;
      }
    } else if (ke.equals("down")) {
      if (this.current.bottom.isFlooded || this.current.bottom.isOcean() ||
              this.current.y == ForbiddenIslandWorld.ISLAND_SIZE) {
        this.current = this.current;
      } else {
        this.current = this.current.bottom;
      }
    }
  }

  // draw the player
  WorldImage draw() {
    return new FromFileImage("pilot.png");
  }
}