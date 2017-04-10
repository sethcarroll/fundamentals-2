package ForbiddenIslandWorld;

// Assignment 6 Part 2
// Meyers, Ian
// iameyers
// Carroll, Seth
// sethcarroll
// McGhehey, Kayla (we have Dr.Razzaq's permission to work as a group of three)
// mcghehey

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import ForbiddenIslandWorld.Cells.Cell;
import ForbiddenIslandWorld.Cells.OceanCell;
import ForbiddenIslandWorld.Predicates.IsLand;
import ForbiddenIslandWorld.Targets.HelicopterTarget;
import ForbiddenIslandWorld.Targets.Target;
import ForbiddenIslandWorld.Lists.IList;
import ForbiddenIslandWorld.Lists.Empty;
import ForbiddenIslandWorld.Lists.Cons;
import javalib.impworld.*;
import javalib.worldimages.*;


// represents forbidden island world
public class ForbiddenIslandWorld extends World {
  // defines an int constant
  static final int ISLAND_SIZE = 64;
  // scaling the cells
  static final int SCALE = 10;
  // max height of the island
  static final double MAXHEIGHT = ISLAND_SIZE / 2;

  // all the cells in the game
  IList<Cell> board;
  // the current height of the ocean
  int waterHeight;
  // ticks that have passed
  int ticks;
  // player
  Player player;
  // targets
  IList<Target> targets;
  // returns true if the player wins the game
  boolean win;
  // returns true if the player loses the game
  boolean lose;
  // represents targets left to pick up
  int remaining;
  // Random for rng
  Random random = new Random();


  // constructor
  public ForbiddenIslandWorld(IList<Cell> board, int waterHeight) {
    this.board = board;
    this.waterHeight = waterHeight;
    this.ticks = 0;
    this.lose = false;
    this.win = false;
    //this.remaining = 5;
  }

  // convenience constructor
  ForbiddenIslandWorld() {
    this.board = new Empty<Cell>();
    this.waterHeight = 0;
    this.ticks = 0;
    this.lose = false;
    this.win = false;
    //this.remaining = 5;
  }

  // computes the "Manhattan distance� from the center of the board
  double manhattanDistance(int x, int y) {
    int manhattanX = (ForbiddenIslandWorld.ISLAND_SIZE) / 2;
    int manhattanY = (ForbiddenIslandWorld.ISLAND_SIZE) / 2;
    return Math.abs(manhattanX - x) + Math.abs(manhattanY - y);
  }

  // convert to IList<Cell>
  void initBoard(ArrayList<ArrayList<Cell>> cells) {
    this.board = new Empty<Cell>();
    for (int i = 0; i <= ForbiddenIslandWorld.ISLAND_SIZE; i = i + 1) {
      for (int i2 = 0; i2 <= ForbiddenIslandWorld.ISLAND_SIZE; i2 = i2 + 1) {
        this.board = new Cons<Cell>(cells.get(i).get(i2), this.board);
      }
    }
  }

  // creates the IList<Cell> representing the board
  public IList<Cell> makeBoard(ArrayList<ArrayList<Cell>> cells) {
    IList<Cell> board = new Empty<Cell>();
    for (int i = 0; i < ISLAND_SIZE + 1; i = i + 1) {
      for (int i2 = 0; i2 < ISLAND_SIZE + 1; i2 = i2 + 1) {
        board = board.add(cells.get(i).get(i2));
      }
    }
    return board;
  }

