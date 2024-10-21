public class World {
  ILo<Bullet> bullets;
  ILo<Alien> aliens;
  Ship ship;

  int rows = 30;
  int cols = 20;
  int cellSize = 32;
  double tickRate = 0.1;
  
  World(ILo<Alien> aliens, ILo<Bullet> bullets, Ship ship) {
    this.aliens = aliens;
    this.bullets = bullets;
    this.ship = ship;
  }

  World() {
    new World(this.generateAliens(3, 10), new MtLo<Bullet>(), new Ship(10, 29));
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