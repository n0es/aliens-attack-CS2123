import javalib.funworld.WorldScene;
import javalib.worldimages.WorldImage;

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

  public BoundingBox getBounds(int cellSize) {
    int pixelX = this.x * cellSize;
    int pixelY = this.y * cellSize;
    return new BoundingBox(pixelX, pixelY, cellSize, cellSize);
  }
}

class TickAliens implements IFunc2<Alien, ILo<Alien>, ILo<Alien>> {
  int max;
  Direction direction;
  ILo<Bullet> bullets;
  MoveDirection moveDirection;
  BulletHitAlien bulletHitAlien;
  int tick;

  TickAliens(World world) {
    this.max = world.cols;
    this.direction = world.aliensDirection;
    this.bullets = world.bullets;
    this.moveDirection = new MoveDirection(direction);
    this.bulletHitAlien = new BulletHitAlien(world.cellSize);
    this.tick = world.alienTick;
  }

  public ILo<Alien> apply(Alien a, ILo<Alien> aliens) {
    if (this.bullets.any(bulletHitAlien, a)) {
      return aliens;
    } else if (tick==0) {
      return new ConsLo<Alien>(moveDirection.apply(a), aliens);
    } else {
      return new ConsLo<Alien>(a, aliens);
    }
  }
}

class DrawAliens implements IFunc2<Alien, WorldScene, WorldScene> {
  int cellSize;
  WorldImage image;

  DrawAliens(int cellSize) {
    this.cellSize = cellSize;
    this.image = new Image(11, 8, "  #     #     #   #     #######   ## ### ## ############ ####### ## #     # #   ## ##   ", cellSize).image;
  }

  public WorldScene apply(Alien a, WorldScene scene) {
    return scene.placeImageXY(
      image,
      a.x * this.cellSize + this.cellSize / 2,
      a.y * this.cellSize + this.cellSize / 2
    );
  }
}