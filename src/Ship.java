import javalib.funworld.WorldScene;
import javalib.worldimages.*;

import java.awt.*;

public class Ship {
  int cellSize;
  int x;
  int y;
  int v;
  int speed = 4;
  WorldImage image;

  Ship(int x, int y, int v, int cellSize) {
    this.x = x;
    this.y = y;
    this.v = v;
    this.cellSize = cellSize;
    image = this.shipImage();
  }

  Ship(int x, int y, int cellSize) {
    this(x, y, 0, cellSize);
  }


  private WorldImage shipImage() {
    int w = 11;
    int h = 8;
    String image = "  #     #     #   #     #######   ## ### ## ############ ####### ## #     # #   ## ##   ";
    return this.shipImageHelper(w, h, image);
  }

  private WorldImage shipImageHelper(int w, int h, String str) {
    int pixelSize = Math.floorDiv(this.cellSize, Math.max(w, h));
    return shipImageHelper(
      new RectangleImage(w * pixelSize, h * pixelSize, OutlineMode.SOLID, new Color(0, 0, 0, 0)),
      w, h, 0, str,
      new RectangleImage(pixelSize, pixelSize, OutlineMode.SOLID, Color.RED));
  }

  private WorldImage shipImageHelper(WorldImage image, int w, int h, int a, String str, RectangleImage pixel) {
    if (str.length() == a) {
      return image;
    }
    if (str.charAt(a) == '#') {
      return new OverlayOffsetImage(
        this.shipImageHelper(image, w, h, a + 1, str, pixel),
        (a % w * pixel.width) - (w - 1) * Math.floorDiv(pixel.width, 2),
        (Math.floorDiv(a, w) * pixel.width) - (h - 1) * Math.floorDiv(pixel.width, 2),
        pixel
      );
    }
    return this.shipImageHelper(image, w, h, a + 1, str, pixel);
  }

  public WorldScene drawShip(WorldScene scene) {
    return scene.placeImageXY(
      this.image,
      this.x,
      this.y
    );
  }

  Ship moveLeft() {
    return new Ship(this.x, this.y, -1, this.cellSize);
  }

  Ship moveRight() {
    return new Ship(this.x, this.y, 1, this.cellSize);
  }

  Ship stop() {
    return new Ship(this.x, this.y, this.cellSize);
  }

  int futureX() {
    return this.x + (this.v * this.speed);
  }

  Ship tick(int max) {
    if (this.futureX() > 0 && this.futureX() < max) {
      return new Ship(this.futureX(), this.y, this.v, this.cellSize);
    } else {
      return this.stop();
    }
  }
}
