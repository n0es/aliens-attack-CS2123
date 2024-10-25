import javalib.*;
import tester.*;
public class World {
  ILo<Alien> aliens;
  ILo<Bullet> bullets;
  Ship ship;
  Direction direction;

  int rows = 30;
  int cols = 20;
  int cellSize = 32;
  double tickRate = 0.1;
  
  World(ILo<Alien> aliens, ILo<Bullet> bullets, Ship ship, Direction direction) {
    this.aliens = aliens;
    this.bullets = bullets;
    this.ship = ship;
    this.direction = direction;
  }

  World() {
    new World(this.generateAliens(3, 10),
      new MtLo<Bullet>(),
      new Ship(10, 29),
      new Direction("Right"));
  }

  public World onTick(){
    return this.updateAliens();
  }
  public World updateAliens(){
    return new World(
      this.aliens.map(this.direction.Move()).filter(new AlienHitByAnyBullet(), this.bullets),
      this.bullets,
      this.ship,
      this.direction);
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
  ExamplesWorld() {}

  World w = new World();
}