import javalib.funworld.WorldScene;
import javalib.worldimages.*;

import java.awt.*;

public class Ship {
  int x;
  int y;
  int v;
  int speed = 4;

  Ship(int x, int y, int v) {
    this.x = x;
    this.y = y;
    this.v = v;
  }

  Ship(int x, int y) {
    this(x, y, 0);
  }

  Ship move() {
    return new Ship(futureX(), y, v);
  }

  Ship stop() {
    return new Ship(x, y);
  }

  Ship control(Keys k) {
    if (k.isPressed("left")) {
      return new Ship(x, y, -1);
    } else if (k.isPressed("right")) {
      return new Ship(x, y, 1);
    } else {
      return new Ship(x, y, 0);
    }
  }

  int futureX() {
    return this.x + (this.v * this.speed);
  }
}

class TickShip implements IFunc<Ship, Ship> {
  int max;

  TickShip(int max) {
    this.max = max;
  }

  public Ship apply(Ship s) {
    if (s.futureX() > 0 && s.futureX() < max) {
      return s.move();
    } else {
      return s.stop();
    }
  }
}

class DrawShip implements IFunc2<Ship, WorldScene, WorldScene> {
  int cellSize;
  WorldImage image;

  DrawShip(int cellSize) {
    this.cellSize = cellSize;
    this.image = new Image(3, 3, " # #### #", cellSize).image;
  }

  public WorldScene apply(Ship s, WorldScene scene) {
    return scene.placeImageXY(
      image,
      s.x,
      s.y
    );
  }
}