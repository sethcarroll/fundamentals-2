package ForbiddenIslandWorld;

import java.util.ArrayList;

import ForbiddenIslandWorld.Cells.Cell;
import ForbiddenIslandWorld.Cells.OceanCell;
import ForbiddenIslandWorld.Lists.Cons;
import ForbiddenIslandWorld.Lists.Empty;
import ForbiddenIslandWorld.Lists.IList;
import tester.Tester;

/**
 * Created by sethcarroll on 4/9/17.
 */

// to represent examples
public class ExamplesForbiddenIsland {
  Cell cell1 = new Cell(0, 1, 1);
  Cell cell2 = new Cell(1, 0, 2);
  Cell cell3 = new Cell(1, 1, 1);
  Cell celltl = new Cell(1, 1, 1.0);
  Cell cellbl = new Cell(1, 2, 2.0);
  Cell celltr = new Cell(2, 1, 3.0);
  Cell cellbr = new Cell(2, 2, 4.0);
  OceanCell ocean1 = new OceanCell(0, 0);
  OceanCell ocean2 = new OceanCell(64, 64);
  Player play1 = new Player(cell3);
  IList<Cell> list1 = new Empty<Cell>();
  ArrayList<Cell> array1 = new ArrayList<Cell>();
  ArrayList<Cell> array2 = new ArrayList<Cell>();
  ArrayList<ArrayList<Cell>> array3 = new ArrayList<ArrayList<Cell>>();
  IList<Cell> list2 = new Cons<Cell>(cell1, list1);
  IList<Cell> list3 = new Cons<Cell>(cell1, new Cons<Cell>(ocean1, list1));
  ForbiddenIslandWorld world1 = new ForbiddenIslandWorld();


  // to set initialized testing data
  void initData() {
    this.cell1.initLeft(ocean1);
    this.cell1.initRight(cell2);
    this.cell1.initBottom(cell1);
    this.cell1.initTop(cell1);
    this.cell2.initLeft(cell1);
    this.cell2.initRight(cell2);
    this.cell2.initTop(cell2);
    this.cell2.initBottom(cell2);

    this.world1.waterHeight = 1;

    array1.add(0, celltl);
    array1.add(1, cellbl);
    array2.add(0, celltr);
    array2.add(1, cellbr);
    array3.add(0, array1);
    array3.add(1, array2);
  }

  // tests isFlooded
  void testIsFlooded(Tester t) {
    t.checkExpect(this.cell1.isFlooded, false);
    t.checkExpect(this.cell2.isFlooded, false);
    t.checkExpect(this.ocean1.isFlooded, true);
  }

  //test the method decreaseValue
  void testDecreaseValue(Tester t) {
    t.checkExpect(cell1.decreaseValue(5), 223);
    t.checkExpect(cell2.decreaseValue(257), 0);

  }

  //test the method increaseValue
  void testIncreaseValue(Tester t) {
    t.checkExpect(cell1.increaseValue(5), 32);
    t.checkExpect(cell1.increaseValue(5000), 255);
  }

  // test the method isCoast
  void testIsCoast(Tester t) {
    initData();
    t.checkExpect(cell1.isCoast(), true);
    t.checkExpect(cell2.isCoast(), false);
  }

  // test the method isUnderWater
  void testIsUnderWater(Tester t) {
    t.checkExpect(cell2.isUnderWater(0), false);
    t.checkExpect(cell2.isUnderWater(2), true);

  }

  //test the method manhattanDistance
  void testManhattan(Tester t) {
    t.checkExpect(world1.manhattanDistance(0, 0), 64.0);
    t.checkExpect(world1.manhattanDistance(10, 0), 54.0);
    t.checkExpect(world1.manhattanDistance(10, 10), 44.0);
    t.checkExpect(world1.manhattanDistance(32, 32), 0.0);
  }

