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

  int getX() {
    return this.x;
  }

  int getY() {
    return this.y;
  }

  boolean reachedY(int y) {
    return this.y >= y;
  }

  boolean collidedWith(Bullet bullet) {
    return this.x == bullet.x && this.y == bullet.y;
  }
}

class AlienHitByAnyBullet implements IPredicate2<Alien, ILo<Bullet>> {
  public boolean apply(Alien alien, ILo<Bullet> bulletList) {
    return bulletList.any(new BulletHitAlien(), alien);
  }
}

class MoveAlienLeft implements IFunc<Alien, Alien>{
  public Alien apply(Alien alien){
    return alien.moveLeft();
  }
}
class MoveAlienRight implements IFunc<Alien, Alien>{
  public Alien apply(Alien alien){
    return alien.moveRight();
  }
}
class MoveAlienDown implements IFunc<Alien, Alien>{
  public Alien apply(Alien alien){
    return alien.moveDown();
  }
}
class MoveAlienUp implements IFunc<Alien, Alien>{
  public Alien apply(Alien alien){
    return alien.moveUp();
  }
}