import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayOffsetImage;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

import java.awt.*;

public class Image {
  int width;
  int height;
  int cellSize;
  String data;
  WorldImage image;
  RectangleImage pixel;

  Image(int width, int height, String data, int cellSize) {
    this.width = width;
    this.height = height;
    this.data = data;
    this.cellSize = cellSize;
    this.image = this.draw();
  }

  private WorldImage draw() {
    int pixelSize = Math.floorDiv(cellSize, Math.max(width, height));
    this.pixel = new RectangleImage(pixelSize, pixelSize, OutlineMode.SOLID, Color.RED);
    return draw(
      new RectangleImage(
        width * pixelSize,
        height * pixelSize,
        OutlineMode.SOLID,
        new Color(0, 0, 0, 0)
      ),
      0);
  }

  private WorldImage draw(WorldImage image, int a) {
    if (data.length() == a) {
      return image;
    } else if (data.charAt(a) == '#') {
      return new OverlayOffsetImage(
        this.draw(image, a + 1),
        (a % width * pixel.width) - (width - 1) * Math.floorDiv(pixel.width, 2),
        (Math.floorDiv(a, width) * pixel.width) - (height - 1) * Math.floorDiv(pixel.width, 2),
        pixel
      );
    } else {
      return draw(image, a + 1);
    }
  }
}