  // test the method connectCells
  void testConnectCells(Tester t) {
    initData();

    t.checkExpect(array3.get(1).get(0).left, null);

    this.world1.connectCells(array3);

    t.checkExpect(array3.get(0).get(0).left, array3.get(0).get(0));
    t.checkExpect(array3.get(0).get(1).left, array3.get(0).get(1));
    t.checkExpect(array3.get(1).get(0).left, array3.get(0).get(0));
    t.checkExpect(array3.get(1).get(1).left, array3.get(0).get(1));

    t.checkExpect(array3.get(0).get(0).right, array3.get(1).get(0));
    t.checkExpect(array3.get(0).get(1).right, array3.get(1).get(1));
    t.checkExpect(array3.get(1).get(0).right, array3.get(1).get(0));
    t.checkExpect(array3.get(1).get(1).right, array3.get(1).get(1));

    t.checkExpect(array3.get(0).get(0).top, array3.get(0).get(0));
    t.checkExpect(array3.get(0).get(1).top, array3.get(0).get(0));
    t.checkExpect(array3.get(1).get(0).top, array3.get(1).get(0));
    t.checkExpect(array3.get(1).get(1).top, array3.get(1).get(0));

    t.checkExpect(array3.get(0).get(0).bottom, array3.get(0).get(1));
    t.checkExpect(array3.get(0).get(1).bottom, array3.get(0).get(1));
    t.checkExpect(array3.get(1).get(0).bottom, array3.get(1).get(1));
    t.checkExpect(array3.get(1).get(1).bottom, array3.get(1).get(1));

  }

  // test the method waterRise
  void testWaterRise(Tester t) {
    this.initData();
    t.checkExpect(this.world1.waterHeight, 1);
  }

  // test method makeBoard
  void testMakeBoard(Tester t) {
    world1.initGameMountain();
    t.checkExpect(this.world1.board.length(), 4225);

    world1.initGameRandom();
    t.checkExpect(this.world1.board.length(), 4225);

  }

  // test method makeCell
  void testMakeCell(Tester t) {
    world1.initGameMountain();
    t.checkExpect(this.world1.makeCells(world1.mountainHeights()).get(1)
            .get(1), new OceanCell(1, 1));
    t.checkExpect(this.world1.makeCells(world1.mountainHeights()).get(2)
            .get(2), new OceanCell(2, 2));
    t.checkExpect(this.world1.makeCells(world1.mountainHeights()).get(33)
            .get(33), new Cell(33, 33, 30.0));
  }

  // test method mountainHeights
  void testMountainHeights(Tester t) {
    world1.initGameMountain();
    t.checkExpect(this.world1.mountainHeights().size(), 65);
    t.checkExpect(this.world1.mountainHeights().get(1).get(1), -30.0);
    t.checkExpect(this.world1.mountainHeights().get(1).get(1), -30.0);
  }

  // test method terrainHeights
  void testTerrainHeights(Tester t) {
    world1.initGameTerrain();
    t.checkExpect(this.world1.terrainHeights().size(), 65);
    t.checkExpect(this.world1.targets.length(), 6);
  }


  //Uncomment this to start the game
  // main argument for standard mountain island
  void testBigBangMountain(Tester t) {
    world1.initGameMountain();
    this.world1.bigBang(
            (ForbiddenIslandWorld.ISLAND_SIZE) * ForbiddenIslandWorld.SCALE,
            (ForbiddenIslandWorld.ISLAND_SIZE) * ForbiddenIslandWorld
                    .SCALE, 0.2);
  }

    /*
    // main argument for random height island
    void testBigBangRandom(Tester t) {
        world1.initGameRandom();
        this.world1.bigBang(
                (ForbiddenIslandWorld.ForbiddenIslandWorld.ISLAND_SIZE)
                * ForbiddenIslandWorld.ForbiddenIslandWorld.SCALE,
                (ForbiddenIslandWorld.ForbiddenIslandWorld.ISLAND_SIZE)
                * ForbiddenIslandWorld.ForbiddenIslandWorld
                        .SCALE, 0.15);
    }

    // main argument for random terrain island
    void testBigBangRandom(Tester t) {
        world1.initGameTerrain();
        this.world1.bigBang(
                (ForbiddenIslandWorld.ForbiddenIslandWorld.ISLAND_SIZE)
                * ForbiddenIslandWorld.ForbiddenIslandWorld.SCALE,
                (ForbiddenIslandWorld.ForbiddenIslandWorld.ISLAND_SIZE)
                * ForbiddenIslandWorld.ForbiddenIslandWorld
                        .SCALE, 0.15);
    }
    */

}

