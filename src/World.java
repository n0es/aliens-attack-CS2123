import javalib.funworld.*;
import javalib.worldimages.*;
import tester.*;

import java.awt.*;

public class World extends javalib.funworld.World {
  ILo<Alien> aliens;
  ILo<Bullet> bullets;
  Ship ship;
  Keys keys;
  int ammo;
  Direction aliensDirection;

  int rows = 30;
  int cols = 20;
  int cellSize = 24;
  double fps = 60;


  // Your world must have a constructor that just takes an integer,
  // which represents the number of bullets a player has to shoot.
  // That is how your graders will launch your world.
  World(int ammo) {
    this.aliens = this.generateAliens(3, 10);
    this.bullets = new MtLo<Bullet>();
    this.ship = new Ship(10 * this.cellSize, 29 * this.cellSize + cellSize / 2, this.cellSize);
    this.keys = new Keys();
    this.ammo = ammo;
  }

  World() {
    this.aliens = this.generateAliens(3, 10);
    this.bullets = new MtLo<Bullet>();
    this.ship = new Ship(10 * this.cellSize, 29 * this.cellSize + cellSize / 2, this.cellSize);
    this.keys = new Keys();
    this.ammo = 10;
  }

  World(ILo<Alien> aliens, ILo<Bullet> bullets, Ship ship, Keys keys, int ammo, Direction direction) {
    this.aliens = aliens;
    this.bullets = bullets;
    this.ship = ship;
    this.keys = keys;
    this.ammo = ammo;
    this.aliensDirection = direction;
  }


  DrawShip drawShip = new DrawShip(this.cellSize);

  public WorldScene drawShip(WorldScene scene) {
    return this.drawShip.apply(this.ship, scene);
  }

  DrawBullets drawBullets = new DrawBullets();

  public WorldScene drawBullets(WorldScene scene) {
    return this.bullets.foldr(
      drawBullets,
      scene
    );
  }

  DrawAliens drawAliens = new DrawAliens(this.cellSize);

  public WorldScene drawAliens(WorldScene scene) {
    return this.aliens.foldr(
      drawAliens,
      scene
    );
  }

  public WorldScene drawBackground(boolean grid) {
    if (grid) {
      return this.drawBackground(true, 0, 0);
    } else {
      return this.drawBackground();
    }
  }

  public WorldScene drawBackground(boolean grid, int x, int y) {
    if (x > this.cols) {
      return this.drawBackground(grid, 0, y + 1);
    } else if (y > this.rows) {
      return this.drawBackground();
    } else {
      return this.drawBackground(grid, x + 1, y).placeImageXY(
        new VisiblePinholeImage(new EmptyImage()),
        x * this.cellSize,
        y * this.cellSize
      );
    }
  }

  public WorldScene drawBackground() {
    return this.getEmptyScene().placeImageXY(
      new RectangleImage(
        this.cols * this.cellSize,
        this.rows * this.cellSize,
        OutlineMode.SOLID,
        Color.decode("#271b3a")
      ),
      this.cols * this.cellSize / 2,
      this.rows * this.cellSize / 2
    );
  }

  public WorldScene makeScene() {
    return drawAliens(
      drawBullets(
        drawShip(
          drawBackground(true)
        )
      )
    );
  }


//  TickAlien tickAlien = new TickAlien();
//  public World updateAliens() {
//    return new World(
//      this.aliens.filter(
//        alienHitByAnyBullet,
//        this.bullets
//      ).map(
//        tickAlien
//      ),
//      this.bullets,
//      this.ship,
//      this.keys,
//      this.ammo,
//      this.aliensDirection
//    );
//  }

  public ILo<Bullet> handleShoot(String s) {
    if (s.equals(" ") &&
      this.ammo > 0 &&
      !this.keys.isPressed(" ")) {
      return this.bullets.add(new Bullet(this.ship.x, this.ship.y));
    } else {
      return this.bullets;
    }
  }
  TickBullets tickBullets = new TickBullets();
  TickAliens tickAliens = new TickAliens();
  TickShip tickShip = new TickShip(this.cols * this.cellSize);
  public World onTick() {
    return new World(
      aliens.foldr(
        tickAliens,
        new MtLo<Alien>()
      ),
      bullets.foldr(
        tickBullets,
        new MtLo<Bullet>()
      ),
      tickShip.apply(ship.control(keys)),
      keys,
      ammo,
      aliensDirection
    );
  }

  @Override
  public javalib.funworld.World onKeyEvent(String key) {
    return new World(
      this.aliens,
      this.handleShoot(key),
      this.ship,
      keys.press(key),
      this.ammo,
      this.aliensDirection
    );
  }

  @Override
  public javalib.funworld.World onKeyReleased(String key) {
    return new World(
      this.aliens,
      this.bullets,
      this.ship,
      keys.release(key),
      this.ammo,
      this.aliensDirection
    );
  }

  public boolean bigBang() {
    return super.bigBang(this.cols * this.cellSize, this.rows * this.cellSize, 1 / this.fps);
  }

  ILo<Alien> generateAliens(int rows, int cols, int startX, int startY, int accum) {
    if (accum == rows * cols) {
      return new MtLo<Alien>();
    } else {
      return new ConsLo<Alien>(
        new Alien(startX + accum % cols - Math.floorDiv(cols, 2), startY + accum / cols),
        this.generateAliens(rows, cols, startX, startY, accum + 1)
      );
    }
  }

  ILo<Alien> generateAliens(int rows, int cols) {
    return generateAliens(rows, cols, Math.floorDiv(this.cols, 2), 1, 0);
  }

  ReachedEarth reachedEarth = new ReachedEarth(this.rows - 1);
  boolean aliensReachedEarth() {
    return this.aliens.any(reachedEarth);
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

  boolean testBigBang(Tester t) {
    World w = new World();
    System.out.println(w.ship);
    return w.bigBang();
  }

  boolean testIntDivision(Tester t) {
    return t.checkExpect(3 / 11, 0);
  }


  boolean testGenerateAliens(Tester t) {
    World w = new World();
    return t.checkExpect(
      w.generateAliens(2, 6),
      new ConsLo<Alien>(
        new Alien(7, 1),
        new ConsLo<Alien>(
          new Alien(8, 1),
          new ConsLo<Alien>(
            new Alien(9, 1),
            new ConsLo<Alien>(
              new Alien(10, 1),
              new ConsLo<Alien>(
                new Alien(11, 1),
                new ConsLo<Alien>(
                  new Alien(12, 1),
                  new ConsLo<Alien>(
                    new Alien(7, 2),
                    new ConsLo<Alien>(
                      new Alien(8, 2),
                      new ConsLo<Alien>(
                        new Alien(9, 2),
                        new ConsLo<Alien>(
                          new Alien(10, 2),
                          new ConsLo<Alien>(
                            new Alien(11, 2),
                            new ConsLo<Alien>(
                              new Alien(12, 2),
                              new MtLo<Alien>()
                            )
                          )
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    );
  }

//  boolean testShip(Tester t) {
//    World w = new World();
//    return t.checkExpect(w.ship, new Ship(10, 29, 24));
//  }
}