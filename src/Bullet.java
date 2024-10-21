public class Bullet {
  int x;
  int y;

  Bullet(int x, int y) {
    this.x = x;
    this.y = y;
  }

  Bullet moveUp() {
    return new Bullet(this.x, this.y - 1);
  }
}
