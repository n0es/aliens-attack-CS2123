import javalib.funworld.*;
import javalib.worldimages.*;

import java.awt.*;

public class Bullet {
  int x;
  int y;

  Bullet(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public boolean collidesWith(Alien alien) {
    return (this.x == alien.getX()) && (this.y == alien.getY());
  }

  public double distanceFrom(Alien alien, int cellSize) {
    return Math.sqrt(
      Math.pow(this.x - alien.getX()*cellSize, 2) +
      Math.pow(this.y - alien.getY()*cellSize, 2)
    );
  }
}

class DrawBullets implements IFunc2<Bullet, WorldScene, WorldScene> {
  WorldImage bulletImage = new RectangleImage(4, 12, OutlineMode.SOLID, Color.RED);

  public WorldScene apply(Bullet b, WorldScene scene) {
    return scene.placeImageXY(
      bulletImage,
      b.x,
      b.y
    );
  }
}

// tick bullet
// if y < 0, remove bullet from linked list
// else, move bullet up by 5
class TickBullets implements IFunc2<Bullet, ILo<Bullet>, ILo<Bullet>> {
  ILo<Alien> aliens;
  AlienHitBullet alienHitBullet;

  TickBullets(ILo<Alien> aliens, int cellSize) {
    this.aliens = aliens;
    this.alienHitBullet = new AlienHitBullet(cellSize);
  }

  public ILo<Bullet> apply(Bullet b, ILo<Bullet> bullets) {
    if (b.y < 0 || this.aliens.any(alienHitBullet, b)) {
      return bullets;
    } else {
      return new ConsLo<Bullet>(new Bullet(b.x, b.y - 5), bullets);
    }
  }
}

class BulletHitAlien implements IPredicate2<Bullet, Alien> {
  int cellSize;

  BulletHitAlien(int cellSize) {
    this.cellSize = cellSize;
  }

  public boolean apply(Bullet bullet, Alien alien) {
    return bullet.distanceFrom(alien, cellSize) < cellSize;
  }
}

class AlienHitBullet implements IPredicate2<Alien, Bullet> {
  int cellSize;

  AlienHitBullet(int cellSize) {
    this.cellSize = cellSize;
  }

  public boolean apply(Alien alien, Bullet bullet) {
    return bullet.distanceFrom(alien, cellSize) < Math.floorDiv(cellSize, 2);
  }
}
