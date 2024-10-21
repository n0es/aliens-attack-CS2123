public class Ship {
  int x;
  int y;

  Ship(int x, int y) {
    this.x = x;
    this.y = y;
  }

  Ship moveLeft() {
    return new Ship(this.x - 1, this.y);
  }

  Ship moveRight() {
    return new Ship(this.x + 1, this.y);
  }
}
