import javalib.funworld.WorldScene;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

import java.awt.*;

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
      a.x * this.cellSize,
      a.y * this.cellSize
    );
  }
}