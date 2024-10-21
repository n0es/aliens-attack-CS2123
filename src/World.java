public class World {
  int bullets;
  ILo<Alien> aliens;

  int rows = 30;
  int cols = 20;
  int cellSize = 32;
  double tickRate = 0.1;

  World(int lives) {
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


  World w = new World(10);
}