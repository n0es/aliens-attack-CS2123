public class Bullet {
  int x;
  int y;

  Bullet(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean collidesWith(Alien alien) {
    return (this.x == alien.getX()) && (this.y == alien.getY());
  }

  Bullet moveUp() {
    return new Bullet(this.x, this.y - 1);
  }
}

class BulletHitAlien implements IPredicate2<Bullet, Alien>{
  public boolean apply(Bullet bullet, Alien alien){
    return (bullet.collidesWith(alien));
  }
}
