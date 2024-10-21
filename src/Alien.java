public class Alien {
  int x;
  int y;

  Alien(int x, int y) {
    this.x = x;
    this.y = y;
  }

  Alien moveUp() {
    return new Alien(this.x, this.y - 1);
  }

  Alien moveDown() {
    return new Alien(this.x, this.y + 1);
  }

  Alien moveLeft() {
    return new Alien(this.x - 1, this.y);
  }

  Alien moveRight() {
    return new Alien(this.x + 1, this.y);
  }

  boolean reachedY(int y) {
    return this.y >= y;
  }

  boolean collidedWith(Bullet bullet) {
    return this.x == bullet.x && this.y == bullet.y;
  }
}