import javalib.funworld.*;
import javalib.worldimages.*;
import tester.*;

import java.awt.*;

public class MyWorld extends World {
  ILo<Bullet> bullets;
  ILo<Alien> aliens;
  Ship ship;

  int rows = 30;
  int cols = 20;
  int cellSize = 24;
  double fps = 60;


  // Your world must have a constructor that just takes an integer,
  // which represents the number of bullets a player has to shoot.
  // That is how your graders will launch your world.
  MyWorld(ILo<Alien> aliens, ILo<Bullet> bullets, Ship ship) {
    this.aliens = aliens;
    this.bullets = bullets;
    this.ship = ship;
  }

  MyWorld() {
    this.aliens = this.generateAliens(3, 10);
    this.bullets = new MtLo<Bullet>();
    this.ship = new Ship(10*this.cellSize, 29*this.cellSize+cellSize/2, this.cellSize);
  }

  public WorldScene drawGrid(WorldScene scene, int x, int y) {
    if (x == this.cols) {
      return scene;
    } else if (y == this.rows) {
      return this.drawGrid(scene, x + 1, 0);
    } else {
      return this.drawGrid(
        scene.placeImageXY(
          new RectangleImage(
            this.cellSize,
            this.cellSize,
            OutlineMode.OUTLINE,
            Color.DARK_GRAY
          ),
          x * this.cellSize + this.cellSize / 2,
          y * this.cellSize + this.cellSize / 2
        ),
        x,
        y + 1
      );
    }
  }

  public WorldScene drawKeys(WorldScene scene) {
    boolean left = this.ship.v < 0;
    boolean right = this.ship.v > 0;
    return scene.placeImageXY(
      new RectangleImage(
        this.cellSize,
        this.cellSize,
        OutlineMode.SOLID,
        left ? Color.GREEN : Color.RED
      ),
      this.cellSize / 2,
      this.cellSize / 2
    ).placeImageXY(
      new RectangleImage(
        this.cellSize,
        this.cellSize,
        OutlineMode.SOLID,
        right ? Color.GREEN : Color.RED
      ),
      this.cellSize + this.cellSize / 2,
      this.cellSize / 2
    ).placeImageXY(
      new TextImage(
        String.valueOf(this.bullets.size()),
        12,
        Color.BLACK
      ),
      this.cellSize / 2,
      this.cellSize / 2
    );
  }

  DrawBullets drawBullets = new DrawBullets();
  public WorldScene drawBullets(WorldScene scene) {
    return this.bullets.foldr(
      drawBullets,
      scene
    );
  }

  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    return this.drawKeys(this.drawBullets(this.ship.drawShip(this.drawGrid(scene, 0, 0))));
  }

  public Ship moveShip(String s) {
    if(s.equals("left")) {
      return this.ship.moveLeft();
    } else if(s.equals("right")) {
      return this.ship.moveRight();
    } else {
      return this.ship;
    }
  }

  public Ship stopShip(String key) {
    if(key.equals("left") || key.equals("right")) {
      return this.ship.stop();
    } else {
      return this.ship;
    }
  }

  public ILo<Bullet> handleShoot(String s) {
    if(s.equals(" ")) {
      return this.bullets.add(new Bullet(this.ship.x, this.ship.y));
    } else {
      return this.bullets;
    }
  }

  TickBullets tickBullets = new TickBullets();
  public MyWorld onTick() {
      return new MyWorld(
        this.aliens,
        this.bullets.foldr(
          tickBullets,
          new MtLo<Bullet>()
        ),
        this.ship.tick(this.cellSize * this.cols)
      );
  }

  @Override
  public World onKeyEvent(String key) {
    System.out.println(key);
    return new MyWorld(this.aliens, this.handleShoot(key), this.moveShip(key));
  }

  @Override
  public World onKeyReleased(String key) {
    return new MyWorld(this.aliens, this.bullets, this.stopShip(key));
  }

  public boolean bigBang() {
    return super.bigBang(this.cols * this.cellSize, this.rows * this.cellSize, 1/this.fps);
  }

  private ILo<Alien> generateAliens(int rows, int cols) {
    ILo<Alien> aliens = new MtLo<Alien>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        aliens.add(new Alien(j, i));
      }
    }
    return aliens;
  }

  boolean aliensReachedEarth() {
    return this.aliens.any(new ReachedEarth(rows - 1));
  }

  boolean gameOver() {
    return this.aliensReachedEarth();
  }
}

class ReachedEarth implements IPredicate<Alien> {
  int y;

  ReachedEarth(int y) {
    this.y = y;
  }

  public boolean apply(Alien alien) {
    return alien.reachedY(this.y);
  }
}

class ExamplesWorld {
  ExamplesWorld() {
  }

  boolean testIntDivision(Tester t) {
    return t.checkExpect(3 / 11, 0);
  }

  boolean testBigBang(Tester t) {
    MyWorld w = new MyWorld();
    System.out.println(w.ship);
    return w.bigBang();
  }

  boolean testShip(Tester t) {
    MyWorld w = new MyWorld();
    return t.checkExpect(w.ship, new Ship(10, 29, 24));
  }
}