  // creates the ArrayList<ArrayList<Cell>> representing the game's cells
  public ArrayList<ArrayList<Cell>> makeCells(ArrayList<ArrayList<Double>> heights) {
    ArrayList<ArrayList<Cell>> column = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < ISLAND_SIZE + 1; i = i + 1) {
      ArrayList<Cell> row = new ArrayList<Cell>();
      for (int i2 = 0; i2 < ISLAND_SIZE + 1; i2 = i2 + 1) {
        if (heights.get(i).get(i2) > 0) {
          row.add(new Cell(i, i2, heights.get(i).get(i2)));
        } else {
          row.add(new OceanCell(i, i2));
        }
      }
      column.add(row);
    }
    return column;
  }

  // creates the ArrayList<ArrayList<Double>> representing the game's heights (mountain)
  public ArrayList<ArrayList<Double>> mountainHeights() {
    ArrayList<ArrayList<Double>> column = new ArrayList<ArrayList<Double>>();
    double center = (double) MAXHEIGHT;
    for (int i = 0; i < ISLAND_SIZE + 1; i = i + 1) {
      ArrayList<Double> row = new ArrayList<Double>();
      for (int i2 = 0; i2 < ISLAND_SIZE + 1; i2 = i2 + 1) {
        row.add(center - this.manhattanDistance(i, i2));
      }
      column.add(row);
    }
    return column;
  }

  // creates the ArrayList<ArrayList<Double>> representing the game's heights (random heights)
  public ArrayList<ArrayList<Double>> randomHeights() {
    ArrayList<ArrayList<Double>> column = new ArrayList<ArrayList<Double>>();
    for (int i = 0; i < ISLAND_SIZE + 1; i = i + 1) {
      ArrayList<Double> row = new ArrayList<Double>();
      for (int i2 = 0; i2 < ISLAND_SIZE + 1; i2 = i2 + 1) {
        if (this.manhattanDistance(i, i2) >= MAXHEIGHT) {
          row.add((double) this.waterHeight);
        } else {
          int r = this.random.nextInt(ISLAND_SIZE / 2) + 1;
          double d = (double) r;
          row.add(d);
        }
      }
      column.add(row);
    }
    return column;
  }

  // creates the ArrayList<ArrayList<Double>> representing the game's
  // heights for a random terrain, before randomized terrain applied
  public ArrayList<ArrayList<Double>> terrainHeights() {

    // initialize grid to heights of 0
    ArrayList<ArrayList<Double>> column = new ArrayList<ArrayList<Double>>();
    for (int i = 0; i < ISLAND_SIZE + 1; i = i + 1) {
      ArrayList<Double> row = new ArrayList<Double>();
      for (int i2 = 0; i2 < ISLAND_SIZE + 1; i2 = i2 + 1) {
        row.add(0.0);
      }
      column.add(row);
    }
    column.get((int) MAXHEIGHT).set(0, 1.0);
    column.get((int) MAXHEIGHT).set(ISLAND_SIZE, 1.0);
    column.get(ISLAND_SIZE).set((int) MAXHEIGHT, 1.0);
    column.get(0).set((int) MAXHEIGHT, 1.0);
    column.get((int) MAXHEIGHT).set((int) MAXHEIGHT, MAXHEIGHT);
    this.randomizeHeights(0, 0, (int) MAXHEIGHT, (int) MAXHEIGHT, column);
    this.randomizeHeights((int) MAXHEIGHT, (int) MAXHEIGHT, ISLAND_SIZE, ISLAND_SIZE, column);
    this.randomizeHeights(0, (int) MAXHEIGHT, (int) MAXHEIGHT, ISLAND_SIZE, column);
    this.randomizeHeights((int) MAXHEIGHT, 0, ISLAND_SIZE, (int) MAXHEIGHT, column);
    return column;
  }

  // helper for terrainHeights
  public void randomizeHeights(int x, int y, int x2, int y2,
                               ArrayList<ArrayList<Double>> heights) {
    int random = this.random.nextInt(6);
    int a = random - 3;
    double topleft = heights.get(x).get(y);
    double topright = heights.get(x2).get(y2);
    double bottomleft = heights.get(x).get(y2);
    double bottomright = heights.get(x2).get(y);
    double left = a + (topleft + bottomleft) / 2;
    double right = a + (topright + bottomleft) / 2;
    double top = a + (topleft + topright) / 2;
    double bottom = a + (bottomleft + bottomright) / 2;
    double middle = a + (left + right + top + bottom) / 4;
    if (x2 - x > 1) {
      heights.get(x).set((y + y2) / 2, left);
      heights.get(x2).set((y + y2) / 2, right);
      heights.get((x + x2) / 2).set(y, top);
      heights.get((x + x2) / 2).set(y2, bottom);
      heights.get((x + x2) / 2).set((y + y2) / 2, middle);
      this.randomizeHeights(x, y, (x + x2) / 2, (y + y2) / 2, heights);
      this.randomizeHeights((x + x2) / 2, (y + y2) / 2, x2, y2, heights);
      this.randomizeHeights(x, (y + y2) / 2, (x + x2) / 2, y2, heights);
      this.randomizeHeights((x + x2) / 2, y, x2, (y + y2) / 2, heights);
    }
  }


  // connects the cells
  void connectCells(ArrayList<ArrayList<Cell>> cells) {
    int cs = cells.size();
    for (int i = 0; i < cs; i = i + 1) {
      for (int i2 = 0; i2 < cs; i2 = i2 + 1) {
        Cell cell = cells.get(i).get(i2);
        if (i < (cells.size() - 1)) {
          cell.initRight(cells.get(i + 1).get(i2));
        } else {
          cell.initRight(cell);
        }
        if (cell.x > 1) {
          cell.initLeft(cells.get(i - 1).get(i2));
        } else {
          cell.initLeft(cell);
        }
        if (cell.y > 1) {
          cell.initTop(cells.get(i).get(i2 - 1));
        } else {
          cell.initTop(cell);
        }
        if ((i2 < (cells.get(i).size() - 1))) {
          cell.initBottom(cells.get(i).get(i2 + 1));
        } else {
          cell.initBottom(cell);
        }
      }
    }
  }


  // return a list of land
  IList<Cell> getLand() {
    return this.board.filter(new IsLand());
  }


  // initializes the targets
  public void initTargets() {
    IList<Cell> land = this.getLand();
    Cell cell = new Cell(0, 0, 0);
    int high = 0;
    int counter = 0;
    for (Cell c : land) {
      counter = counter + 1;
      if (c.height > cell.height) {
        cell = c;
        high = counter;
      }
    }
    land = land.delete(high);
    IList<Target> targets = new Empty<Target>();
    for (int i = 0; i < 5; i = i + 1) {
      Random random = new Random();
      int index = random.nextInt(land.length()) - 1;
      Cell cell2 = land.indexItem(index);
      targets = new Cons<Target>(new Target(cell2), targets);
      land.delete(index);
    }
    this.targets = new Cons<Target>(new HelicopterTarget(cell), targets);
  }

  // mutates data when the player steps on a Target
  public void updateWorld(IList<Target> targets) {
    for (Target t : targets) {
      if (!t.isHeli() && !t.pickedUp && this.player.current.equals(t.current)) {
        t.pickedUp = true;
        this.remaining = this.remaining - 1;
      } else if (t.isHeli() && this.remaining == 0 && this.player.current.equals(t.current)) {
        t.pickedUp = true;
        this.win = true;
      }
    }
  }

  // creates the worldscene
  public WorldScene makeScene() {
    int size = (ForbiddenIslandWorld.ISLAND_SIZE) * ForbiddenIslandWorld.SCALE;
    WorldScene bg = new WorldScene(size, size);

    if (this.win) {
      bg.placeImageXY(new RectangleImage(size, size, OutlineMode.SOLID,
              new Color(0, 240, 255)), (int)
              MAXHEIGHT * SCALE, (int) MAXHEIGHT * SCALE);
      bg.placeImageXY(new TextImage("WINNER", Color.white), (int)
              MAXHEIGHT * SCALE, (int) MAXHEIGHT * SCALE);
    } else {
      if (this.lose) {
        bg.placeImageXY(new RectangleImage(size, size, OutlineMode.SOLID,
                new Color(0, 0, 150)), (int)
                MAXHEIGHT * SCALE, (int) MAXHEIGHT * SCALE);
        bg.placeImageXY(new TextImage("LOSER", Color.red), (int)
                MAXHEIGHT * SCALE, (int) MAXHEIGHT * SCALE);
      } else {
        for (Cell cell : this.board) {
          bg.placeImageXY(cell.drawCell(this.waterHeight), cell.x
                  * ForbiddenIslandWorld.SCALE, cell.y *
                  ForbiddenIslandWorld.SCALE);
        }
        for (Target t : this.targets) {
          if (!t.pickedUp) {
            bg.placeImageXY(t.targetImage(), t.current.x * ForbiddenIslandWorld.SCALE, t
                    .current.y * ForbiddenIslandWorld.SCALE);
          }
        }
        bg.placeImageXY(player.draw(), player.current.x * ForbiddenIslandWorld.SCALE, player
                .current.y * ForbiddenIslandWorld.SCALE);
      }
    }
    return bg;
  }

  // increases the waterHeight
  public void waterRise() {
    this.waterHeight = waterHeight + 1;

    for (Cell c : this.board) {
      if (!c.isOcean() && c.isUnderWater(waterHeight) && c.isCoast()) {
        c.flood(waterHeight);
      }
    }
  }


  // initializes entire game for mountain
  void initGameMountain() {
    this.waterHeight = 0;
    this.win = false;
    this.lose = false;
    ArrayList<ArrayList<Double>> heights = this.mountainHeights();
    ArrayList<ArrayList<Cell>> cells = this.makeCells(heights);
    this.connectCells(cells);
    IList<Cell> list = this.makeBoard(cells);
    this.board = list;
    this.initPlayer();
    this.initTargets();
    this.remaining = 5;
  }

  // initializes entire game for random heights
  void initGameRandom() {
    this.waterHeight = 0;
    this.win = false;
    this.lose = false;
    ArrayList<ArrayList<Double>> heights = this.randomHeights();
    ArrayList<ArrayList<Cell>> cells = this.makeCells(heights);
    this.connectCells(cells);
    IList<Cell> list = this.makeBoard(cells);
    this.board = list;
    this.initPlayer();
    this.initTargets();
    this.remaining = 5;
  }

  // initializes entire game for random terrain
  void initGameTerrain() {
    this.waterHeight = 0;
    this.win = false;
    this.lose = false;
    ArrayList<ArrayList<Double>> heights = this.terrainHeights();
    ArrayList<ArrayList<Cell>> cells = this.makeCells(heights);
    this.connectCells(cells);
    IList<Cell> list = this.makeBoard(cells);
    this.board = list;
    this.initPlayer();
    this.initTargets();
    this.remaining = 5;
  }

  // ticks World to its next state
  public void onTick() {
    ticks = ticks + 1;
    if (ticks % 10 == 0) {
      this.waterRise();
    }
    this.updateWorld(this.targets);
    if (this.player.current.isFlooded) {
      this.lose = true;
    }
  }

  // key events
  public void onKeyEvent(String ke) {
    if (ke.equals("m")) {
      this.initGameMountain();
    }
    if (ke.equals("r")) {
      this.initGameRandom();
    }
    if (ke.equals("t")) {
      this.initGameTerrain();
    } else {
      this.player.move(ke);
    }
  }

  // initializes the player
  public void initPlayer() {
    IList<Cell> land = this.getLand();
    int random = this.random.nextInt(land.length() + 1);
    Cell randomcell = land.indexItem(random);
    this.player = new Player(randomcell);
  }

}

