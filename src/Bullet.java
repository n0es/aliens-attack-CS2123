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

  public BoundingBox getBounds(int cellSize) {
    int bulletWidth = Math.max(2, cellSize / 4);
    int bulletHeight = cellSize/2;
    return new BoundingBox(x - bulletWidth / 2, y - bulletHeight / 2, bulletWidth, bulletHeight);
  }
}

class DrawBullets implements IFunc2<Bullet, WorldScene, WorldScene> {
  WorldImage bulletImage;

  // Constructor now accepts cellSize
  DrawBullets(int cellSize) {
    this.bulletImage = new RectangleImage(
            Math.max(2, cellSize / 4),
            cellSize/2,
            OutlineMode.SOLID,
            Color.RED
    );
  }

  public WorldScene apply(Bullet b, WorldScene scene) {
    // Position the bullet based on cellSize
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
    return bullet.getBounds(cellSize).intersects(alien.getBounds(cellSize));
  }
}

class AlienHitBullet implements IPredicate2<Alien, Bullet> {
  int cellSize;

  AlienHitBullet(int cellSize) {
    this.cellSize = cellSize;
  }

  public boolean apply(Alien alien, Bullet bullet) {
    return alien.getBounds(cellSize).intersects(bullet.getBounds(cellSize));
  }
}
